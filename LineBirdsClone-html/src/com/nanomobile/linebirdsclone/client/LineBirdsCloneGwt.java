package com.nanomobile.linebirdsclone.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.nanomobile.linebirdsclone.LineBirdsClone;
import com.nanomobile.linebirdsclone.framework.Screen;

public class LineBirdsCloneGwt extends GwtApplication {
	@Override
	public GwtApplicationConfiguration getConfig () {
		GwtApplicationConfiguration cfg = new GwtApplicationConfiguration(
													Screen.SCREEN_WIDTH, Screen.SCREEN_HEIGHT
											);
		return cfg;
	}

	@Override
	public ApplicationListener getApplicationListener () {
		return new LineBirdsClone();
	}
}