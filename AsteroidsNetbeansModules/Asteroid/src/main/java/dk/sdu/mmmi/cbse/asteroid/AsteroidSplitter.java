/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.asteroids.Asteroid;
import static dk.sdu.mmmi.cbse.common.asteroids.AsteroidType.MEDIUM;
import static dk.sdu.mmmi.cbse.common.asteroids.AsteroidType.SMALL;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.SplitterPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import java.util.Random;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

/**
 *
 * @author Phillip Olsen
 */
@ServiceProviders(value = {
    @ServiceProvider(service = IEntityProcessingService.class),})
public class AsteroidSplitter implements IEntityProcessingService {

    Random rnd = new Random();

    @Override
    public void process(GameData gameData, World world) {
        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            Asteroid theAsteroid = (Asteroid) asteroid;
            PositionPart posPart = asteroid.getPart(PositionPart.class);
            SplitterPart splitter = asteroid.getPart(SplitterPart.class);

            if (theAsteroid.getSize().equals("LARGE") && splitter.ShouldSplit()) {
                splitter.setHasSplit(true);
                splitter.setShouldSplit(false);
                Asteroid mediumAsteroidOne = createMediumAsteroid(posPart.getX(), posPart.getY(), splitter.getID());
                Asteroid mediumAsteroidTwo = createMediumAsteroid(posPart.getX(), posPart.getY(), splitter.getID());
                world.addEntity(mediumAsteroidOne);
                world.addEntity(mediumAsteroidTwo);
                continue;
            }

            if (theAsteroid.getSize().equals("MEDIUM") && splitter.ShouldSplit()) {
                splitter.setHasSplit(true);
                splitter.setShouldSplit(false);
                Asteroid smallAsteroidOne = createSmallAsteroid(posPart.getX(), posPart.getY(), splitter.getID());
                Asteroid smallAsteroidTwo = createSmallAsteroid(posPart.getX(), posPart.getY(), splitter.getID());
                world.addEntity(smallAsteroidOne);
                world.addEntity(smallAsteroidTwo);
                continue;
            }

            if (theAsteroid.getSize().equals("SMALL") && splitter.ShouldSplit()) {
                splitter.setHasSplit(true);
                splitter.setShouldSplit(false);
                LifePart lp = theAsteroid.getPart(LifePart.class);
                lp.setLife(0);
                continue;
            }

        }
    }

    private Asteroid createSmallAsteroid(float x, float y, String uuid) {
        float speed = (float) Math.random() * 10f + 13f;
        float radians = 3.1415f / 2 + (float) Math.random();

        float[] colour = new float[4];
        colour[0] = 1.0f;
        colour[1] = 1.0f;
        colour[2] = 1.0f;
        colour[3] = 1.0f;

        Entity asteroid = new Asteroid(SMALL);
        asteroid.add(new MovingPart(0, speed, speed, 0));
        asteroid.add(new PositionPart(x + rnd.nextInt(50), y + rnd.nextInt(50), radians));
        asteroid.add(new LifePart(1));
        asteroid.add(new SplitterPart(uuid));
        asteroid.setColour(colour);
        asteroid.setRadius(5);

        return (Asteroid) asteroid;
    }

    private Asteroid createMediumAsteroid(float x, float y, String uuid) {
        float speed = (float) Math.random() * 10f + 40f;
        float radians = 3.1415f / 2 + (float) Math.random();

        Entity asteroid = new Asteroid(MEDIUM);
        float[] colour = new float[4];
        colour[0] = 1.0f;
        colour[1] = 1.0f;
        colour[2] = 1.0f;
        colour[3] = 1.0f;

        asteroid.setColour(colour);

        asteroid.add(new MovingPart(0, speed, speed, 0));
        asteroid.add(new PositionPart(x + rnd.nextInt(50), y + rnd.nextInt(50), radians));
        asteroid.add(new LifePart(1));
        asteroid.add(new SplitterPart(uuid));
        asteroid.setRadius(10);

        return (Asteroid) asteroid;
    }
}
