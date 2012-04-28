package com.nanomobile.linebirdsclone.framework;

import java.util.Random;

public class Rand {
	public static Random rand = null;
	
	public static Random getRand() {
		if (rand == null) {
			rand = new Random();
		}
		
		return rand;
	}
}
