package dk.sdu.mmmi.cbse.osgiasteroidsplitter.impl;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import dk.sdu.mmmi.cbse.common.asteroids.IAsteroidSplitter;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 *
 * @author corfixen
 */
public class AsteroidSplitterImpl implements IAsteroidSplitter {

    public void createSplitAsteroid(Entity e, World world) {
        PositionPart otherPos = e.getPart(PositionPart.class);
        LifePart otherLife = e.getPart(LifePart.class);
        float radians = otherPos.getRadians();
        int radius = 0;
        float speed = 5;
        int life = otherLife.getLife()-1;
        if (life == 1) {
            radius = 6;
            speed = (float) Math.random() * 30f + 70f;
        } else if (life == 2) {
            radius = 10;
            speed = (float) Math.random() * 10f + 50f;
        } else if(life <= 0) {
            world.removeEntity(e);
            return;
        }
        
        Entity asteroid1 = new Asteroid();
        
        asteroid1.setRadius(radius);
        float radians1 = radians -0.5f;
        
        float by1 = (float) sin(radians1) * e.getRadius() * asteroid1.getRadius();
        float bx1 = (float) cos(radians1) * e.getRadius() * asteroid1.getRadius();
        
        PositionPart astPositionPart1 = new PositionPart(otherPos.getX() + bx1, otherPos.getY() + by1, radians1);
        asteroid1.add(new MovingPart(0, 5000, speed, 0));
        asteroid1.add(astPositionPart1);
        asteroid1.add(new LifePart(life));
        
        world.addEntity(asteroid1);
        
        Entity asteroid2 = new Asteroid();
        
        asteroid2.setRadius(radius);
        float radians2 = radians + 0.5f;
        
        float by2 = (float) sin(radians2) * e.getRadius() * asteroid2.getRadius();
        float bx2 = (float) cos(radians2) * e.getRadius() * asteroid2.getRadius();
        PositionPart astPositionPart2 = new PositionPart(otherPos.getX() + bx2, otherPos.getY() + by2, radians2);
        
        asteroid2.add(new MovingPart(0, 5000, speed, 0));
        asteroid2.add(astPositionPart2);
        asteroid2.add(new LifePart(life));
        
        world.addEntity(asteroid2);
        
        world.removeEntity(e);
    }


}
