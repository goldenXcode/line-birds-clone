package com.nanomobile.linebirdsclone.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.nanomobile.linebirdsclone.framework.Bird;
import com.nanomobile.linebirdsclone.framework.Coin;
import com.nanomobile.linebirdsclone.framework.OverlapTester;
import com.nanomobile.linebirdsclone.framework.AirPlane;
import com.nanomobile.linebirdsclone.framework.Rand;
import com.nanomobile.linebirdsclone.framework.Settings;
import com.nanomobile.linebirdsclone.framework.Spike;

public class GameWorld {
	public interface WorldListener {
		
	}
	
	public static final float WORLD_WIDTH = 48;//Screen.SCREEN_WIDTH / 20;
	public static final float WORLD_HEIGHT = 32;// Screen.SCREEN_HEIGHT / 20;
	
	public static final Vector2 WORLD_GRAVITY = new Vector2(0, -18);
	public static final Vector2 BIRD_START_POINT = new Vector2(4.2f, 14.5f);
	
	public static final int WORLD_STATE_READY = 0;
	public static final int WORLD_STATE_RUNNING = 1;
	public static final int WORLD_STATE_PAUSE = 2;
	public static final int WORLD_STATE_END = 3;
	
	public final WorldListener listener;
	
	public boolean isTouchLeft, isTouchRight;
	
	public Vector2 liteCollisionEffectPos = null;
	
	public Bird bird;
	public List<Spike> spikesList;
	public Coin coin;
	public AirPlane airPlane;
	public int coinsCount;
	
	public int score;
	public int state;
	public String crashString;
	
	private int _km;
	
	private float _x, _y;

	public GameWorld (WorldListener listener) {
		this.listener = listener;
		
		this.coinsCount = this.score = this._km = 0;
		
		this.state = WORLD_STATE_READY;
		
		_generateLevel();
	}

	private void _generateLevel () {
		_createWorld();
	}
	
	private void _createWorld() {
		bird = new Bird(BIRD_START_POINT.x, BIRD_START_POINT.y);
		
		_createWorldObjects();
	}
	
	private void _generateSpike_Y() {
		_y = Rand.getRand().nextFloat() * (Spike.SPIKE_HEIGHT * 0.5f) / (Settings.difficulty + 1) + 
				(4 - Settings.difficulty) * 0.3f;
		
		if (Rand.getRand().nextBoolean()) {
			// Down Spike
			_y = Spike.SPIKE_HEIGHT/(10 - Settings.difficulty) - _y;
		} else {
			// Up Spike
			_y = WORLD_HEIGHT - Spike.SPIKE_HEIGHT/(7 - Settings.difficulty) + _y;
		}
	}
	
	int randInt, tmpInt;
	private void _createWorldObjects() {
		spikesList = new ArrayList<Spike>();
		
		do {
			randInt = Rand.getRand().nextInt(20 * (Settings.difficulty + 1));
			tmpInt = Rand.getRand().nextInt(20 * (Settings.difficulty + 1));
		} while (randInt != tmpInt);
		
		for (int i = 0; i < 20 * (Settings.difficulty + 1); i++) {
			if (i == 0) {
				_x = WORLD_WIDTH * 0.5f + Rand.getRand().nextInt((int)(WORLD_WIDTH * 0.5f));
			} else {
				_x = spikesList.get(i-1).position.x + 4 * (2 + Settings.difficulty) + 
						Rand.getRand().nextInt(17);
			}
			
			_generateSpike_Y();
			
			spikesList.add(i, new Spike(_x, _y));
			
			if (randInt == i) {
				coin = new Coin(
						_x + Rand.getRand().nextInt((int)(WORLD_WIDTH * 0.4f)) + Coin.COIN_WIDTH * 2.5f, 
						WORLD_HEIGHT * 0.75f * Rand.getRand().nextFloat() + WORLD_HEIGHT * 0.15f
				);
			}
			
			if (tmpInt == i) {
				airPlane = new AirPlane(
						_x + Rand.getRand().nextInt((int)(WORLD_WIDTH * 10.5f)) + 
														AirPlane.AIRPLANE_WIDTH * 2.5f, 
						WORLD_HEIGHT * 0.75f * Rand.getRand().nextFloat() + WORLD_HEIGHT * 0.15f
				);
			}
		}
		
		for (Spike spike : spikesList) {
			if (OverlapTester.overlapRectangles(spike.bounds, coin.bounds)) {
				coin.setPosition(
						coin.position.x + 
								Coin.COIN_WIDTH * 1.25f * (spike.position.x > coin.position.x ? -1 : 1), 
						coin.position.y
				);
				
				return;
			}
		}
	}
	
