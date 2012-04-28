package com.nanomobile.linebirdsclone;

import com.nanomobile.linebirdsclone.framework.Game;
import com.nanomobile.linebirdsclone.framework.Screen;
import com.nanomobile.linebirdsclone.game.screens.MainMenuScreen;

public class LineBirdsClone extends Game {
	public static String LINE_BIRDS_CLONE_VERSION = "Version: 1.0.0";
	
	@Override
	public Screen getStartScreen () {
		return new MainMenuScreen(this);
	}

	@Override
	public void create () {
		super.create();
	}
}
