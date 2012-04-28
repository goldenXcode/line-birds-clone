package com.nanomobile.linebirdsclone.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.nanomobile.linebirdsclone.framework.Assets;
import com.nanomobile.linebirdsclone.framework.Bird;
import com.nanomobile.linebirdsclone.framework.FontManager;
import com.nanomobile.linebirdsclone.framework.Game;
import com.nanomobile.linebirdsclone.framework.OverlapTester;
import com.nanomobile.linebirdsclone.framework.Screen;
import com.nanomobile.linebirdsclone.framework.Settings;
import com.nanomobile.linebirdsclone.game.GameWorld;
import com.nanomobile.linebirdsclone.game.GameWorld.WorldListener;
import com.nanomobile.linebirdsclone.game.WorldRenderer;

public class GameScreen extends Screen {
	static final int GAME_RUNNING = 0;
	static final int GAME_PAUSED = 1;
	static final int GAME_END = 2;
	
	public static final Vector2 GAME_BUTTONS_POINT = new Vector2(SCREEN_WIDTH/2f, 220);
	
	boolean isTouchRight, isTouchLeft;
	float touchLeftTime;
	
	int state;
	
	Vector3 touchPoint;
	
	GameWorld gameWorld;
	WorldRenderer worldRenderer;
	WorldListener worldListener;
	
	Rectangle pauseBounds;
	Rectangle resumeBounds;
	Rectangle backMainMenuBounds;
	Rectangle mainMenuBounds;
	Rectangle retryBounds;
	
	Sprite bush1Sprite, bush2Sprite;
	
	int lastScore;
	
	private float _stateTimeGameEnd;
	private String _str;

	public GameScreen (Game game) {
		super(game);
		
		//Assets.successSound.stop();
		//Assets.failedSound.stop();
		
		state = GAME_RUNNING;
		
		isTouchLeft = false;
		isTouchRight = false;
		
		touchPoint = new Vector3();
		worldListener = new WorldListener() {
			
		};
		
		gameWorld = new GameWorld(worldListener);
		worldRenderer = new WorldRenderer(batcher, gameWorld);

		pauseBounds = new Rectangle(0, Screen.SCREEN_HEIGHT - 64, 64, 64);
		resumeBounds = new Rectangle(
				guiCam.position.x - Assets.BUTTON_RENDER_SIZES.x / 2f + 20, 
				guiCam.position.y + 30, 
				Assets.BUTTON_RENDER_SIZES.x, 
				Assets.BUTTON_RENDER_SIZES.y
		);
		backMainMenuBounds = new Rectangle(
				guiCam.position.x - Assets.BUTTON_RENDER_SIZES.x / 2f + 20, 
				guiCam.position.y - 40, 
				Assets.BUTTON_RENDER_SIZES.x, 
				Assets.BUTTON_RENDER_SIZES.y
		);
		mainMenuBounds = new Rectangle(
				guiCam.position.x - Assets.BUTTON_RENDER_SIZES.x / 2f + 20, 
				guiCam.position.y + 30, 
				Assets.BUTTON_RENDER_SIZES.x, 
				Assets.BUTTON_RENDER_SIZES.y
		);
		retryBounds = new Rectangle(
				guiCam.position.x - Assets.BUTTON_RENDER_SIZES.x / 2f + 20, 
				guiCam.position.y - 40, 
				Assets.BUTTON_RENDER_SIZES.x, 
				Assets.BUTTON_RENDER_SIZES.y
		);
		
		bush1Sprite = new Sprite(Assets.bushHorizontalRegion);
		bush1Sprite.setSize(
				Assets.BUSH_HORIZONTAL_RENDER_SIZES.x, 
				Assets.BUSH_HORIZONTAL_RENDER_SIZES.y
		);
		bush1Sprite.setOrigin(
				Assets.BUSH_HORIZONTAL_RENDER_SIZES.x/2f, 
				Assets.BUSH_HORIZONTAL_RENDER_SIZES.y/2f
		);
		bush1Sprite.setPosition(0, 0);
		
		bush2Sprite = new Sprite(Assets.bushVerticalRegion);
		bush2Sprite.setSize(
				Assets.BUSH_VERTICAL_RENDER_SIZES.x, 
				Assets.BUSH_VERTICAL_RENDER_SIZES.y
		);
		bush2Sprite.setOrigin(
				Assets.BUSH_VERTICAL_RENDER_SIZES.x/2f, 
				Assets.BUSH_VERTICAL_RENDER_SIZES.y/2f
		);
		bush2Sprite.setPosition(Screen.SCREEN_WIDTH - Assets.BUSH_VERTICAL_RENDER_SIZES.x, 0);
		
		lastScore = 0;
		
		touchLeftTime = -1;
	}

