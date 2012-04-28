package com.nanomobile.linebirdsclone;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;

public class LineBirdsCloneAndroid extends AndroidApplication {
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize(new LineBirdsClone(), true);
    }
}
