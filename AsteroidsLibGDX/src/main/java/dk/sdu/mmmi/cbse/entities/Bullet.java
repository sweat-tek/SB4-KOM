package dk.sdu.mmmi.cbse.entities;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

public class Bullet extends SpaceObject {

    private float lifeTime;
    private float lifeTimer;
    private boolean remove;

    // The speed which the bullet travels.
    private float speed = 350;

    // Bullet size.
    private final int width = 2;
    private final int height = 2;


    public Bullet(float x, float y, float radians ) {
        this.x = x;
        this.y = y;
        this.radians = radians;

        dx = MathUtils.cos(this.radians ) * this.speed;
        dy = MathUtils.sin(this.radians ) * this.speed;

        this.lifeTimer = 0;
        this.lifeTime = 1;
    }

    public boolean shouldRemove() {
        return this.remove;
    }

    public void update(float dt) {
        this.x += dx * dt;
        this.y += dy * dt;

        wrap();

        this.lifeTimer += dt;
        if( this.lifeTimer > this.lifeTime ) {
            this.remove = true;
        }
    }

    public void draw(ShapeRenderer sr) {

        sr.setColor(1,1,1,1);
        //sr.begin(ShapeRenderer.ShapeType.Point);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        //sr.circle(this.x - this.width / 2, this.y - this.height / 2, this.radians);
        sr.circle(this.x - this.width / 2, this.y - this.height / 2, this.width/2);

        sr.end();
    }

}