	@Override
	public void update (float deltaTime) {
		if (deltaTime > 0.1f) deltaTime = 0.1f;

		switch (state) {
		case GAME_RUNNING:
			updateRunning(deltaTime);
			break;
		case GAME_PAUSED:
			updatePaused();
			break;
		case GAME_END:
			updateLevelFailed(deltaTime);
			break;
		}
	}

	private void updateRunning (float deltaTime) {
		guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
		
		if (Gdx.input.justTouched()) {
			if (OverlapTester.pointInRectangle(pauseBounds, touchPoint.x, touchPoint.y)) {
				//Assets.playSound(Assets.clickSound);
				pause();
				return;
			}
		}
		
		if (touchLeftTime >= 1.5f) {
			isTouchLeft = false;
			touchLeftTime = -1;
		} else if (touchLeftTime >= 0) {
			touchLeftTime += deltaTime;
		}
		
		gameWorld.update(deltaTime, isTouchLeft, isTouchRight);
		
		if (gameWorld.score != lastScore) {
			lastScore = gameWorld.score;
		}
		
		if (gameWorld.state == GameWorld.WORLD_STATE_END) {
			//Assets.playSound(Assets.failedSound);
			
			state = GAME_END;
			_stateTimeGameEnd = 0;
			_alpha = 0;
			/*if (lastScore >= Settings.highscores[4])
				//"NEW HIGHSCORE: " + lastScore;
			else
				//"SCORE: " + lastScore;*/
			
			Settings.addScore(lastScore);
		}
	}

	private void updatePaused () {
		if (Gdx.input.justTouched()) {
			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
			
			if (OverlapTester.pointInRectangle(resumeBounds, touchPoint.x, touchPoint.y)) {
				//Assets.playSound(Assets.clickSound);
				state = GAME_RUNNING;
				if (gameWorld.bird.position.x == GameWorld.BIRD_START_POINT.x) {
					gameWorld.state = GameWorld.WORLD_STATE_READY;
				} else {
					gameWorld.state = GameWorld.WORLD_STATE_RUNNING;
				}
				return;
			}

			if (OverlapTester.pointInRectangle(backMainMenuBounds, touchPoint.x, touchPoint.y)) {
				//Assets.playSound(Assets.clickSound);
				game.setScreen(new MainMenuScreen(game));
				//Assets.successSound.stop();
				//Assets.failedSound.stop();
				return;
			}
		}
	}

	private void updateLevelFailed (float deltaTime) {
		_stateTimeGameEnd += deltaTime;
		
		gameWorld.update(deltaTime, false, false);
		
		if (Gdx.input.justTouched()) {
			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
			
			if (OverlapTester.pointInRectangle(mainMenuBounds, touchPoint.x, touchPoint.y)) {
				//Assets.playSound(Assets.clickSound);
				game.setScreen(new MainMenuScreen(game));
				//Assets.successSound.stop();
				//Assets.failedSound.stop();
				return;
			}
			
			if (OverlapTester.pointInRectangle(retryBounds, touchPoint.x, touchPoint.y)) {
				//Assets.playSound(Assets.clickSound);
				restartLevel();
				return;
			}
		}
	}

	@Override
	public void present (float deltaTime) {
		if (deltaTime > 0.1f) deltaTime = 0.1f;
		
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		
		worldRenderer.render();
		
		guiCam.update();
		batcher.setProjectionMatrix(guiCam.combined);
		//batcher.enableBlending();
		batcher.begin();
		
		switch (state) {
		case GAME_RUNNING:
			presentRunning(deltaTime);
			break;
		case GAME_PAUSED:
			presentPaused(deltaTime);
			break;
		case GAME_END:
			presentGameOver();
			break;
		}
		
		batcher.end();
	}

