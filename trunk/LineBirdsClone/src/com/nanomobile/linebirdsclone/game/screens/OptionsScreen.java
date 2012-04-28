package com.nanomobile.linebirdsclone.game.screens;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.nanomobile.linebirdsclone.framework.Assets;
import com.nanomobile.linebirdsclone.framework.BirdMenu;
import com.nanomobile.linebirdsclone.framework.FontManager;
import com.nanomobile.linebirdsclone.framework.Game;
import com.nanomobile.linebirdsclone.framework.OverlapTester;
import com.nanomobile.linebirdsclone.framework.Screen;
import com.nanomobile.linebirdsclone.framework.Settings;

public class OptionsScreen extends Screen {
	private final int BUTTON_OFFSET = 100;
	private final int TEXT_OFFSET = BUTTON_OFFSET + 50;
	private final int DELTA  = 80;
	
	private BirdMenu burpy, houdini, kirby, pablo, pebbles, perky, smoky;
	private List<BirdMenu> birdsList = null;
	
	Rectangle soundBounds;
	Rectangle musicBounds;
	Rectangle backBounds;
	
	Vector3 touchPoint;

	public OptionsScreen(Game game) {
		super(game);
		
		_createBirds();

		soundBounds = new Rectangle(
				guiCam.position.x - Assets.BUTTON_RENDER_SIZES.x / 2f, 
				guiCam.position.y + BUTTON_OFFSET, 
				Assets.BUTTON_RENDER_SIZES.x, Assets.BUTTON_RENDER_SIZES.y
		);
		
		musicBounds = new Rectangle(
				guiCam.position.x - Assets.BUTTON_RENDER_SIZES.x / 2f, 
				guiCam.position.y + (BUTTON_OFFSET - DELTA), 
				Assets.BUTTON_RENDER_SIZES.x, Assets.BUTTON_RENDER_SIZES.y
		);
		
		backBounds = new Rectangle(
				guiCam.position.x - Assets.BUTTON_RENDER_SIZES.x / 2f, 
				guiCam.position.y + (BUTTON_OFFSET - DELTA * 2), 
				Assets.BUTTON_RENDER_SIZES.x, Assets.BUTTON_RENDER_SIZES.y
		);
		
		touchPoint = new Vector3();
	}
	
	private void _createBirds() {
		if (birdsList == null) {
			birdsList = new ArrayList<BirdMenu>();
		} else {
			if (!birdsList.isEmpty()) {
				birdsList.clear();
			}
		}
		
		burpy = new BirdMenu();
		birdsList.add(burpy);
		
		houdini = new BirdMenu();
		birdsList.add(houdini);
		
		kirby = new BirdMenu();
		birdsList.add(kirby);
		
		pablo = new BirdMenu();
		birdsList.add(pablo);
		
		pebbles = new BirdMenu();
		birdsList.add(pebbles);
		
		perky = new BirdMenu();
		birdsList.add(perky);
		
		smoky = new BirdMenu();
		birdsList.add(smoky);
	}

