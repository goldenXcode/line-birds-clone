package com.nanomobile.linebirdsclone.framework;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class ParticleSystem {
	static final int EFFECT_TIME_STOP = -1;
	static final int EFFECT_TIME_START = 0;
	
	static final float EFFECT_DURATION_SECONDS = 2;
	
	static final String EFFECT_PATH = Assets.DATA_DIR_PATH + "particles/";
	
	public ParticleEffect effect;
	public Vector2 effectPos;
	public String effectFile = "";
	protected float effectTime = EFFECT_TIME_STOP;
	
	public float effectDuration;
	
	private boolean completeEffect;
	private boolean enabledEffect;

	public ParticleSystem (SpriteBatch batch, String fileName) {
		effectFile = EFFECT_PATH + fileName;
		
		effect = new ParticleEffect();
		effect.loadEmitters(Gdx.files.internal(effectFile));
		//Texture.setEnforcePotImages(false);
		
		effectPos = null;
		
		effectTime = EFFECT_TIME_STOP;
		
		effectDuration = EFFECT_DURATION_SECONDS;
	}
	
	public void updateEffect(Vector2 pos) {
		effect.setPosition(pos.x, pos.y);
		effectPos = new Vector2(pos);
		effectTime = EFFECT_TIME_START;
	}
	
	public void renderEffect(SpriteBatch batch) {
		if (effectTime <= effectDuration && effectTime != EFFECT_TIME_STOP) {
			effectTime += Gdx.graphics.getDeltaTime();
		} else if (effectTime > effectDuration) {				 
			effectTime = EFFECT_TIME_STOP;
			effect.loadEmitters(Gdx.files.internal(effectFile));
		}
		
		enabledEffect = (effectTime != EFFECT_TIME_STOP);
		if (!enabledEffect) {
			return;
		}
		
		completeEffect = true;
		for (ParticleEmitter emitter : effect.getEmitters()) {
			if (emitter.getSprite() == null && emitter.getImagePath() != null) {
				loadImage(emitter);
			}
			
			if (emitter.getSprite() != null) {
				emitter.getSprite().setColor(Color.BLUE);
				emitter.draw(batch, Gdx.graphics.getDeltaTime());
			}
			
			if (emitter.isContinuous()) completeEffect = false;
			if (!emitter.isComplete()) completeEffect = false;
		}
		if (completeEffect) effect.start();
	}
	
	private String emitterImagePath;
	private FileHandle emitterFile;
	private void loadImage (ParticleEmitter emitter) {
		emitterImagePath = emitter.getImagePath();
		try {
			emitterFile = Gdx.files.getFileHandle(EFFECT_PATH + emitterImagePath, FileType.Internal);

			emitter.setSprite(new Sprite(new Texture(emitterFile)));
		} catch (GdxRuntimeException ex) {
			ex.printStackTrace();
			emitter.setImagePath(null);
		}
	}
}