	private void _update(float deltaTime) {
		//score = (int)( (bird.position.x - BIRD_START_POINT.x) * (4 - Settings.difficulty) * 0.75f );
		score = (int)(bird.position.x - BIRD_START_POINT.x);
		
		if (score >= (1 + _km) * 1000) {
			_km++;
			bird.velocity.x += 0.25f;
		}
		
		_checkCollisions(deltaTime);
		
		if (bird.state != Bird.BIRD_STATE_FLY) {
			return;
		}
		
		_updateSpikes();
		_updateCoin();
		_updateAirPlane();
	}
	
	private void _updateAirPlane() {
		if (airPlane.position.x >= bird.position.x - WORLD_WIDTH * 15 + (4 - Settings.difficulty) * 5) {
			return;
		}
		
		do {
			randInt = Rand.getRand().nextInt(20 * (Settings.difficulty + 1));
			_x = spikesList.get(randInt).position.x;
			if (_x < bird.position.x + WORLD_WIDTH * 0.75f) {
				_x = 0;
			}
		} while (0 == _x);
		
		airPlane.setPosition(
				_x + WORLD_WIDTH * 5.5f + (4 - Settings.difficulty) * 5, 
				WORLD_HEIGHT * 0.75f * Rand.getRand().nextFloat() + WORLD_HEIGHT * 0.15f
		);
	}
	
	private void _updateCoin() {
		coin.rotationDegree += 2.5f;
		if (coin.rotationDegree >= 360) {
			coin.rotationDegree -= 360;
		}
				
		if (coin.position.x >= bird.position.x - WORLD_WIDTH/2f) {
			return;
		}
		
		do {
			randInt = Rand.getRand().nextInt(20 * (Settings.difficulty + 1));
			_x = spikesList.get(randInt).position.x;
			if (_x < bird.position.x + WORLD_WIDTH * 0.75f) {
				_x = 0;
			}
		} while (0 == _x);
		
		coin.setPosition(
				_x + Coin.COIN_WIDTH * 3.5f, 
				WORLD_HEIGHT * 0.75f * Rand.getRand().nextFloat() + WORLD_HEIGHT * 0.15f
		);
		
		for (Spike spike : spikesList) {
			if (OverlapTester.overlapRectangles(spike.bounds, coin.bounds)) {
				coin.setPosition(
						coin.position.x + 
							Coin.COIN_WIDTH * 1.25f * (spike.position.x > coin.position.x ? -1 : 1), 
						coin.position.y
				);
				
				return;
			}
		}
	}
	
	private void _checkCollisions(float deltaTime) {
		if (bird.state != Bird.BIRD_STATE_FLY) {
			return;
		}
		
		if (bird.position.y - Bird.BIRD_HEIGHT * 0.1f <= 0) {
			crashString = "Hit ground too hard!"; // Down ground
        	_birdCrashed(deltaTime);
        	return;
        } else if (bird.position.y + Bird.BIRD_HEIGHT * 0.1f >= GameWorld.WORLD_HEIGHT) {
        	crashString = "Hit ground too hard!"; // Up ground
        	_birdCrashed(deltaTime);
        	return;
        } else if (bird.position.y - Bird.BIRD_HEIGHT * 0.25f <= 0) {
        	liteCollisionEffectPos = new Vector2();
        	liteCollisionEffectPos.set(
        			bird.position.x, 
        			bird.position.y - Bird.BIRD_HEIGHT/2f
        	);
        } else if (bird.position.y + Bird.BIRD_HEIGHT * 0.6f >= GameWorld.WORLD_HEIGHT) {
        	liteCollisionEffectPos = new Vector2();
        	liteCollisionEffectPos.set(
        			bird.position.x, 
        			bird.position.y + Bird.BIRD_HEIGHT/2f
        	);
        }
		
		_checkSpikeCollisions(deltaTime);
		
		if (bird.state != Bird.BIRD_STATE_FLY) {
			return;
		}
		
		_checkAirPlaneCollision(deltaTime);
		
		if (bird.state != Bird.BIRD_STATE_FLY) {
			return;
		}
		
		_checkCoinCollision();
	}
	