	private void presentRunning (float deltaTime) {
		batcher.draw(Assets.pauseRegion, 10, SCREEN_HEIGHT - 32, 18, 22);
		
		_str = "FPS: " + Gdx.graphics.getFramesPerSecond();
		FontManager.fontMarker.setColor(Color.BLUE);
		FontManager.fontMarker.draw(
				batcher, 
				_str, 
				SCREEN_WIDTH/2f - FontManager.fontMarker.getMultiLineBounds(_str).width/2f, 
				SCREEN_HEIGHT
		);
		FontManager.fontMarker.setColor(Color.BLACK);
		
		_renderScore();
		_renderCoins(deltaTime);
	}
	
	private final float X_DELTA_OFFSET = 40;
	private final float SCALE_DELTA_OFFSET = 0.008f;
	private final Vector2 COIN_SIZE = new Vector2(30, 20);
	private final Vector2 EMPTY_SIZE = new Vector2(18, 18);
	
	private float _x, _y, _coinsTime = 0, _sizeScale = 1;
	private boolean _isCoinScaleUp = true;
	private void _renderCoins(float deltaTime) {
		_x = 80;
		_y = SCREEN_HEIGHT - 40;
		
		_coinsTime += deltaTime;
		if (_coinsTime >= 0.5f) {
			_coinsTime = 0;
			if (_isCoinScaleUp) {
				_isCoinScaleUp = false;
			} else {
				_isCoinScaleUp = true;
			}
		} else if (0 < Settings.threeOfCoinsCount) {
			if (_isCoinScaleUp) {
				_sizeScale += SCALE_DELTA_OFFSET;
			} else {
				_sizeScale -= SCALE_DELTA_OFFSET;
			}
		}
		
		switch (gameWorld.coinsCount) {
			case 1:
				batcher.draw(
						Assets.coinRegion, _x, _y, 
						COIN_SIZE.x * _sizeScale, COIN_SIZE.y * _sizeScale
				);
				batcher.draw(
						Assets.starEmptyRegion, _x + X_DELTA_OFFSET, _y, 
						EMPTY_SIZE.x * _sizeScale, EMPTY_SIZE.y * _sizeScale
				);
				batcher.draw(
						Assets.starEmptyRegion, _x + X_DELTA_OFFSET*2, _y, 
						EMPTY_SIZE.x * _sizeScale, EMPTY_SIZE.y * _sizeScale
				);
				break;
				
			case 2:
				batcher.draw(
						Assets.coinRegion, _x, _y, 
						COIN_SIZE.x * _sizeScale, COIN_SIZE.y * _sizeScale
				);
				batcher.draw(
						Assets.coinRegion, _x + X_DELTA_OFFSET, _y,
						COIN_SIZE.x * _sizeScale, COIN_SIZE.y * _sizeScale
				);
				batcher.draw(
						Assets.starEmptyRegion, _x + X_DELTA_OFFSET*2, _y, 
						EMPTY_SIZE.x * _sizeScale, EMPTY_SIZE.y * _sizeScale
				);
				break;
				
			case 3:
				batcher.draw(
						Assets.coinRegion, _x, _y, 
						COIN_SIZE.x * _sizeScale, COIN_SIZE.y * _sizeScale
				);
				batcher.draw(
						Assets.coinRegion, _x + X_DELTA_OFFSET, _y, 
						COIN_SIZE.x * _sizeScale, COIN_SIZE.y * _sizeScale
				);
				batcher.draw(
						Assets.coinRegion, _x + X_DELTA_OFFSET*2, _y, 
						COIN_SIZE.x * _sizeScale, COIN_SIZE.y * _sizeScale
				);
				break;
				
			default:
				if (Settings.threeOfCoinsCount == 0) {
					batcher.draw(
							Assets.starEmptyRegion, _x, _y, 
							EMPTY_SIZE.x, EMPTY_SIZE.y
					);
					batcher.draw(
							Assets.starEmptyRegion, _x + X_DELTA_OFFSET, _y, 
							EMPTY_SIZE.x, EMPTY_SIZE.y
					);
					batcher.draw(
							Assets.starEmptyRegion, _x + X_DELTA_OFFSET*2, _y, 
							EMPTY_SIZE.x, EMPTY_SIZE.y
					);
				} else {
					batcher.draw(
							Assets.coinRegion, _x, _y, 
							COIN_SIZE.x * _sizeScale, COIN_SIZE.y * _sizeScale
					);
					batcher.draw(
							Assets.coinRegion, _x + X_DELTA_OFFSET, _y, 
							COIN_SIZE.x * _sizeScale, COIN_SIZE.y * _sizeScale
					);
					batcher.draw(
							Assets.coinRegion, _x + X_DELTA_OFFSET*2, _y, 
							COIN_SIZE.x * _sizeScale, COIN_SIZE.y * _sizeScale
					);
				}
				break;
		}
		
		if (Settings.threeOfCoinsCount == 0) {
			return;
		}
		
		batcher.draw(Assets.xRegion, _x + X_DELTA_OFFSET * 3, _y, 18, 22);
		FontManager.fontMarker.draw(
				batcher, 
				String.valueOf(Settings.threeOfCoinsCount), 
				_x + X_DELTA_OFFSET * 3 + 20,
				_y + 26
		);
	}
	
