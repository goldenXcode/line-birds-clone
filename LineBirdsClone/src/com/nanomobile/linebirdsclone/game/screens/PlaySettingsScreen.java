package com.nanomobile.linebirdsclone.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.nanomobile.linebirdsclone.framework.Assets;
import com.nanomobile.linebirdsclone.framework.FontManager;
import com.nanomobile.linebirdsclone.framework.Game;
import com.nanomobile.linebirdsclone.framework.OverlapTester;
import com.nanomobile.linebirdsclone.framework.Screen;
import com.nanomobile.linebirdsclone.framework.Settings;

public class PlaySettingsScreen extends Screen {
	private final int BUTTON_OFFSET = 100;
	private final int TEXT_OFFSET = BUTTON_OFFSET + 50;
	private final int DELTA  = 80;
	
	Rectangle difficultyLeftBounds, difficultyRightBounds;
	Rectangle birdLeftBounds, birdRightBounds;
	Rectangle playBounds;
	
	Vector3 touchPoint;

	public PlaySettingsScreen(Game game) {
		super(game);

		difficultyLeftBounds = new Rectangle(
				guiCam.position.x - Assets.BUTTON_RENDER_SIZES.x / 2f, 
				guiCam.position.y + BUTTON_OFFSET, 
				Assets.BUTTON_RENDER_SIZES.x/2f, Assets.BUTTON_RENDER_SIZES.y
		);
		difficultyRightBounds = new Rectangle(
				guiCam.position.x, 
				guiCam.position.y + BUTTON_OFFSET, 
				Assets.BUTTON_RENDER_SIZES.x/2f, Assets.BUTTON_RENDER_SIZES.y
		);
		
		birdLeftBounds = new Rectangle(
				guiCam.position.x - Assets.BUTTON_RENDER_SIZES.x / 2f, 
				guiCam.position.y + (BUTTON_OFFSET - DELTA), 
				Assets.BUTTON_RENDER_SIZES.x/2f, Assets.BUTTON_RENDER_SIZES.y
		);
		birdRightBounds = new Rectangle(
				guiCam.position.x, 
				guiCam.position.y + (BUTTON_OFFSET - DELTA), 
				Assets.BUTTON_RENDER_SIZES.x/2f, Assets.BUTTON_RENDER_SIZES.y
		);
		
		playBounds = new Rectangle(
				guiCam.position.x - Assets.BUTTON_RENDER_SIZES.x / 2f, 
				guiCam.position.y + (BUTTON_OFFSET - DELTA * 3), 
				Assets.BUTTON_RENDER_SIZES.x, Assets.BUTTON_RENDER_SIZES.y
		);
		
		touchPoint = new Vector3();
	}

	@Override
	public void update (float deltaTime) {
		if (deltaTime > 0.1f) deltaTime = 0.1f;
		
		if (Gdx.input.justTouched()) {
			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

			if (OverlapTester.pointInRectangle(difficultyLeftBounds, touchPoint.x, touchPoint.y)) {
				//Assets.playSound(Assets.clickSound);
				if (Settings.difficulty > 0) {
					Settings.difficulty = --Settings.difficulty % 4;
				} else {
					Settings.difficulty = 3;
				}
				return;
			}
			
			if (OverlapTester.pointInRectangle(difficultyRightBounds, touchPoint.x, touchPoint.y)) {
				//Assets.playSound(Assets.clickSound);
				Settings.difficulty = ++Settings.difficulty % 4;
				return;
			}
			
			if (OverlapTester.pointInRectangle(birdLeftBounds, touchPoint.x, touchPoint.y)) {
				//Assets.playSound(Assets.clickSound);
				if (Settings.bird > 0) {
					Settings.bird = --Settings.bird % 7;
				} else {
					Settings.bird = 6;
				}
				return;
			}
			
			if (OverlapTester.pointInRectangle(birdRightBounds, touchPoint.x, touchPoint.y)) {
				//Assets.playSound(Assets.clickSound);
				Settings.bird = ++Settings.bird % 7;
				return;
			}
			
			if (OverlapTester.pointInRectangle(playBounds, touchPoint.x, touchPoint.y)) {
				//Assets.playSound(Assets.clickSound);
				game.setScreen(new GameScreen(game));
				return;
			}
		}
	}

	@Override
	public void present (float deltaTime) {
		GLCommon gl = Gdx.gl;
		gl.glClearColor(0, 0, 0, 1);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		guiCam.update();
		batcher.setProjectionMatrix(guiCam.combined);
		
		_renderBackground();
		_renderMenu();
	}
	
	private void _renderBackground() {
		batcher.disableBlending();
		batcher.begin();
		batcher.draw(
				Assets.backgroundRegion, 
				guiCam.position.x - Screen.SCREEN_WIDTH / 2, 
				guiCam.position.y - Screen.SCREEN_HEIGHT / 2, 
				Screen.SCREEN_WIDTH,
				Screen.SCREEN_HEIGHT
		);
		batcher.end();
	}
	
	private void _renderMenu() {
		batcher.enableBlending();
		batcher.begin();
		
		_renderButtons();
		_renderBushes();
		_renderTextButtons();
		_renderHeader();
		
		batcher.end();
	}
	
