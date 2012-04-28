package com.nanomobile.linebirdsclone.framework;

public class Coin extends GameObject {
	public static final float COIN_WIDTH = 50/20f;
    public static final float COIN_HEIGHT = 39/20f;
    
    public float rotationDegree;
    
    public Coin(float x, float y) {
    	super(x, y, COIN_WIDTH * 0.7f, COIN_HEIGHT * 0.7f);
    	
    	rotationDegree = 0;
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