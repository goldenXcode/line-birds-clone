package com.nanomobile.linebirdsclone;

import com.badlogic.gdx.backends.jogl.JoglApplication;
import com.badlogic.gdx.backends.jogl.JoglApplicationConfiguration;
import com.nanomobile.linebirdsclone.framework.Screen;

public class LineBirdsCloneDesktopStarter {

	public static void main(String[] args) {
		JoglApplicationConfiguration config = new JoglApplicationConfiguration();
		config.title = "Line Birds Clone";
		config.width = Screen.SCREEN_WIDTH;
		config.height = Screen.SCREEN_HEIGHT;
		config.useGL20 = true;
		
		new JoglApplication(new LineBirdsClone(), config);
	}
}
