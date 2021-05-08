package dk.sdu.mmmi.cbse.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

public class Bullet extends SpaceObject {
    private float xBound;
    private float yBound;
    private float radius;

    public Bullet(float x, float y, float xBound, float yBound, float radians){
        this.x = x;
        this.y = y;
        this.xBound = xBound;
        this.yBound = yBound;
        this.radians = radians;
        this.speed = 300;
        this.dx = MathUtils.cos(radians) * speed;
        this.dy = MathUtils.sin(radians) * speed;
        this.radius = 3;
    }

    public void update(float dt) {
        x += dx * dt;
        y += dy * dt;
    }

    public boolean shouldRemove() {
        return this.x < 0 || this.x > this.xBound || this.y < 0 || this.y > yBound;
    }

    public void draw(ShapeRenderer sr) {
        sr.setColor(1, 0, 0, 1);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.circle(x, y, radius);
        sr.end();
    }
}
