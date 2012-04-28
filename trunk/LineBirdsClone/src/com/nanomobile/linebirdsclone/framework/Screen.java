package com.nanomobile.linebirdsclone.framework;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Screen extends InputAdapter {
	public static final int SCREEN_WIDTH = 960;
	public static final int SCREEN_HEIGHT = 640;
	
	public Game game;
	
	public OrthographicCamera guiCam;
	public SpriteBatch batcher;

	public Screen (Game game) {
		Gdx.input.setInputProcessor(this);
		Gdx.input.setCatchBackKey(true);
		
		this.game = game;
		
		guiCam = new OrthographicCamera(SCREEN_WIDTH, SCREEN_HEIGHT);
		guiCam.position.set(SCREEN_WIDTH / 2f, SCREEN_HEIGHT / 2f, 0);
		batcher = new SpriteBatch();
	}

	public abstract void update (float deltaTime);

	public abstract void present (float deltaTime);

	public abstract void pause ();

	public abstract void resume ();

	public abstract void dispose ();
}
