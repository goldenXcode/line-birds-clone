package com.nanomobile.linebirdsclone.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.nanomobile.linebirdsclone.framework.Assets;
import com.nanomobile.linebirdsclone.framework.FontManager;
import com.nanomobile.linebirdsclone.framework.Game;
import com.nanomobile.linebirdsclone.framework.OverlapTester;
import com.nanomobile.linebirdsclone.framework.Screen;

public class FullVersionScreen extends Screen {
	public static final float END_Y = -(Assets.MORE_GAMES_RENDER_SIZES.y + 40);
	
	boolean isTouched;
	
	float prevY;
	
	Vector3 touchPoint;
	
	Rectangle backBounds;

	public FullVersionScreen (Game game) {
		super(game);
		
		isTouched = false;
		
		prevY = 0;
		touchPoint = new Vector3();
	}

	@Override
	public void update (float deltaTime) {
		if (deltaTime > 0.1f) deltaTime = 0.1f;
		
		if (Gdx.input.justTouched()) {
			guiCam.unproject(touchPoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
			
			backBounds = new Rectangle(
					guiCam.position.x + SCREEN_WIDTH / 2f - Assets.BUTTON_BACK_RENDER_SIZES.x, 
					guiCam.position.y - SCREEN_HEIGHT / 2f, 
					Assets.BUTTON_BACK_RENDER_SIZES.x,
					Assets.BUTTON_BACK_RENDER_SIZES.y
			);
			
			if (OverlapTester.pointInRectangle(backBounds, touchPoint.x, touchPoint.y)) {
				game.setScreen(new MainMenuScreen(game));
				return;
			}
		}
	}
	
	@Override
	public boolean touchDragged(int x, int y, int pointer) {
		if (!isTouched) {
			return false;
		}
		
		_cameraUpdate(y);
		
		return false;
	}
	
	private void _cameraUpdate(int y) {
		guiCam.position.y += (y - prevY) * 0.8f;
		prevY = y;
		
		/** _C_A_M_E_R_A_  _L_I_M_I_T_S_ **/
		if (guiCam.position.y < END_Y + SCREEN_HEIGHT / 2f) {
			guiCam.position.y = END_Y + SCREEN_HEIGHT / 2f;
		} else if (guiCam.position.y > SCREEN_HEIGHT / 2f) {
			guiCam.position.y = SCREEN_HEIGHT / 2f;
		}
		
		guiCam.update();
	}
	
	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		prevY = y;
		isTouched = true;
		
		return false;
	}
	
	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		isTouched = false;
		
