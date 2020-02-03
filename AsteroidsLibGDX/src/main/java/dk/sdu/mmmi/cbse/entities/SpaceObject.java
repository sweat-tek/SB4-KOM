package dk.sdu.mmmi.cbse.entities;

import dk.sdu.mmmi.cbse.main.Game;

public class SpaceObject {
	
	protected float x;
	protected float y;
	
	protected float dx;
	protected float dy;
	
	protected float radians;
	protected float speed;
	protected float rotationSpeed;
	
	protected int width;
	protected int height;
	
	protected float[] shapex;
	protected float[] shapey;
	
	protected void wrap() {
		if(x < 0) x = Game.WIDTH;
		if(x > Game.WIDTH) x = 0;
		if(y < 0) y = Game.HEIGHT;
		if(y > Game.HEIGHT) y = 0; 
	}
	
}


