	private void _renderHeader() {
		batcher.draw(
				Assets.headerRegion, //region
				guiCam.position.x - Screen.SCREEN_WIDTH * 0.45f, //x
				guiCam.position.y + Screen.SCREEN_HEIGHT * 0.2f, //y
				Assets.HEADER_RENDER_SIZES.x/2f, //originX
				Assets.HEADER_RENDER_SIZES.y/2f, //originY
				Assets.HEADER_RENDER_SIZES.x, Assets.HEADER_RENDER_SIZES.y, //width & height 
				1, 1, //scaleX & scaleY
				20 //rotation
		);
	}
	
	private void _renderButtons() {
		batcher.draw(
				Assets.buttonArrowRegion, 
				guiCam.position.x - Assets.BUTTON_RENDER_SIZES.x / 2 + 20, 
				guiCam.position.y + BUTTON_OFFSET, 
				Assets.BUTTON_RENDER_SIZES.x,
				Assets.BUTTON_RENDER_SIZES.y
		);
		batcher.draw(
				Assets.buttonArrowRegion, 
				guiCam.position.x - Assets.BUTTON_RENDER_SIZES.x / 2 + 20, 
				guiCam.position.y + (BUTTON_OFFSET - DELTA), 
				Assets.BUTTON_RENDER_SIZES.x,
				Assets.BUTTON_RENDER_SIZES.y
		);
		batcher.draw(
				Assets.buttonRegion, 
				guiCam.position.x - Assets.BUTTON_RENDER_SIZES.x / 2 + 20, 
				guiCam.position.y + (BUTTON_OFFSET - DELTA * 3), 
				Assets.BUTTON_RENDER_SIZES.x,
				Assets.BUTTON_RENDER_SIZES.y
		);
	}
	
	private void _renderBushes() {
		batcher.draw(
				Assets.bushHorizontalRegion, 
				0, 0, 
				Assets.BUSH_HORIZONTAL_RENDER_SIZES.x,
				Assets.BUSH_HORIZONTAL_RENDER_SIZES.y
		);
		batcher.draw(
				Assets.bushVerticalRegion, 
				Screen.SCREEN_WIDTH - Assets.BUSH_VERTICAL_RENDER_SIZES.x, 0, 
				Assets.BUSH_VERTICAL_RENDER_SIZES.x,
				Assets.BUSH_VERTICAL_RENDER_SIZES.y
		);
	}
	
	private String _str;
	private Sprite _birdSprite;
	private void _renderTextButtons() {
		switch (Settings.difficulty) {
			case Settings.VERY_EASY:
				_str = "Very Easy";
				break;
				
			case Settings.EASY:
				_str = "Easy";
				break;
				
			case Settings.MEDIUM:
				_str = "Medium";
				break;
				
			case Settings.HARD:
				_str = "Hard";
				break;
				
			default:
				_str = "";
				break;
		}
		
		FontManager.fontMarker.draw(
				batcher, 
				_str, 
				guiCam.position.x - FontManager.fontMarker.getMultiLineBounds(_str).width/2f + 20, 
				guiCam.position.y + TEXT_OFFSET
		);
		
		switch (Settings.bird) {
			case Settings.KIRBY:
				_birdSprite = Assets.kirbySprite;
				_str = "Kirby, ";
				break;
				
			case Settings.BURPY:
				_birdSprite = Assets.burpySprite;
				_str = "Burpy, ";
				break;
				
			case Settings.HOUDINI:
				_birdSprite = Assets.houdiniSprite;
				_str = "Houdini, ";
				break;
				
			case Settings.PABLO:
				_birdSprite = Assets.pabloSprite;
				_str = "Pablo, ";
				break;
				
			case Settings.PEBBLES:
				_birdSprite = Assets.pebblesSprite;
				_str = "Pebbles, ";
				break;
				
			case Settings.PERKY:
				_birdSprite = Assets.perkySprite;
				_str = "Perky, ";
				break;
				
			case Settings.SMOKY:
				_birdSprite = Assets.smokySprite;
				_str = "Smoky, ";
				break;
				
			default:
				_birdSprite = Assets.pebblesSprite;
				_str = ", ";
				break;
		}
		
		_str += "has the ability to automatically retain his height";
		FontManager.fontMarker.draw(
				batcher, 
				_str, 
				guiCam.position.x - 350, 
				guiCam.position.y + (TEXT_OFFSET - DELTA * 2)
		);
		
		_birdSprite.setPosition(guiCam.position.x - 25, guiCam.position.y + (TEXT_OFFSET - DELTA * 2) + 10);
		_birdSprite.draw(batcher);
		
		FontManager.fontMarker.draw(
				batcher, 
				"Play", 
				guiCam.position.x - FontManager.fontMarker.getMultiLineBounds("Play").width/2f + 15, 
				guiCam.position.y + (TEXT_OFFSET - DELTA * 3)
		);
	}

	@Override
	public void pause () {
	}

	@Override
	public void resume () {
	}

	@Override
	public void dispose () {
	}
}