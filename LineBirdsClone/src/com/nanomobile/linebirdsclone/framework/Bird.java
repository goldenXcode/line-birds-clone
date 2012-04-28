package com.nanomobile.linebirdsclone.framework;

import com.badlogic.gdx.math.Vector2;

public class Bird extends DynamicGameObject {
	public static final float BIRD_STATE_STAND = 0;
	public static final float BIRD_STATE_FLY = 1;
	public static final float BIRD_STATE_END = 2;
	
    public static final float BIRD_WIDTH = 85/20f;
    public static final float BIRD_HEIGHT = 85/20f;
    
    public static final Vector2 BIRD_VELOCITY = new Vector2(8.5f, 0);
    
    public final static float BIRD_ROTATION = 25;
    
    public float state;
    public float stateTime;
    public float rotationDegree;
    
    public Bird(float x, float y) {
    	super(x, y, BIRD_WIDTH * 0.7f, BIRD_HEIGHT * 0.7f);
    	
    	velocity.set(BIRD_VELOCITY.x + Settings.difficulty * 2.5f, BIRD_VELOCITY.y);
        
    	state = BIRD_STATE_STAND;
    	stateTime = 0;
        rotationDegree = 0;
    }
    
    public void update(float deltaTime, boolean isTouchLeft, boolean isTouchRight) {
        position.add(velocity.x * deltaTime, velocity.y * deltaTime);
        
        if (isTouchRight) {
        	rotationDegree += BIRD_ROTATION * 1.4f * deltaTime;
        } else if (isTouchLeft) {
        	rotationDegree -= rotationDegree * deltaTime;
        } else {
        	if (state == BIRD_STATE_FLY) {
        		rotationDegree -= BIRD_ROTATION * 1.4f * deltaTime;
        	} else if (state == BIRD_STATE_END) {
        		rotationDegree += BIRD_ROTATION * 15 * deltaTime;
        	}
        	stateTime -= deltaTime;
        }
        
        if (state == BIRD_STATE_FLY) {
        	_clampRotation();
        }
        
        _setBounds();
        
        stateTime += deltaTime;
    }
    
    private void _clampRotation() {
    	if (rotationDegree > BIRD_ROTATION) {
    		rotationDegree = BIRD_ROTATION;
    	} else if (rotationDegree < -BIRD_ROTATION) {
    		rotationDegree = -BIRD_ROTATION;
    	}
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
