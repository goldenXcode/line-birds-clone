package com.nanomobile.linebirdsclone.framework;

public class Spike extends GameObject {
	public static final float SPIKE_WIDTH = 28/20f;
    public static final float SPIKE_HEIGHT = 620/20f;
    
    public Spike(float x, float y) {
    	super(x, y, SPIKE_WIDTH * 0.4f, SPIKE_HEIGHT * 0.95f);
    }
    
    private void _setBounds() {
    	bounds.x = position.x;
        bounds.y = position.y;
        bounds.x -= bounds.width / 2f;
        bounds.y -= bounds.height / 2f;
    }
    
    public void setPosition(float x, float y) {
    	position.set(x, y);
    	
    	_setBounds();
    }
}