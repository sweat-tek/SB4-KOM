package dk.sdu.mmmi.cbse.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import dk.sdu.mmmi.cbse.main.Game;

import java.util.ArrayList;

public class Player extends SpaceObject {

    private final int MAX_BULLETS = 4;
    private ArrayList<Bullet> bullets;

    private float[] flamex;
    private float[] flamey;

    private boolean left;
    private boolean right;
    private boolean up;

    private float maxSpeed;
    private float acceleration;
    private float deceleration;
    private float accelerationTimer;

    //private float x;
    //private float y;
    //private float radians;

    public Player(ArrayList<Bullet> bullets) {

        this.bullets = bullets;

        x = Game.WIDTH / 2;
        y = Game.HEIGHT / 2;

        maxSpeed = 300;
        acceleration = 200;
        deceleration = 10;

        shapex = new float[4];
        shapey = new float[4];

        flamex = new float[3];
        flamey = new float[3];

        radians = pi / 2;
        rotationSpeed = 3;

    }

    private void setShape() {
        shapex[0] = x + MathUtils.cos(radians) * 8;
        shapey[0] = y + MathUtils.sin(radians) * 8;

        shapex[1] = x + MathUtils.cos(radians - 4 * pi / 5) * 8;
        shapey[1] = y + MathUtils.sin(radians - 4 * pi / 5) * 8;

        shapex[2] = x + MathUtils.cos(radians + pi) * 5;
        shapey[2] = y + MathUtils.sin(radians + pi) * 5;

        shapex[3] = x + MathUtils.cos(radians + 4 * pi / 5) * 8;
        shapey[3] = y + MathUtils.sin(radians + 4 * pi / 5) * 8;
    }

    private void setFlame() {
        flamex[0] = x + MathUtils.cos(radians - 5 * pi / 6) * 5;
        flamey[0] = y + MathUtils.sin(radians - 5 * pi / 6) * 5;

        flamex[1] = x + MathUtils.cos( radians - pi ) * (6 + accelerationTimer * 50);
        flamey[1] = y + MathUtils.sin( radians - pi ) * (6 + accelerationTimer * 50);

        flamex[2] = x + MathUtils.cos(radians + 5 * pi / 6) * 5;
        flamey[2] = y + MathUtils.sin(radians + 5 * pi / 6) * 5;

    }

    public void setLeft(boolean b) {
        left = b;
    }

    public void setRight(boolean b) {
        right = b;
    }

    public void setUp(boolean b) {
        up = b;
    }

    public void shoot() {
        if( this.bullets.size() == MAX_BULLETS) return;
        this.bullets.add(new Bullet(x, y, radians));
    }

    public void hit() {
        System.out.println("Player hit");

        
    }

    public void update(float dt) {

        // turning
        if (left) {
            radians += rotationSpeed * dt;
        } else if (right) {
            radians -= rotationSpeed * dt;
        }

        // accelerating
        if (up) {
            dx += MathUtils.cos(radians) * acceleration * dt;
            dy += MathUtils.sin(radians) * acceleration * dt;
            accelerationTimer += dt;
            if( accelerationTimer > 0.1f ) {
                accelerationTimer = 0;
            }
        } else {
            accelerationTimer = 0;
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

        // set Flame
        if( up ) {
            setFlame();
        }

        // screen wrap
        wrap();

    }

    public void draw(ShapeRenderer sr) {

        sr.setColor(1, 1, 1, 1);

        sr.begin(ShapeType.Line);

        // draw player
        for (int i = 0, j = shapex.length - 1; i < shapex.length; j = i++) {
            sr.line(shapex[i], shapey[i], shapex[j], shapey[j]);
        }

        // draw flame
        if ( up ) {
            for (int i = 0, j = flamex.length -1 ; i < flamex.length ; j = i++ ) {
                sr.line(flamex[i], flamey[i], flamex[j], flamey[j]);
            }
        }

        sr.end();

    }

}
