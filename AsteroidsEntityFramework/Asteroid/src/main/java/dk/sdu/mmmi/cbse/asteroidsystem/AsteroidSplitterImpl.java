package dk.sdu.mmmi.cbse.asteroidsystem;

import dk.sdu.mmmi.cbse.common.asteroid.Asteroid;
import dk.sdu.mmmi.cbse.common.asteroid.IAsteroidSplitter;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class AsteroidSplitterImpl implements IAsteroidSplitter {
    @Override
    public void createSplitAsteroid(Entity e, World world) {
        PositionPart otherPos = e.getPart(PositionPart.class);
        LifePart otherLife = e.getPart(LifePart.class);
        float radians = otherPos.getRadians();
        int radius = 0;
        float speed = 5;
        int life = otherLife.getLife() - 1;
        if (life == 1) {
            radius = 6;
            speed = (float) Math.random() * 30f + 70f;
        } else if (life == 2) {
            radius = 10;
            speed = (float) Math.random() * 10f + 50f;
        } else if (life <= 0) {
            world.removeEntity(e);
            return;
        }

        addNewAsteroid(e, world, otherPos, speed, life, new Asteroid(), radius, radians + 0.5f);
        addNewAsteroid(e, world, otherPos, speed, life, new Asteroid(), radius, radians - 0.5f);

        world.removeEntity(e);
    }

    private void addNewAsteroid(Entity e, World world, PositionPart otherPos, float speed, int life, Entity newAsteroid, float radius, float radians) {
        newAsteroid.setRadius(radius);

        float by = (float) sin(radians) * e.getRadius() * newAsteroid.getRadius();
        float bx = (float) cos(radians) * e.getRadius() * newAsteroid.getRadius();

        PositionPart newAsteroidPosPart = new PositionPart(otherPos.getX() + bx, otherPos.getY() + by, radians);
        newAsteroid.add(new MovingPart(0, 5000, speed, 0));
        newAsteroid.add(newAsteroidPosPart);
        newAsteroid.add(new LifePart(life));

        world.addEntity(newAsteroid);
    }
}
