package com.nanomobile.linebirdsclone.framework;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetErrorListener;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader;
import com.badlogic.gdx.assets.loaders.SoundLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Assets {
	public static final String DATA_DIR_PATH  = "data/";
	//public static final String MUSIC_DIR_PATH = DATA_DIR_PATH + "music/";
	public static final String SOUND_DIR_PATH = DATA_DIR_PATH + "sounds/";
	
	public static final float SOUND_VOLUME = 0.75f;
	
	public static final float BIRDS_ANIMATION_FRAME_DURATION = 0.08f;
	public static final float AIRPLANE_ANIMATION_FRAME_DURATION = 0.25f;
	
	public static final Vector2 BUTTON_RENDER_SIZES = new Vector2(399, 58);
	public static final Vector2 BUTTON_BACK_RENDER_SIZES = new Vector2(155, 60);
	public static final Vector2 BUTTON_BUY_RENDER_SIZES = new Vector2(155, 60);
	public static final Vector2 BUTTON_DJINNWORKS_RENDER_SIZES = new Vector2(525, 37);
	
	public static final Vector2 HEADER_RENDER_SIZES = new Vector2(289, 68);
	public static final Vector2 BUSH_HORIZONTAL_RENDER_SIZES = new Vector2(656, 228);
	public static final Vector2 BUSH_VERTICAL_RENDER_SIZES = new Vector2(304, 640);
	
	public static final Vector2 MORE_GAMES_RENDER_SIZES = new Vector2(380, 253);
	
	/*******************************************************************************/
	
	public static AssetManager assetManager = null;
	
	public static Texture atlas, bushAtlas, spriteSheet, tutorial1, tutorial2;
	
	public static TextureRegion backgroundRegion;
	public static TextureRegion backgroundGameRegion;
	public static TextureRegion headerRegion;
	
	public static TextureRegion featherRegion;
	public static TextureRegion burpyRegion;
	public static TextureRegion houdiniRegion;
	public static TextureRegion kirbyRegion;
	public static TextureRegion pabloRegion;
	public static TextureRegion smokyRegion;
	public static TextureRegion perkyRegion;
	public static TextureRegion pebblesRegion;
	
	public static TextureRegion buttonRegion;
	public static TextureRegion buttonArrowRegion;
	public static TextureRegion buttonBackRegion;
	public static TextureRegion buttonBuyRegion;
	public static TextureRegion buttonDjinnworksRegion;
	
	public static TextureRegion bushHorizontalRegion, bushVerticalRegion;
	
	public static TextureRegion moreGamesLineBirdsRegion;
	public static TextureRegion moreGamesStickCliffDivingRegion;
	public static TextureRegion moreGamesStickStuntBikerRegion;
	
	public static TextureRegion tutorial1Region, tutorial2Region;
	
	public static TextureRegion beakB0Region, beakB1Region, beakB2Region, beakB3Region,
								beakB4Region, beakB5Region, beakP0Region, beakP1Region,
								beakP2Region, beakP3Region, beakP4Region, beakP5Region,
								beakS0Region, beakS1Region, beakS2Region, beakS3Region,
								beakS4Region, beakS5Region;
	
	public static TextureRegion birdBurpyStandRegion, birdHoudiniStandRegion, birdKirbyStandRegion, 
								birdPabloStandRegion, birdPebblesStandRegion, birdPerkyStandRegion,
								birdSmokyStandRegion;
	
	public static TextureRegion coinRegion, pauseRegion, spikeRegion, starEmptyRegion, treeRegion, xRegion;
	
	public static Animation birdBurpyAnimation, birdHoudiniAnimation, birdKirbyAnimation,
							birdPabloAnimation, birdPebblesAnimation, birdPerkyAnimation,
							birdSmokyAnimation, planeAnimation;
	
	public static Sprite featherSprite;
	public static Sprite burpySprite;
	public static Sprite houdiniSprite;
	public static Sprite kirbySprite;
	public static Sprite pabloSprite;
	public static Sprite smokySprite;
	public static Sprite perkySprite;
	public static Sprite pebblesSprite;
	
	//public static Music music;
	
	public static Sound successSound;
	public static Sound failedSound;
	
	private static AssetErrorListener errorListener = new AssetErrorListener() {
		@Override
		public void error (String fileName, Class type, Throwable t) {
			Gdx.app.error("LineBirdsClone AssetManager", "couldn't load asset '" + fileName + "'", (Exception)t);
		}
	};
	
	public static void createAssetManager() {
		InternalFileHandleResolver resolver = new InternalFileHandleResolver();
		
		assetManager = new AssetManager();
		
		assetManager.setLoader(Texture.class, new TextureLoader(resolver));
		//assetManager.setLoader(Music.class, new MusicLoader(resolver));
		assetManager.setLoader(Sound.class, new SoundLoader(resolver));
		assetManager.setLoader(BitmapFont.class, new BitmapFontLoader(resolver));
		
		assetManager.setErrorListener(errorListener);
		
		loadAssets();
		
		//assetManager.finishLoading();
		while (!assetManager.update());
	}
	
	private static void loadAssets() {
		assetManager.load(DATA_DIR_PATH + "atlas.png", Texture.class);
		assetManager.load(DATA_DIR_PATH + "bushAtlas.png", Texture.class);
		assetManager.load(DATA_DIR_PATH + "gameEngineSpriteSheetDefault.png", Texture.class);
		assetManager.load(DATA_DIR_PATH + "tutorial1.png", Texture.class);
		assetManager.load(DATA_DIR_PATH + "tutorial2.png", Texture.class);
		
		//assetManager.load(MUSIC_DIR_PATH + "find_the_truth.mp3", Music.class);
		
		//assetManager.load(SOUND_DIR_PATH + "cartoon003.mp3", Sound.class);
		//assetManager.load(SOUND_DIR_PATH + "laugh_5.mp3", Sound.class);
		
		FontManager.loadAssets();
	}
	
	public static boolean isLoaded;
	public static boolean isLoaded() {
		if (null == assetManager)  {
			return false;
		}
		
		isLoaded = true;
		
		if (!assetManager.isLoaded(DATA_DIR_PATH + "atlas.png")) {
			isLoaded = false;
		}
		if (!assetManager.isLoaded(DATA_DIR_PATH + "bushAtlas.png")) {
			isLoaded = false;
		}
		if (!assetManager.isLoaded(DATA_DIR_PATH + "gameEngineSpriteSheetDefault.png")) {
			isLoaded = false;
		}
		if (!assetManager.isLoaded(DATA_DIR_PATH + "tutorial1.png")) {
			isLoaded = false;
		}
		if (!assetManager.isLoaded(DATA_DIR_PATH + "tutorial2.png")) {
			isLoaded = false;
		}
		/*if (!assetManager.isLoaded(MUSIC_DIR_PATH + "find_the_truth.mp3")) {
			isLoaded = false;
		}
		if (!assetManager.isLoaded(SOUND_DIR_PATH + "cartoon003.mp3")) {
			isLoaded = false;
		}
		if (!assetManager.isLoaded(SOUND_DIR_PATH + "laugh_5.mp3")) {
			isLoaded = false;
		}*/
		
		isLoaded = FontManager.isLoaded();
		
		return isLoaded;
	}
	
	private static void unloadAssetManager() {
		assetManager.unload(DATA_DIR_PATH + "atlas.png");
		assetManager.unload(DATA_DIR_PATH + "bushAtlas.png");
		assetManager.unload(DATA_DIR_PATH + "gameEngineSpriteSheetDefault.png");
		assetManager.unload(DATA_DIR_PATH + "tutorial1.png");
		assetManager.unload(DATA_DIR_PATH + "tutorial2.png");
		
		//assetManager.unload(MUSIC_DIR_PATH + "find_the_truth.mp3");
		//assetManager.unload(SOUND_DIR_PATH + "cartoon003.mp3");
		//assetManager.unload(SOUND_DIR_PATH + "laugh_5.mp3");
		
		FontManager.unloadAssets();
		
		assetManager.dispose();
		assetManager = null;
	}
	
	public static Texture setFilter (Texture texture) {
		texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
		return texture;
	}

	public static void load () {
		createAssetManager();
		//while(!isLoaded());
		
		atlas = setFilter(assetManager.get(DATA_DIR_PATH + "atlas.png", Texture.class));
		bushAtlas = setFilter(assetManager.get(DATA_DIR_PATH + "bushAtlas.png", Texture.class));
		spriteSheet = setFilter(assetManager.get(DATA_DIR_PATH + "gameEngineSpriteSheetDefault.png", 
																					Texture.class));
		tutorial1 = setFilter(assetManager.get(DATA_DIR_PATH + "tutorial1.png", Texture.class));
		tutorial2 = setFilter(assetManager.get(DATA_DIR_PATH + "tutorial2.png", Texture.class));
		
		backgroundRegion = new TextureRegion(atlas, 0, 0, 960, 640);
		backgroundGameRegion = new TextureRegion(spriteSheet, 0, 622, 480, 320);
		headerRegion = new TextureRegion(atlas, 0, 640 + 85 + 58*2 + 60, 289, 68);
		
		featherRegion = new TextureRegion(atlas, 960, 0, 64, 64);
		
		burpyRegion = new TextureRegion(atlas, 0, 641, 85, 84);
		houdiniRegion = new TextureRegion(atlas, 85, 641, 85, 84);
		kirbyRegion = new TextureRegion(atlas, 85*2, 641, 85, 84);
		pabloRegion = new TextureRegion(atlas, 85*3, 641, 85, 84);
		smokyRegion = new TextureRegion(atlas, 85*4, 641, 85, 84);
		perkyRegion = new TextureRegion(atlas, 85*5, 641, 85, 84);
		pebblesRegion = new TextureRegion(atlas, 85*6, 641, 85, 84);
		
		_createBirdsSprites();
		
		buttonRegion = new TextureRegion(atlas, 0, 640 + 85, 399, 58);
		buttonArrowRegion = new TextureRegion(atlas, 0, 640 + 85 + 58, 399, 57);
		buttonBackRegion = new TextureRegion(atlas, 0, 640 + 85 + 58*2, 155, 60);
		buttonBuyRegion = new TextureRegion(atlas, 155, 640 + 85 + 58*2, 155, 60);
		buttonDjinnworksRegion = new TextureRegion(atlas, 0, 1024 - 37, 525, 37);
		
		bushHorizontalRegion = new TextureRegion(bushAtlas, 0, 0, 656, 228);
		bushVerticalRegion = new TextureRegion(bushAtlas, 0, 228, 304, 640);
		
		moreGamesLineBirdsRegion = new TextureRegion(bushAtlas, 304, 228, 380, 253);
		moreGamesStickCliffDivingRegion = new TextureRegion(bushAtlas, 304, 228 + 253, 380, 253);
		moreGamesStickStuntBikerRegion = new TextureRegion(bushAtlas, 304, 228 + 253*2, 380, 253);
		
		tutorial1Region = new TextureRegion(tutorial1, 0, 0, 960, 640);
		tutorial2Region = new TextureRegion(tutorial2, 0, 0, 960, 640);
		
		beakB0Region = new TextureRegion(spriteSheet, 900, 348, 85, 85);
		beakB1Region = new TextureRegion(spriteSheet, 813, 348, 85, 85);
		beakB2Region = new TextureRegion(spriteSheet, 726, 348, 85, 85);
		beakB3Region = new TextureRegion(spriteSheet, 639, 348, 85, 85);
		beakB4Region = new TextureRegion(spriteSheet, 886, 261, 85, 85);
		beakB5Region = new TextureRegion(spriteSheet, 799, 261, 85, 85);
		beakP0Region = new TextureRegion(spriteSheet, 712, 261, 85, 85);
		beakP1Region = new TextureRegion(spriteSheet, 886, 174, 85, 85);
		beakP2Region = new TextureRegion(spriteSheet, 799, 174, 85, 85);
		beakP3Region = new TextureRegion(spriteSheet, 712, 174, 85, 85);
		beakP4Region = new TextureRegion(spriteSheet, 500, 930, 85, 85);
		beakP5Region = new TextureRegion(spriteSheet, 482, 843, 85, 85);
		beakS0Region = new TextureRegion(spriteSheet, 482, 756, 85, 85);
		beakS1Region = new TextureRegion(spriteSheet, 482, 669, 85, 85);
		beakS2Region = new TextureRegion(spriteSheet, 625, 261, 85, 85);
		beakS3Region = new TextureRegion(spriteSheet, 625, 174, 85, 85);
		beakS4Region = new TextureRegion(spriteSheet, 552, 435, 85, 85); 
		beakS5Region = new TextureRegion(spriteSheet, 552, 348, 85, 85);
		
		birdBurpyStandRegion = new TextureRegion(spriteSheet, 685, 87, 85, 85);
		birdHoudiniStandRegion = new TextureRegion(spriteSheet, 598, 87, 85, 85);
		birdKirbyStandRegion = new TextureRegion(spriteSheet, 511, 87, 85, 85);
		birdPabloStandRegion = new TextureRegion(spriteSheet, 451, 321, 85, 85);
		birdPebblesStandRegion = new TextureRegion(spriteSheet, 364, 321, 85, 85);
		birdPerkyStandRegion = new TextureRegion(spriteSheet, 277, 321, 85, 85);
		birdSmokyStandRegion = new TextureRegion(spriteSheet, 30, 460, 85, 85);
		
		coinRegion = new TextureRegion(spriteSheet, 30, 547, 50, 39);
		pauseRegion = new TextureRegion(spriteSheet, 336, 990, 18, 22);
		spikeRegion = new TextureRegion(spriteSheet, 0, 0, 28, 620);
		starEmptyRegion = new TextureRegion(spriteSheet, 356, 990, 20, 20);
		treeRegion = new TextureRegion(spriteSheet, 30, 0, 245, 458); 
		xRegion = new TextureRegion(spriteSheet, 316, 990, 18, 22);
		
		birdBurpyAnimation = new Animation(
				BIRDS_ANIMATION_FRAME_DURATION, 
				new TextureRegion(spriteSheet, 482, 582, 85, 85),
				new TextureRegion(spriteSheet, 859, 87, 85, 85),
				new TextureRegion(spriteSheet, 772, 87, 85, 85),
				new TextureRegion(spriteSheet, 859, 87, 85, 85)
		);
		
		birdHoudiniAnimation = new Animation(
				BIRDS_ANIMATION_FRAME_DURATION, 
				new TextureRegion(spriteSheet, 859, 0, 85, 85),
				new TextureRegion(spriteSheet, 772, 0, 85, 85),
				new TextureRegion(spriteSheet, 685, 0, 85, 85),
				new TextureRegion(spriteSheet, 772, 0, 85, 85)
		);
		
		birdKirbyAnimation = new Animation(
				BIRDS_ANIMATION_FRAME_DURATION, 
				new TextureRegion(spriteSheet, 598, 0, 85, 85),
				new TextureRegion(spriteSheet, 538, 261, 85, 85),
				new TextureRegion(spriteSheet, 538, 174, 85, 85),
				new TextureRegion(spriteSheet, 538, 261, 85, 85)
		);
		
		birdPabloAnimation = new Animation(
				BIRDS_ANIMATION_FRAME_DURATION, 
				new TextureRegion(spriteSheet, 511, 0, 85, 85),
				new TextureRegion(spriteSheet, 465, 495, 85, 85),
				new TextureRegion(spriteSheet, 465, 408, 85, 85),
				new TextureRegion(spriteSheet, 465, 495, 85, 85)
		);
		
		birdPebblesAnimation = new Animation(
				BIRDS_ANIMATION_FRAME_DURATION, 
				new TextureRegion(spriteSheet, 451, 234, 85, 85),
				new TextureRegion(spriteSheet, 378, 495, 85, 85),
				new TextureRegion(spriteSheet, 378, 408, 85, 85),
				new TextureRegion(spriteSheet, 378, 495, 85, 85)
		);
		
		birdPerkyAnimation = new Animation(
				BIRDS_ANIMATION_FRAME_DURATION, 
				new TextureRegion(spriteSheet, 364, 234, 85, 85),
				new TextureRegion(spriteSheet, 291, 495, 85, 85),
				new TextureRegion(spriteSheet, 291, 408, 85, 85),
				new TextureRegion(spriteSheet, 291, 495, 85, 85)
		);
		
		birdSmokyAnimation = new Animation(
				BIRDS_ANIMATION_FRAME_DURATION, 
				new TextureRegion(spriteSheet, 277, 234, 85, 85),
				new TextureRegion(spriteSheet, 204, 460, 85, 85),
				new TextureRegion(spriteSheet, 204, 460, 85, 85),
				new TextureRegion(spriteSheet, 117, 460, 85, 85)
		);
		
		planeAnimation = new Animation(
				AIRPLANE_ANIMATION_FRAME_DURATION, 
				new TextureRegion(spriteSheet, 372, 944, 126, 44),
				new TextureRegion(spriteSheet, 244, 944, 126, 44)
		);
		
		FontManager.load();
		
		/*music = assetManager.get(MUSIC_DIR_PATH + "find_the_truth.mp3", Music.class);
		music.setLooping(true);
		music.setVolume(0.5f);
		if (Settings.soundEnabled) music.play();
		
		successSound = assetManager.get(SOUND_DIR_PATH + "cartoon003.mp3", Sound.class);
		failedSound = assetManager.get(SOUND_DIR_PATH + "laugh_5.mp3", Sound.class);*/
	}
	
	private static void _createBirdsSprites() {
		burpySprite = new Sprite(burpyRegion);
		burpySprite.setSize(85, 85);
		burpySprite.setOrigin(85/2f, 85/2f);
		
		kirbySprite = new Sprite(kirbyRegion);
		kirbySprite.setSize(85, 85);
		kirbySprite.setOrigin(85/2f, 85/2f);
		
		houdiniSprite = new Sprite(houdiniRegion);
		houdiniSprite.setSize(85, 85);
		houdiniSprite.setOrigin(85/2f, 85/2f);
		
		pabloSprite = new Sprite(pabloRegion);
		pabloSprite.setSize(85, 85);
		pabloSprite.setOrigin(85/2f, 85/2f);
		
		pebblesSprite = new Sprite(pebblesRegion);
		pebblesSprite.setSize(85, 85);
		pebblesSprite.setOrigin(85/2f, 85/2f);
		
		perkySprite = new Sprite(perkyRegion);
		perkySprite.setSize(85, 85);
		perkySprite.setOrigin(85/2f, 85/2f);
		
		smokySprite = new Sprite(smokyRegion);
		smokySprite.setSize(85, 85);
		smokySprite.setOrigin(85/2f, 85/2f);
	}
	
	public static void playSound (Sound sound) {
		if (Settings.soundEnabled) sound.play(SOUND_VOLUME);
	}
	
	public static void dispose() {
		unloadAssetManager();
		
		atlas.dispose();
		bushAtlas.dispose();
		spriteSheet.dispose();
		tutorial1.dispose();
		tutorial2.dispose();
		
		FontManager.dispose();
		
		/*music.dispose();
		failedSound.dispose();
		successSound.dispose();*/
	}
}
