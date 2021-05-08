package dk.sdu.mmmi.cbse.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import dk.sdu.mmmi.cbse.main.Game;

import java.util.ArrayList;
import java.util.Random;

public class Enemy extends SpaceObject {
  private float maxSpeed;
  private float acceleration;
  private float deceleration;
  private Random directionRandomizer;
  private ArrayList<Bullet> bullets;

  public Enemy(ArrayList<Bullet> bullets) {
    directionRandomizer = new Random();

    x = Game.WIDTH - 30;
    y = Game.HEIGHT - 50;
    this.bullets = bullets;

    maxSpeed = 100;
    acceleration = 70;
    deceleration = 10;

    shapex = new float[4];
    shapey = new float[4];

    radians = 3.1415f / 2;
    rotationSpeed = 3;
  }

  private void setShape() {
    shapex[0] = x + MathUtils.cos(radians) * 8;
    shapey[0] = y + MathUtils.sin(radians) * 8;

    shapex[1] = x + MathUtils.cos(radians - 4 * 3.1415f / 5) * 8;
    shapey[1] = y + MathUtils.sin(radians - 4 * 3.1145f / 5) * 8;

    shapex[2] = x + MathUtils.cos(radians + 3.1415f) * 5;
    shapey[2] = y + MathUtils.sin(radians + 3.1415f) * 5;

    shapex[3] = x + MathUtils.cos(radians + 4 * 3.1415f / 5) * 8;
    shapey[3] = y + MathUtils.sin(radians + 4 * 3.1415f / 5) * 8;
  }
  public void update(float dt) {

    // turning
    boolean goLeft = getRandomDirection();
    if (goLeft) {
      radians += rotationSpeed * dt;
    } else {
      radians -= rotationSpeed * dt;
    }

    // accelerating
    boolean shouldAccelerate = getRandomDirection();
    if (shouldAccelerate) {
      dx += MathUtils.cos(radians) * acceleration * dt;
      dy += MathUtils.sin(radians) * acceleration * dt;
    }

    // deceleration
    float vec = (float) Math.sqrt(dx * dx + dy * dy);
    if (vec > 0) {
      dx -= (dx / vec) * deceleration * dt;
      dy -= (dy / vec) * deceleration * dt;
    }
    if (vec > maxSpeed) {
      dx = (dx / vec) * maxSpeed;
      dy = (dy / vec) * maxSpeed;
    }

    // set position
    x += dx * dt;
    y += dy * dt;

    // set shape
    setShape();

    // screen wrap
    wrap();
    // fire away
    shoot();
  }

  private boolean getRandomDirection() {
    return directionRandomizer.nextBoolean();
  }

  public void shoot() {
    if(bullets.size() == 8) {
      return;
    }
    bullets.add(new Bullet(x, y, Game.WIDTH, Game.HEIGHT, radians));
  }

  public void draw(ShapeRenderer sr) {

    sr.setColor(1, 0, 1, 1);

    sr.begin(ShapeType.Line);

    for (int i = 0, j = shapex.length - 1; i < shapex.length; j = i++) {

      sr.line(shapex[i], shapey[i], shapex[j], shapey[j]);
    }
    sr.end();
  }
}
