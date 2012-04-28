package com.nanomobile.linebirdsclone.framework;

import com.badlogic.gdx.math.Vector2;

public class AirPlane extends DynamicGameObject {
	public static final float AIRPLANE_WIDTH = 126/20f;
    public static final float AIRPLANE_HEIGHT = 44/20f;
    
    public static final Vector2 PLANE_VELOCITY = new Vector2(-8.5f, 0);
    
    public float stateTime;
    
    public AirPlane(float x, float y) {
    	super(x, y, AIRPLANE_WIDTH * 0.75f, AIRPLANE_HEIGHT * 0.75f);
    	
    	velocity.set(PLANE_VELOCITY.x + Settings.difficulty, PLANE_VELOCITY.y);
        
    	stateTime = 0;
    }
    
    public void update(float deltaTime) {
        position.add(velocity.x * deltaTime, velocity.y * deltaTime);
        
        _setBounds();
        
        stateTime += deltaTime;
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