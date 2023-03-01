package dk.sdu.mmmi.cbse.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import dk.sdu.mmmi.cbse.main.Game;
import java.lang.Math;
import java.util.ArrayList;

public class Enemy extends SpaceObject{

    private final int MAX_BULLETS = 4;
    private ArrayList<Bullet> enemyBullets;
    private float maxSpeed;
    private float acceleration;
    private float deceleration;

    public Enemy(ArrayList<Bullet> enemyBullets) {
        this.enemyBullets = enemyBullets;


        x = Game.WIDTH / 2;
        y = Game.HEIGHT / 2;

        maxSpeed = 200;
        acceleration = 200;
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

    public void update(float dt){

        if((int)(Math.random()*(200-100+1)+100) % 2 == 0){
            dx += MathUtils.cos(radians) * acceleration * dt;
            dy += MathUtils.sin(radians) * acceleration * dt;
        } else if ((int)(Math.random()*(200-100+1)+100) % 2 == 0) {
            radians += rotationSpeed*3*dt;
        } else {radians -= rotationSpeed*3*dt;}

        if((int)(Math.random()*(200-100+1)+100) < 110){
            if(enemyBullets.size() == MAX_BULLETS){ return;}
            enemyBullets.add(new Bullet(x,y,radians));
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

        //set position
        x += dx * dt;
        y += dy * dt;

        setShape();
        wrap();
    }

    public void draw(ShapeRenderer sr) {

        sr.setColor(1, 0, 0, 0);

        sr.begin(ShapeRenderer.ShapeType.Line);

        for (int i = 0, j = shapex.length - 1;
             i < shapex.length;
             j = i++) {

            sr.line(shapex[i], shapey[i], shapex[j], shapey[j]);

        }

        sr.end();

    }
}