	private void _checkAirPlaneCollision(float deltaTime) {
		if (OverlapTester.overlapRectangles(bird.bounds, airPlane.bounds)) {
			crashString = "Hit by the plane!";
			airPlane.velocity.y += WORLD_GRAVITY.y/2f;
			_birdCrashed(deltaTime);
			return;
		}
		
		return;
	}
	
	private void _checkSpikeCollisions(float deltaTime) {
		for (Spike spike : spikesList) {
			if (bird.position.dst(spike.position.x, spike.bounds.y) 
					<= Bird.BIRD_HEIGHT * 0.4f) { // Up Spike
				liteCollisionEffectPos = new Vector2();
				liteCollisionEffectPos.set(
					spike.position.x, 
					spike.bounds.y
				);
				
				return;
			} else if ( // Down Spike
				bird.position.dst(
						spike.position.x, spike.bounds.y + spike.bounds.height
				) <= Bird.BIRD_HEIGHT * 0.4f
			) {
				liteCollisionEffectPos = new Vector2();
				liteCollisionEffectPos.set(
					spike.position.x, 
					spike.bounds.y + spike.bounds.height
				);
				
				return;
			}
			
			if (OverlapTester.overlapRectangles(bird.bounds, spike.bounds)) {
				crashString = "Crashed!";
				_birdCrashed(deltaTime);
				return;
			}
		}
	}
	
	private void _checkCoinCollision() {
		if (OverlapTester.overlapRectangles(bird.bounds, coin.bounds)) {
			coinsCount++;
			if (coinsCount == 3) {
				Settings.threeOfCoinsCount++;
				coinsCount = 0;
			}
			coin.setPosition(-WORLD_WIDTH, -WORLD_HEIGHT);
		}
		
		return;
	}
	
	private void _updateSpikes() {
		for (int i = 0; i < spikesList.size(); i++) {
			if (spikesList.get(i).position.x <= bird.position.x - WORLD_WIDTH * 2.5f) {
				if (i == 0) {
					_x = spikesList.get(spikesList.size()-1).position.x + 5 * (2 + Settings.difficulty) + 
																	Rand.getRand().nextInt(25);
				} else {
					_x = spikesList.get(i-1).position.x + 4 * (2 + Settings.difficulty) + 
							Rand.getRand().nextInt(17);
				}
				
				_generateSpike_Y();
				
				spikesList.get(i).setPosition(_x, _y);
			}
		}
	}
	
	private void _birdCrashed(float deltaTime) {
		state = WORLD_STATE_END;
		
		bird.state = Bird.BIRD_STATE_END;
		bird.velocity.set(0, WORLD_GRAVITY.y/2f);
		
		bird.update(deltaTime, false, false);
	}
	
	public void update(float deltaTime, boolean isLeft, boolean isRight) {
		deltaTime *= 1.6f;
		
		isTouchLeft = isLeft;
		isTouchRight = isRight;
		
		if (state == WORLD_STATE_READY && isTouchRight == false) {
			return;
		} else if (bird.state == Bird.BIRD_STATE_END) {
			bird.update(deltaTime, false, false);
			airPlane.update(deltaTime);
			return;
		}
		
		if (isTouchRight) {
			bird.velocity.add(WORLD_GRAVITY.x * deltaTime, -1.25f * WORLD_GRAVITY.y * deltaTime);
			if (state == WORLD_STATE_READY) {
				state = WORLD_STATE_RUNNING;
				bird.state = Bird.BIRD_STATE_FLY;
			}
		} else if (isTouchLeft) {
			bird.velocity.add(WORLD_GRAVITY.x * deltaTime, -bird.velocity.y);
		} else {
			bird.velocity.add(WORLD_GRAVITY.x * deltaTime, WORLD_GRAVITY.y * deltaTime);
		}
		
		if (state == WORLD_STATE_RUNNING) {
			bird.update(deltaTime, isTouchLeft, isTouchRight);
			airPlane.update(deltaTime);
			_update(deltaTime);
		}
	}
	
	public void dispose () {
		disposeWorld();
		
		_km = 0;
		score = 0;
		state = WORLD_STATE_END;
	}
	
	private void disposeWorld() {
		
	}
}