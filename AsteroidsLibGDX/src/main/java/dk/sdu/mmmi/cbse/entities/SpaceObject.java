package dk.sdu.mmmi.cbse.entities;

import dk.sdu.mmmi.cbse.main.Game;

public class SpaceObject {

	protected float x;
	protected float y;

	protected float dx;
	protected float dy;

	protected float pi = 3.1415f;

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

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float[] getShapex() {
		return shapex;
	}

	public float[] getShapey() {
		return shapey;
	}

	public boolean intersects(SpaceObject other) {
		float[] sx = other.getShapex();
		float[] sy = other.getShapey();
		for (int i = 0; i < sx.length; i++) {
			if( contains(sx[i], sy[i])) {
				return true;
			}
		}
		return false;
	}

	public boolean contains(float x, float y) {
		boolean b = false;
		for (int i = 0, j = shapex.length -1; i < shapex.length ; j = i++) {
			if((shapey[i] > y) != (shapey[j] > y) && (x < (shapex[j] - shapex[i]) * (y - shapey[i]) / (shapey[j] - shapey[i]) + shapex[i])) {
				b = !b;
			}
		}
		return b;
	}
}


