	private void _renderScore() {
		FontManager.fontMarker.draw(
				batcher, 
				String.valueOf(lastScore), 
				SCREEN_WIDTH - FontManager.fontMarker
									.getMultiLineBounds(String.valueOf(lastScore)).width - 20, 
				SCREEN_HEIGHT - 5
		);
	}

	private void presentPaused (float deltaTime) {
		batcher.draw(Assets.pauseRegion, 10, SCREEN_HEIGHT - 32, 18, 22);
		
		_renderCoins(deltaTime);
		
		batcher.draw(
				Assets.buttonRegion, 
				guiCam.position.x - Assets.BUTTON_RENDER_SIZES.x / 2f + 20, 
				guiCam.position.y + 30, 
				Assets.BUTTON_RENDER_SIZES.x,
				Assets.BUTTON_RENDER_SIZES.y
		);
		_str = "Resume";
		FontManager.fontMarker.draw(
				batcher, 
				_str, 
				guiCam.position.x - FontManager.fontMarker.getMultiLineBounds(_str).width/2f + 15, 
				guiCam.position.y + 80
		);
		
		batcher.draw(
				Assets.buttonRegion, 
				guiCam.position.x - Assets.BUTTON_RENDER_SIZES.x / 2f + 20, 
				guiCam.position.y - 40, 
				Assets.BUTTON_RENDER_SIZES.x,
				Assets.BUTTON_RENDER_SIZES.y
		);
		_str = "Back Main Menu";
		FontManager.fontMarker.draw(
				batcher, 
				_str, 
				guiCam.position.x - FontManager.fontMarker.getMultiLineBounds(_str).width/2f + 20, 
				guiCam.position.y + 10
		);
		
		FontManager.fontMarker.draw(
				batcher, 
				String.valueOf(lastScore), 
				SCREEN_WIDTH - FontManager.fontMarker
									.getMultiLineBounds(String.valueOf(lastScore)).width - 20, 
				SCREEN_HEIGHT - 5
		);
	}
	
	private void _renderGameEndScore() {
		_str = "Final Score: ";
		FontManager.fontMarker.draw(
				batcher, 
				_str, 
				guiCam.position.x - FontManager.fontMarker.getMultiLineBounds(_str).width, 
				guiCam.position.y + 70 + Assets.BUTTON_RENDER_SIZES.y * 2.5f
		);
		
		_str = String.valueOf(lastScore);
		FontManager.fontMarker.draw(
				batcher, 
				_str, 
				guiCam.position.x - FontManager.fontMarker.getMultiLineBounds(_str).width + 150, 
				guiCam.position.y + 70 + Assets.BUTTON_RENDER_SIZES.y * 2.5f
		);
		
		_str = "Personal Best: ";
		FontManager.fontMarker.draw(
				batcher, 
				_str, 
				guiCam.position.x - FontManager.fontMarker.getMultiLineBounds(_str).width, 
				guiCam.position.y + 70 + Assets.BUTTON_RENDER_SIZES.y * 1.5f
		);
		
		switch (Settings.difficulty) {
			case Settings.VERY_EASY:
				_str = String.valueOf(Settings.personalBestVeryEasy);
				break;
				
			case Settings.EASY:
				_str = String.valueOf(Settings.personalBestEasy);
				break;
				
			case Settings.MEDIUM:
				_str = String.valueOf(Settings.personalBestMedium);
				break;
				
			case Settings.HARD:
				_str = String.valueOf(Settings.personalBestHard);
				break;
				
			default:
				_str = "";
				break;
		}
		FontManager.fontMarker.draw(
				batcher, 
				_str, 
				guiCam.position.x - FontManager.fontMarker.getMultiLineBounds(_str).width + 150, 
				guiCam.position.y + 70 + Assets.BUTTON_RENDER_SIZES.y * 1.5f
		);
	}

