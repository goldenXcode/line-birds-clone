package com.nanomobile.linebirdsclone.framework;

import com.badlogic.gdx.assets.loaders.BitmapFontLoader.BitmapFontParameter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class FontManager {
	public static final String FONT_DIR_PATH = Assets.DATA_DIR_PATH + "font/";
	
	public static final float FONT_SCALE_SIZE = 1f;
	
	/*******************************************************************************/
	
	public static BitmapFont fontBig, fontSmall, fontMarker;
	
	static BitmapFontParameter bitmapFontParam = new BitmapFontParameter();
	
	public static void loadAssets() {
		bitmapFontParam.maxFilter = TextureFilter.Linear;
		bitmapFontParam.minFitler = TextureFilter.Linear;
		
		Assets.assetManager.load(
				FONT_DIR_PATH + "americanPurposeBig.fnt", 
				BitmapFont.class,
				bitmapFontParam
		);
		Assets.assetManager.load(FONT_DIR_PATH + "americanPurposeBig.png", Texture.class);
		
		Assets.assetManager.load(
				FONT_DIR_PATH + "americanPurposeSmall.fnt", 
				BitmapFont.class,
				bitmapFontParam
		);
		Assets.assetManager.load(FONT_DIR_PATH + "americanPurposeSmall.png", Texture.class);
		
		Assets.assetManager.load(
				FONT_DIR_PATH + "markerfelt14.fnt", 
				BitmapFont.class,
				bitmapFontParam
		);
		Assets.assetManager.load(FONT_DIR_PATH + "markerfelt14.png", Texture.class);
	}
	
	public static boolean areFontsLoaded;
	public static boolean isLoaded() {
		if (null == Assets.assetManager)  {
			return false;
		}
		
		areFontsLoaded = true;
		
		if (!Assets.assetManager.isLoaded(FONT_DIR_PATH + "americanPurposeBig.fnt")) {
			areFontsLoaded = false;
		}
		if (!Assets.assetManager.isLoaded(FONT_DIR_PATH + "americanPurposeBig.png")) {
			areFontsLoaded = false;
		}
		
		if (!Assets.assetManager.isLoaded(FONT_DIR_PATH + "americanPurposeSmall.fnt")) {
			areFontsLoaded = false;
		}
		if (!Assets.assetManager.isLoaded(FONT_DIR_PATH + "americanPurposeSmall.png")) {
			areFontsLoaded = false;
		}
		
		if (!Assets.assetManager.isLoaded(FONT_DIR_PATH + "markerfelt14.fnt")) {
			areFontsLoaded = false;
		}
		if (!Assets.assetManager.isLoaded(FONT_DIR_PATH + "markerfelt14.png")) {
			areFontsLoaded = false;
		}
		
		return areFontsLoaded;
	}
	
	public static void unloadAssets() {
		Assets.assetManager.unload(FONT_DIR_PATH + "americanPurposeBig.fnt");
		Assets.assetManager.unload(FONT_DIR_PATH + "americanPurposeBig.png");
		
		Assets.assetManager.unload(FONT_DIR_PATH + "americanPurposeSmall.fnt");
		Assets.assetManager.unload(FONT_DIR_PATH + "americanPurposeSmall.png");
		
		Assets.assetManager.unload(FONT_DIR_PATH + "markerfelt14.fnt");
		Assets.assetManager.unload(FONT_DIR_PATH + "markerfelt14.png");
	}

	public static void load () {
		fontBig = Assets.assetManager.get(FONT_DIR_PATH + "americanPurposeBig.fnt", BitmapFont.class);
		fontBig.setScale(FONT_SCALE_SIZE);
		
		//setFilter(assetManager.get(DATA_DIR_PATH + "atlas.png", Texture.class));
		fontSmall = Assets.assetManager.get(FONT_DIR_PATH + "americanPurposeSmall.fnt", BitmapFont.class);
		fontSmall.setScale(FONT_SCALE_SIZE - 0.05f);
		
		fontMarker = Assets.assetManager.get(FONT_DIR_PATH + "markerfelt14.fnt", BitmapFont.class);
		fontMarker.setScale(FONT_SCALE_SIZE);
		fontMarker.setColor(Color.BLACK);
	}
	
	public static void dispose() {
		fontBig.dispose();
		fontSmall.dispose();
		fontMarker.dispose();
	}
}