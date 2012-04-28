package com.nanomobile.linebirdsclone.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.nanomobile.linebirdsclone.framework.AirPlane;
import com.nanomobile.linebirdsclone.framework.Assets;
import com.nanomobile.linebirdsclone.framework.Bird;
import com.nanomobile.linebirdsclone.framework.Coin;
import com.nanomobile.linebirdsclone.framework.ParticleSystem;
import com.nanomobile.linebirdsclone.framework.Rand;
import com.nanomobile.linebirdsclone.framework.Settings;
import com.nanomobile.linebirdsclone.framework.Spike;

public class WorldRenderer {
	static final float FRUSTUM_WIDTH = GameWorld.WORLD_WIDTH;
	static final float FRUSTUM_HEIGHT = GameWorld.WORLD_HEIGHT;
	
	public OrthographicCamera cam;
	
	GameWorld world;
	SpriteBatch batch;
	
	public ParticleSystem endGameParticleSystem, 
						  liteCollisionParticleSystem;
	
	public List<ParticleSystem> windsParticleSystemList;
	public static final int COUNT_WINDS_EFFECT = 5;
	
	HashMap<Spike, Sprite> spikesSprite;
	Sprite coinSprite;

	private ParticleSystem _tmpParticleSystem;
	public WorldRenderer (SpriteBatch batch, GameWorld world) {
		this.world = world;
		
		cam = new OrthographicCamera(FRUSTUM_WIDTH, FRUSTUM_HEIGHT);
		cam.position.set(FRUSTUM_WIDTH / 2f, FRUSTUM_HEIGHT / 2f, 0);
		
		this.batch = batch;
		
		endGameParticleSystem = new ParticleSystem(batch, "GameEndCollision.txt");
		
		liteCollisionParticleSystem = new ParticleSystem(batch, "LiteCollision.txt");
		
		windsParticleSystemList = new ArrayList<ParticleSystem>();
		for (int i = 0; i < COUNT_WINDS_EFFECT; i++) {
			_tmpParticleSystem = new ParticleSystem(batch, "WindEffect.txt");
			_tmpParticleSystem.effectDuration = 2.5f;
			windsParticleSystemList.add(i, _tmpParticleSystem);
		}
		
		_createSpikesSprite();
		
		coinSprite = new Sprite(Assets.coinRegion);
		coinSprite.setSize(Coin.COIN_WIDTH, Coin.COIN_HEIGHT);
		coinSprite.setOrigin(Coin.COIN_WIDTH/2f, Coin.COIN_HEIGHT/2f);
		coinSprite.setPosition(world.coin.bounds.x, world.coin.bounds.y);
	}
	
	private Sprite tmpSprite;
	private void _createSpikesSprite() {
		spikesSprite = new HashMap<Spike, Sprite>();
		
		for (int i = 0; i < world.spikesList.size(); i++) {
			tmpSprite = new Sprite(Assets.spikeRegion);
			tmpSprite.setSize(Spike.SPIKE_WIDTH, Spike.SPIKE_HEIGHT);
			tmpSprite.setOrigin(Spike.SPIKE_WIDTH/2f, Spike.SPIKE_HEIGHT/2f);
			
			spikesSprite.put(world.spikesList.get(i), tmpSprite);
		}
	}

	public void render () {
		if (world.state == GameWorld.WORLD_STATE_RUNNING) {
			cam.position.x = world.bird.position.x + FRUSTUM_WIDTH/2f - GameWorld.BIRD_START_POINT.x;
		}
		
		cam.update();
		batch.setProjectionMatrix(cam.combined);
		
		_renderBackground();
		_renderWorldObjects();
	}
	
	private void _renderBackground() {
		batch.disableBlending();
		batch.begin();
		batch.draw(
				Assets.backgroundGameRegion, 
				cam.position.x - GameWorld.WORLD_WIDTH/2f, cam.position.y - GameWorld.WORLD_HEIGHT/2f, 
				GameWorld.WORLD_WIDTH, GameWorld.WORLD_HEIGHT
		);
		batch.end();
	}
	