	private void presentGameOver () {
		_renderBushes();
		
		_renderGameEndScore();
		
		//crashString = "Hit ground too hard!";
		FontManager.fontMarker.draw(
				batcher, 
				gameWorld.crashString, 
				guiCam.position.x - 
						FontManager.fontMarker.getMultiLineBounds(gameWorld.crashString).width/2f, 
				guiCam.position.y + 125 + Assets.BUTTON_RENDER_SIZES.y * 2.5f
		);
		
		batcher.draw(
				Assets.buttonRegion, 
				guiCam.position.x - Assets.BUTTON_RENDER_SIZES.x / 2f + 20, 
				guiCam.position.y + 30, 
				Assets.BUTTON_RENDER_SIZES.x,
				Assets.BUTTON_RENDER_SIZES.y
		);
		_str = "Main Menu";
		FontManager.fontMarker.draw(
				batcher, 
				_str, 
				guiCam.position.x - FontManager.fontMarker.getMultiLineBounds(_str).width/2f + 20, 
				guiCam.position.y + 80
		);
		
		batcher.draw(
				Assets.buttonRegion, 
				guiCam.position.x - Assets.BUTTON_RENDER_SIZES.x / 2f + 20, 
				guiCam.position.y - 40, 
				Assets.BUTTON_RENDER_SIZES.x,
				Assets.BUTTON_RENDER_SIZES.y
		);
		_str = "Retry";
		FontManager.fontMarker.draw(
				batcher, 
				_str, 
				guiCam.position.x - FontManager.fontMarker.getMultiLineBounds(_str).width/2f + 20, 
				guiCam.position.y + 10
		);
	}
	
	private float _alpha;
	private void _renderBushes() {
		if (_alpha < 1) {
			_alpha += _stateTimeGameEnd / 20f;
		}
		
		if (_alpha > 1) {
			_alpha = 1;
			_stateTimeGameEnd = 0;
		}
				
		bush1Sprite.draw(batcher, _alpha);
		bush2Sprite.draw(batcher, _alpha);
	}

	@Override
	public void pause () {
		if (state == GAME_RUNNING) {
			state = GAME_PAUSED;
			gameWorld.state = GameWorld.WORLD_STATE_PAUSE;
		}
	}

	@Override
	public void resume () {
	}

	@Override
	public void dispose () {
		gameWorld.dispose();
	}
	
	@Override
	public boolean keyUp (int keycode) {
		if (Input.Keys.R == keycode || Input.Keys.BACK == keycode) {
			pause();
			return true;
		}
		return false;
	}
	
	@Override
	public boolean touchDown (int x, int y, int pointer, int button) {
		guiCam.unproject(touchPoint.set(x, y, 0));
		
		if (touchPoint.y > GAME_BUTTONS_POINT.y) {
			return false;
		}
		
		touchLeftTime = -1;
		
		if (touchPoint.x >= GAME_BUTTONS_POINT.x) {
			isTouchRight = true;
			isTouchLeft  = false;
			
			gameWorld.bird.velocity.y *= 0.6f; // Attenuation WORLD_GRAVITY
		} else {
			if (Settings.threeOfCoinsCount > 0 && Bird.BIRD_STATE_FLY == gameWorld.bird.state) {
				Settings.threeOfCoinsCount--;
				isTouchLeft = true;
				touchLeftTime = 0;
			} else {
				isTouchLeft  = false;
			}
			
			isTouchRight = false;
		}
		
		return false;
	}

	@Override
	public boolean touchUp (int x, int y, int pointer, int button) {
		guiCam.unproject(touchPoint.set(x, y, 0));
		
		isTouchRight = false;
		isTouchLeft  = false;
		
		gameWorld.bird.velocity.y *= 0.6f; // Attenuation BIRD_VELOCITY
		
		return false;
	}
	
	private void restartLevel() {
		dispose();

		//Assets.successSound.stop();
		//Assets.failedSound.stop();
		
		gameWorld = new GameWorld(worldListener);
		worldRenderer = new WorldRenderer(batcher, gameWorld);
		
		isTouchLeft = false;
		isTouchRight = false;
		
		state = GAME_RUNNING;
		
		touchLeftTime = -1;
	}
}