	@Override
	public void update (float deltaTime) {
		if (deltaTime > 0.1f) deltaTime = 0.1f;
		
		for (BirdMenu bird : birdsList) {
			bird.update(deltaTime);
		}
		
		if (Gdx.input.justTouched()) {
			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

			if (OverlapTester.pointInRectangle(soundBounds, touchPoint.x, touchPoint.y)) {
				//Assets.playSound(Assets.clickSound);
				Settings.soundEnabled = !Settings.soundEnabled;
				return;
			}
			
			if (OverlapTester.pointInRectangle(musicBounds, touchPoint.x, touchPoint.y)) {
				//Assets.playSound(Assets.clickSound);
				Settings.musicEnabled = !Settings.musicEnabled;
				return;
			}
			
			if (OverlapTester.pointInRectangle(backBounds, touchPoint.x, touchPoint.y)) {
				//Assets.playSound(Assets.clickSound);
				game.setScreen(new MainMenuScreen(game));
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
				guiCam.position.x - Screen.SCREEN_WIDTH / 2f, 
				guiCam.position.y - Screen.SCREEN_HEIGHT / 2f, 
				Screen.SCREEN_WIDTH,
				Screen.SCREEN_HEIGHT
		);
		batcher.end();
	}
	
	private void _renderMenu() {
		batcher.enableBlending();
		batcher.begin();
		
		_renderButtons();
		_renderBirds();
		_renderBushes();
		_renderTextButtons();
		_renderHeader();
		
		batcher.end();
	}
	
	TextureRegion keyFrame;
	private void _renderBirds() {
		/** B U R P Y **/
		keyFrame = Assets.birdBurpyAnimation.getKeyFrame(burpy.stateTime, true);
        batcher.draw(
        		keyFrame, burpy.position.x, burpy.position.y, 85/2f, 85/2f, 85, 85, 1, 1,
        		burpy.rotationDegree
        );
        batcher.draw(
        		Assets.beakB0Region, burpy.position.x, burpy.position.y, 85/2f, 85/2f, 85, 85, 1, 1,
        		burpy.rotationDegree
        );
        
        /** H O U D I N I **/
        keyFrame = Assets.birdHoudiniAnimation.getKeyFrame(houdini.stateTime, true);
        batcher.draw(
        		keyFrame, houdini.position.x, houdini.position.y, 85/2f, 85/2f, 85, 85, 1, 1,
        		houdini.rotationDegree
        );
        batcher.draw(
        		Assets.beakP0Region, houdini.position.x, houdini.position.y, 85/2f, 85/2f, 85, 85, 1, 1,
        		houdini.rotationDegree
        );
        
        /** K I R B Y **/
        keyFrame = Assets.birdKirbyAnimation.getKeyFrame(kirby.stateTime, true);
        batcher.draw(
        		keyFrame, kirby.position.x, kirby.position.y, 85/2f, 85/2f, 85, 85, 1, 1,
        		kirby.rotationDegree
        );
        batcher.draw(
        		Assets.beakS0Region, kirby.position.x, kirby.position.y, 85/2f, 85/2f, 85, 85, 1, 1,
        		kirby.rotationDegree
        );
        
        /** P A B L O **/
        keyFrame = Assets.birdPabloAnimation.getKeyFrame(pablo.stateTime, true);
        batcher.draw(
        		keyFrame, pablo.position.x, pablo.position.y, 85/2f, 85/2f, 85, 85, 1, 1,
        		pablo.rotationDegree
        );
        batcher.draw(
        		Assets.beakB1Region, pablo.position.x, pablo.position.y, 85/2f, 85/2f, 85, 85, 1, 1,
        		pablo.rotationDegree
        );
        
        /** P E B B L E S **/
        keyFrame = Assets.birdPebblesAnimation.getKeyFrame(pebbles.stateTime, true);
        batcher.draw(
        		keyFrame, pebbles.position.x, pebbles.position.y, 85/2f, 85/2f, 85, 85, 1, 1,
        		pebbles.rotationDegree
        );
        batcher.draw(
        		Assets.beakS1Region, pebbles.position.x, pebbles.position.y, 85/2f, 85/2f, 85, 85, 1, 1,
        		pebbles.rotationDegree
        );
        
        /** P E R K Y **/
        keyFrame = Assets.birdPerkyAnimation.getKeyFrame(perky.stateTime, true);
        batcher.draw(
        		keyFrame, perky.position.x, perky.position.y, 85/2f, 85/2f, 85, 85, 1, 1,
        		perky.rotationDegree
        );
        batcher.draw(
        		Assets.beakS1Region, perky.position.x, perky.position.y, 85/2f, 85/2f, 85, 85, 1, 1,
        		perky.rotationDegree
        );
        
        /** S M O K Y **/
        keyFrame = Assets.birdSmokyAnimation.getKeyFrame(smoky.stateTime, true);
        batcher.draw(
        		keyFrame, smoky.position.x, smoky.position.y, 85/2f, 85/2f, 85, 85, 1, 1,
        		smoky.rotationDegree
        );
        batcher.draw(
        		Assets.beakB2Region, smoky.position.x, smoky.position.y, 85/2f, 85/2f, 85, 85, 1, 1,
        		smoky.rotationDegree
        );
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
				guiCam.position.x - Assets.BUTTON_RENDER_SIZES.x / 2f + 20, 
				guiCam.position.y + BUTTON_OFFSET, 
				Assets.BUTTON_RENDER_SIZES.x,
				Assets.BUTTON_RENDER_SIZES.y
		);
		batcher.draw(
				Assets.buttonArrowRegion, 
				guiCam.position.x - Assets.BUTTON_RENDER_SIZES.x / 2f + 20, 
				guiCam.position.y + (BUTTON_OFFSET - DELTA), 
				Assets.BUTTON_RENDER_SIZES.x,
				Assets.BUTTON_RENDER_SIZES.y
		);
		batcher.draw(
				Assets.buttonRegion, 
				guiCam.position.x - Assets.BUTTON_RENDER_SIZES.x / 2f + 20, 
				guiCam.position.y + (BUTTON_OFFSET - DELTA * 2), 
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
	
	private void _renderTextButtons() {
		FontManager.fontMarker.draw(
				batcher, 
				"Sound " + (Settings.soundEnabled ? "on" : "off"), 
				guiCam.position.x - 35, 
				guiCam.position.y + TEXT_OFFSET
		);
		
		FontManager.fontMarker.setScale(FontManager.FONT_SCALE_SIZE + 0.075f);
		FontManager.fontMarker.draw(
				batcher, 
				"Game Music " + (Settings.musicEnabled ? "on" : "off"), 
				guiCam.position.x - 70, 
				guiCam.position.y + (TEXT_OFFSET - DELTA)
		);
		FontManager.fontMarker.setScale(FontManager.FONT_SCALE_SIZE + 0.2f);
		
		FontManager.fontMarker.draw(
				batcher, 
				"Back", 
				guiCam.position.x - 20, 
				guiCam.position.y + (TEXT_OFFSET - DELTA * 2)
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