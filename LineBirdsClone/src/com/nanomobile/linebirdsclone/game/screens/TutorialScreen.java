package com.nanomobile.linebirdsclone.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.nanomobile.linebirdsclone.framework.Assets;
import com.nanomobile.linebirdsclone.framework.Game;
import com.nanomobile.linebirdsclone.framework.OverlapTester;
import com.nanomobile.linebirdsclone.framework.Screen;

public class TutorialScreen extends Screen {
	int		page;
	Vector3 touchPoint;
	
	Rectangle nextBounds;

	public TutorialScreen (Game game) {
		super(game);
		
		page = 1;
		touchPoint = new Vector3();
		
		nextBounds = new Rectangle(
				370, 50, Assets.BUTTON_RENDER_SIZES.x/2f, Assets.BUTTON_RENDER_SIZES.y
		);
	}

	@Override
	public void update (float deltaTime) {
		if (deltaTime > 0.1f) deltaTime = 0.1f;
		
		if (Gdx.input.justTouched()) {
			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

			if (OverlapTester.pointInRectangle(nextBounds, touchPoint.x, touchPoint.y)) {
				//Assets.playSound(Assets.clickSound);
				if (page == 1) {
					page++;
				} else if (page == 2) {
					game.setScreen(new MainMenuScreen(game));
				}
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
		_renderTutorial();
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
	
	TextureRegion tmpRegion;
	private void _renderTutorial() {
		switch (page) {
			case 1:
				tmpRegion = Assets.tutorial1Region;
				break;
			
			case 2:
				tmpRegion = Assets.tutorial2Region;
				break;
				
			default:
				tmpRegion = Assets.tutorial1Region;
				break;
		}
			
		batcher.enableBlending();
		batcher.begin();
		batcher.draw(
				tmpRegion, 
				guiCam.position.x - Screen.SCREEN_WIDTH / 2, 
				guiCam.position.y - Screen.SCREEN_HEIGHT / 2, 
				Screen.SCREEN_WIDTH,
				Screen.SCREEN_HEIGHT
		);
		batcher.end();
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