	private void _renderWorldObjects() {
		batch.enableBlending();
		batch.begin();
		
		_renderParticles();
		_renderTree();
		_renderSpikes();
		_renderCoin();
		_renderAirPlane();
		_renderBird();
		
		batch.end();
	}
	
	/** A I R    P L A N E **/
	private float tmpRotation;
	private void _renderAirPlane() {
		tmpRotation = 0;
		if (world.airPlane.velocity.y < 0) {
			tmpRotation = 45;
		}
		
		batch.draw(
				Assets.planeAnimation.getKeyFrame(world.airPlane.stateTime, true), 
				world.airPlane.bounds.x + AirPlane.AIRPLANE_WIDTH, world.airPlane.bounds.y, 
				AirPlane.AIRPLANE_WIDTH/2f, AirPlane.AIRPLANE_HEIGHT/2f, 
				-AirPlane.AIRPLANE_WIDTH, AirPlane.AIRPLANE_HEIGHT, 
				1, 1, 
				tmpRotation
		);
	}
	
	/** C O I N **/
	private void _renderCoin() {
		coinSprite.setPosition(world.coin.bounds.x, world.coin.bounds.y);
		coinSprite.setRotation(world.coin.rotationDegree);
		coinSprite.draw(batch);
	}
	
	/** P A R T I C L E S **/
	private Vector2 _tmpVector2;
	private void _renderParticles() {
		if (endGameParticleSystem.effectPos == null && world.bird.state == Bird.BIRD_STATE_END) {
			endGameParticleSystem.updateEffect(world.bird.position);
		}
		endGameParticleSystem.renderEffect(batch);
		
		for (int i=0; i < windsParticleSystemList.size(); i++) {
			_tmpVector2 = new Vector2(
					cam.position.x + FRUSTUM_WIDTH * 0.6f + (i+1) * COUNT_WINDS_EFFECT, 
					cam.position.y/2f + Rand.getRand().nextFloat() * (FRUSTUM_WIDTH * 0.4f)
			);
			
			windsParticleSystemList.get(i).updateEffect(_tmpVector2);
			windsParticleSystemList.get(i).renderEffect(batch);
		}
		
		
		if (world.liteCollisionEffectPos != null) {
			liteCollisionParticleSystem.updateEffect(new Vector2(world.liteCollisionEffectPos));
			world.liteCollisionEffectPos = null;
		}
		liteCollisionParticleSystem.renderEffect(batch);
	}
	
	/** S P I K E S **/
	private void _renderSpikes() {
		for (int i = 0; i < world.spikesList.size(); i++) {
			tmpSprite = spikesSprite.get(world.spikesList.get(i));
			
			if (world.spikesList.get(i).bounds.y <= 0) {
				tmpSprite.rotate(180);
			}
			
			tmpSprite.setPosition(
					world.spikesList.get(i).bounds.x,
					world.spikesList.get(i).bounds.y
			);
			
			if (world.state == GameWorld.WORLD_STATE_RUNNING || 
						world.state == GameWorld.WORLD_STATE_READY) {
				tmpSprite.draw(batch);
			} else {
				tmpSprite.draw(batch, 0.4f);
			}
			
			if (world.spikesList.get(i).bounds.y <= 0) {
				tmpSprite.rotate(180);
			}
		}
	}
	
	/** T R E E **/
	private void _renderTree() {
		batch.draw(
				Assets.treeRegion, 
				-0.5f, 0, 
				245/20f, 458/20f
		);
	}
	
