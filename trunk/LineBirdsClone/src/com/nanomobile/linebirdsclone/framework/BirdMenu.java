package com.nanomobile.linebirdsclone.framework;

import com.badlogic.gdx.math.Vector2;

public class BirdMenu extends DynamicGameObject {
    public static final float BIRD_WIDTH = 85;
    public static final float BIRD_HEIGHT = 85;
    
    public static final Vector2 BIRD_VELOCITY = new Vector2(0, Screen.SCREEN_HEIGHT);
    public static final Vector2 BIRD_GRAVITY = new Vector2(0,  150 - Screen.SCREEN_HEIGHT);
    
    private final static int BIRD_POS_MAX_X = 200;
	private final static int BIRD_POS_MAX_Y = 200;
	
	private final static float BIRD_ROTATION = 15;
    
	public float rotationDegree;
    public float stateTime;
    
    public BirdMenu() {
    	super(
    			BIRD_POS_MAX_X * Rand.getRand().nextFloat(), 
    			BIRD_POS_MAX_Y * Rand.getRand().nextFloat(),
				BIRD_WIDTH, BIRD_HEIGHT
		);
    	
    	_initBird();
    }
    
    private void _initBird() {
    	if (Rand.getRand().nextInt(75) != 1) {
    		position.set(0, 0);
        	velocity.set(0, 0);
    	} else {
    		velocity.set(BIRD_VELOCITY);
            velocity.y *= Rand.getRand().nextFloat();
    	}
    	
    	rotationDegree = BIRD_ROTATION;
    	stateTime = 0;
    }
    
    public void update(float deltaTime) {
    	deltaTime *= 1.6f;
    	
    	rotationDegree -= BIRD_ROTATION * 2 * deltaTime;
    	
    	velocity.add(BIRD_GRAVITY.x * deltaTime, BIRD_GRAVITY.y * deltaTime);
        position.add(velocity.x * deltaTime, velocity.y * deltaTime);
        
        bounds.x = position.x;
        bounds.y = position.y;
        bounds.x -= BIRD_WIDTH / 2f;
        bounds.y -= BIRD_HEIGHT / 2f;
        
        if (velocity.y > 0) {
        	stateTime += deltaTime;
        } else if (position.y < 0) {
        	_initPosition();
        	_initBird();
        }
    }
    
    private void _initPosition() {
    	position.set(
        		BIRD_POS_MAX_X * Rand.getRand().nextFloat(), 
    			BIRD_POS_MAX_Y * Rand.getRand().nextFloat()
        );
    }
}