		return false;
	}

	@Override
	public void present (float deltaTime) {
		GLCommon gl = Gdx.gl;
		gl.glClearColor(0, 0, 0, 1);
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		guiCam.update();
		batcher.setProjectionMatrix(guiCam.combined);
		
		_renderBackground();
		_renderAdvertisment();
	}
	
	private void _renderBackground() {
		batcher.disableBlending();
		batcher.begin();
		batcher.draw(
				Assets.backgroundRegion, 
				guiCam.position.x - SCREEN_WIDTH / 2f, 
				guiCam.position.y - SCREEN_HEIGHT / 2f,
				SCREEN_WIDTH,
				SCREEN_HEIGHT
		);
		batcher.end();
	}
	
	private void _renderAdvertisment() {
		batcher.enableBlending();
		batcher.begin();
		batcher.draw(
				Assets.buttonDjinnworksRegion, 
				SCREEN_WIDTH / 2f - Assets.BUTTON_DJINNWORKS_RENDER_SIZES.x / 2f, 
				SCREEN_HEIGHT - Assets.BUTTON_DJINNWORKS_RENDER_SIZES.y, 
				Assets.BUTTON_DJINNWORKS_RENDER_SIZES.x,
				Assets.BUTTON_DJINNWORKS_RENDER_SIZES.y
		);
		
		_renderMoreGames();
		
		batcher.draw(
				Assets.buttonBackRegion, 
				guiCam.position.x + SCREEN_WIDTH / 2f - Assets.BUTTON_BACK_RENDER_SIZES.x, 
				guiCam.position.y - SCREEN_HEIGHT / 2f, 
				Assets.BUTTON_BACK_RENDER_SIZES.x,
				Assets.BUTTON_BACK_RENDER_SIZES.y
		);
		batcher.end();
	}
	
	private void _renderMoreGames() {
		_renderFirstGame();
		_renderSecondGame();
		_renderThirdGame();
	}
	
	private String _str = "";
	private void _renderFirstGame() {
		batcher.draw(
				Assets.moreGamesStickStuntBikerRegion, 
				60, 
				SCREEN_HEIGHT - Assets.MORE_GAMES_RENDER_SIZES.y - 60,
				Assets.MORE_GAMES_RENDER_SIZES.x,
				Assets.MORE_GAMES_RENDER_SIZES.y
		);
		
		FontManager.fontBig.drawMultiLine(
				batcher, "STIC STUNT\nBIKER", 
				Assets.MORE_GAMES_RENDER_SIZES.x + 100, 
				SCREEN_HEIGHT - 60
		);
		
		_str = "bike fun and challenging tracks using your\n";
		_str += "destructible stick biker.\n";
		_str += "nr. 1 racing game and top 100 in the app store!";
		FontManager.fontSmall.drawMultiLine(
				batcher, _str, 
				Assets.MORE_GAMES_RENDER_SIZES.x + 100, 
				SCREEN_HEIGHT - 140
		);
		
		batcher.draw(
				Assets.buttonBuyRegion, 
				Assets.MORE_GAMES_RENDER_SIZES.x + 100, 
				SCREEN_HEIGHT - Assets.MORE_GAMES_RENDER_SIZES.y - 60,
				Assets.BUTTON_BUY_RENDER_SIZES.x,
				Assets.BUTTON_BUY_RENDER_SIZES.y
		);
	}
	
	private void _renderSecondGame() {
		batcher.draw(
				Assets.moreGamesLineBirdsRegion, 
				60, 
				SCREEN_HEIGHT - Assets.MORE_GAMES_RENDER_SIZES.y * 2 - 100,
				Assets.MORE_GAMES_RENDER_SIZES.x,
				Assets.MORE_GAMES_RENDER_SIZES.y
		);
		
		FontManager.fontBig.drawMultiLine(
				batcher, "LINE BIRDS", 
				Assets.MORE_GAMES_RENDER_SIZES.x + 100, 
				SCREEN_HEIGHT - Assets.MORE_GAMES_RENDER_SIZES.y - 110
		);
		
		_str = "guide beautiful birds through a world full of\n";
		_str += "danger. unlock various birds with cool abilities\n";
		_str += "and earn a lot of achievements.";
		FontManager.fontSmall.drawMultiLine(
				batcher, _str, 
				Assets.MORE_GAMES_RENDER_SIZES.x + 100, 
				SCREEN_HEIGHT - Assets.MORE_GAMES_RENDER_SIZES.y - 180
		);
		
		batcher.draw(
				Assets.buttonBuyRegion, 
				Assets.MORE_GAMES_RENDER_SIZES.x + 100, 
				SCREEN_HEIGHT - Assets.MORE_GAMES_RENDER_SIZES.y * 2 - 100,
				Assets.BUTTON_BUY_RENDER_SIZES.x,
				Assets.BUTTON_BUY_RENDER_SIZES.y
		);
	}
	
	private void _renderThirdGame() {
		batcher.draw(
				Assets.moreGamesStickCliffDivingRegion, 
				60, 
				SCREEN_HEIGHT - Assets.MORE_GAMES_RENDER_SIZES.y * 3 - 140, 
				Assets.MORE_GAMES_RENDER_SIZES.x,
				Assets.MORE_GAMES_RENDER_SIZES.y
		);
		
		FontManager.fontBig.drawMultiLine(
				batcher, "STICK CLIFF\nDIVING", 
				Assets.MORE_GAMES_RENDER_SIZES.x + 100, 
				SCREEN_HEIGHT - Assets.MORE_GAMES_RENDER_SIZES.y * 2 - 140
		);
		
		_str = "get your kicks and jump down the highest and most\n";
		_str += "dangerous cliffs all over the planet!\n";
		_str += "a top 100 hit with millions of players!";
		FontManager.fontSmall.drawMultiLine(
				batcher, _str, 
				Assets.MORE_GAMES_RENDER_SIZES.x + 100, 
				SCREEN_HEIGHT - Assets.MORE_GAMES_RENDER_SIZES.y * 2 - 230
		);
		
		batcher.draw(
				Assets.buttonBuyRegion, 
				Assets.MORE_GAMES_RENDER_SIZES.x + 100, 
				SCREEN_HEIGHT - Assets.MORE_GAMES_RENDER_SIZES.y * 3 - 140,
				Assets.BUTTON_BUY_RENDER_SIZES.x,
				Assets.BUTTON_BUY_RENDER_SIZES.y
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