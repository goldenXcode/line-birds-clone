package com.nanomobile.linebirdsclone.framework;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Settings {
	public static final int VERY_EASY = 0, EASY = 1, MEDIUM = 2, HARD = 3;
	public static final int BURPY = 0, HOUDINI = 1, KIRBY = 2, PABLO = 3, PEBBLES = 4, PERKY = 5, SMOKY = 6;
	
	public static int threeOfCoinsCount;
	public static int personalBestVeryEasy, personalBestEasy, personalBestMedium, personalBestHard;
	public static int difficulty;
	public static int bird;
	public static boolean soundEnabled = true;
	public static boolean musicEnabled = true;
	
	private final static String prefName  = "LineBirdsClone_Settings";
	private final static Preferences pref = Gdx.app.getPreferences(prefName);

	public static void load () {
		threeOfCoinsCount = pref.getInteger("threeOfCoinsCount", 0);
		
		personalBestVeryEasy = pref.getInteger("personalBestVeryEasy", 0);
		personalBestEasy = pref.getInteger("personalBestEasy", 0);
		personalBestMedium = pref.getInteger("personalBestMedium", 0);
		personalBestHard = pref.getInteger("personalBestHard", 0);
		
		difficulty = pref.getInteger("difficulty", EASY);
		bird = pref.getInteger("bird", KIRBY);
		
		soundEnabled = pref.getBoolean("soundEnabled", true);
		musicEnabled = pref.getBoolean("musicEnabled", true);
	}

	public static void save () {
		pref.putInteger("threeOfCoinsCount", threeOfCoinsCount);
		
		pref.putInteger("personalBestVeryEasy", personalBestVeryEasy);
		pref.putInteger("personalBestEasy", personalBestEasy);
		pref.putInteger("personalBestMedium", personalBestMedium);
		pref.putInteger("personalBestHard", personalBestHard);
		
		pref.putInteger("difficulty", difficulty);
		pref.putInteger("bird", bird);
		
		pref.putBoolean("soundEnabled", soundEnabled);
		pref.putBoolean("musicEnabled", musicEnabled);
		
		pref.flush();
	}

	public static void addScore (int score) {
		switch (Settings.difficulty) {
			case VERY_EASY:
				if (score > personalBestVeryEasy) {
					personalBestVeryEasy = score;
				}
				break;
				
			case EASY:
				if (score > personalBestEasy) {
					personalBestEasy = score;
				}
				break;
				
			case MEDIUM:
				if (score > personalBestMedium) {
					personalBestMedium = score;
				}
				break;
				
			case HARD:
				if (score > personalBestHard) {
					personalBestHard = score;
				}
				break;
				
			default:
				break;
		}
	}
}