	private TextureRegion birdRegion, beakRegion;
	/** B I R D S **/
	private void _renderBird() {
		switch (Settings.bird) {
			case Settings.BURPY:
				if (world.state == GameWorld.WORLD_STATE_READY) {
					birdRegion = Assets.birdBurpyStandRegion;
					beakRegion = Assets.beakB0Region;
				} else if (world.state == GameWorld.WORLD_STATE_RUNNING) {
					birdRegion = Assets.birdBurpyAnimation.getKeyFrame(world.bird.stateTime, true);
					beakRegion = Assets.beakB0Region;
				}
				break;
			
			case Settings.HOUDINI:
				if (world.state == GameWorld.WORLD_STATE_READY) {
					birdRegion = Assets.birdHoudiniStandRegion;
					beakRegion = Assets.beakB0Region;
				} else if (world.state == GameWorld.WORLD_STATE_RUNNING) {
					birdRegion = Assets.birdHoudiniAnimation.getKeyFrame(world.bird.stateTime, true);
					beakRegion = Assets.beakB0Region;
				}
				break;
				
			case Settings.KIRBY:
				if (world.state == GameWorld.WORLD_STATE_READY) {
					birdRegion = Assets.birdKirbyStandRegion;
					beakRegion = Assets.beakS0Region;
				} else if (world.state == GameWorld.WORLD_STATE_RUNNING) {
					birdRegion = Assets.birdKirbyAnimation.getKeyFrame(world.bird.stateTime, true);
					beakRegion = Assets.beakS0Region;
				}
				break;
				
			case Settings.PABLO:
				if (world.state == GameWorld.WORLD_STATE_READY) {
					birdRegion = Assets.birdPabloStandRegion;
					beakRegion = Assets.beakS0Region;
				} else if (world.state == GameWorld.WORLD_STATE_RUNNING) {
					birdRegion = Assets.birdPabloAnimation.getKeyFrame(world.bird.stateTime, true);
					beakRegion = Assets.beakS0Region;
				}
				break;
				
			case Settings.PEBBLES:
				if (world.state == GameWorld.WORLD_STATE_READY) {
					birdRegion = Assets.birdPebblesStandRegion;
					beakRegion = Assets.beakS0Region;
				} else if (world.state == GameWorld.WORLD_STATE_RUNNING) {
					birdRegion = Assets.birdPebblesAnimation.getKeyFrame(world.bird.stateTime, true);
					beakRegion = Assets.beakS0Region;
				}
				break;
				
			case Settings.PERKY:
				if (world.state == GameWorld.WORLD_STATE_READY) {
					birdRegion = Assets.birdPerkyStandRegion;
					beakRegion = Assets.beakP0Region;
				} else if (world.state == GameWorld.WORLD_STATE_RUNNING) {
					birdRegion = Assets.birdPerkyAnimation.getKeyFrame(world.bird.stateTime, true);
					beakRegion = Assets.beakP0Region;
				}
				break;
				
			case Settings.SMOKY:
				if (world.state == GameWorld.WORLD_STATE_READY) {
					birdRegion = Assets.birdSmokyStandRegion;
					beakRegion = Assets.beakB0Region;
				} else if (world.state == GameWorld.WORLD_STATE_RUNNING) {
					birdRegion = Assets.birdSmokyAnimation.getKeyFrame(world.bird.stateTime, true);
					beakRegion = Assets.beakB0Region;
				}
				break;
				
			default:
				if (world.state == GameWorld.WORLD_STATE_READY) {
					birdRegion = Assets.birdKirbyStandRegion;
					beakRegion = Assets.beakS0Region;
				} else if (world.state == GameWorld.WORLD_STATE_RUNNING) {
					birdRegion = Assets.birdKirbyAnimation.getKeyFrame(world.bird.stateTime, true);
					beakRegion = Assets.beakS0Region;
				}
				break;
		}
		
		batch.draw(
				birdRegion, 
				world.bird.bounds.x, world.bird.bounds.y,
				Bird.BIRD_WIDTH/2f, Bird.BIRD_HEIGHT/2f,
				Bird.BIRD_WIDTH, Bird.BIRD_HEIGHT,
				1,1,
				world.bird.rotationDegree
		);
		batch.draw(
				beakRegion, 
				world.bird.bounds.x, world.bird.bounds.y,
				Bird.BIRD_WIDTH/2f, Bird.BIRD_HEIGHT/2f,
				Bird.BIRD_WIDTH, Bird.BIRD_HEIGHT,
				1,1,
				world.bird.rotationDegree
		);
	}
}