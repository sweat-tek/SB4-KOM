package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.data.entityparts.SplitterPart;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import java.util.Random;

public class AsteroidSplitter implements IEntityProcessingService {

    Random randomNumber = new Random();

    @Override
    public void process(GameData gameData, World world) {
        // Iterates through the entities of Asteroid and adds asteroids and associated types to the world
        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            Asteroid processedAsteroid = (Asteroid) asteroid;
            PositionPart positionPart = asteroid.getPart(PositionPart.class);
            SplitterPart splitterPart = asteroid.getPart(SplitterPart.class);

            // Create the Medium asteroid
            if (processedAsteroid.getSize().equals("LARGE") && splitterPart.SplitAsteroid()) {
                splitterPart.setSplitAsteroid(false);
                Asteroid mediumAsteroid1 = createMediumAsteroid(positionPart.getX(), positionPart.getY());
                Asteroid mediumAsteroid2 = createMediumAsteroid(positionPart.getX(), positionPart.getY());
                world.addEntity(mediumAsteroid1);
                world.addEntity(mediumAsteroid2);
            }
            // Create the Small asteroid
            if (processedAsteroid.getSize().equals("MEDIUM") && splitterPart.SplitAsteroid()) {
                splitterPart.setSplitAsteroid(false);
                Asteroid smallAsteroid1 = createSmallAsteroid(positionPart.getX(), positionPart.getY());
                Asteroid smallAsteroid2 = createSmallAsteroid(positionPart.getX(), positionPart.getY());
                world.addEntity(smallAsteroid1);
                world.addEntity(smallAsteroid2);
            }
        }
    }

    private Asteroid createMediumAsteroid(float x, float y) {
        float speed = (float) Math.random() * 10f + 40f;
        float radians = 3.1415f / 2 + (float) Math.random();

        Entity asteroid = new Asteroid(AsteroidType.MEDIUM);

        asteroid.add(new MovingPart(0, speed, speed, 0));
        asteroid.add(new PositionPart(x + randomNumber.nextInt(50),
                y + randomNumber.nextInt(50), radians));
        asteroid.add(new LifePart(6, 69));
        asteroid.add(new SplitterPart());
        asteroid.setRadius(15);

        return (Asteroid) asteroid;
    }

    private Asteroid createSmallAsteroid(float x, float y) {
        float speed = (float) Math.random() * 10f + 5f;
        float radians = 3.1415f / 2 + (float) Math.random();

        Entity asteroid = new Asteroid(AsteroidType.SMALL);

        asteroid.add(new MovingPart(0,speed,speed,0));
        asteroid.add(new PositionPart(x + randomNumber.nextInt(50),
                y + randomNumber.nextInt(50), radians));
        asteroid.add(new LifePart(2,69));
        asteroid.add(new SplitterPart());
        asteroid.setRadius(15);

        return (Asteroid) asteroid;
    }

    // Create class - AsteroidControlSystem
    // Ressources:
    // Add META-INF
    // Add module-info file AFTER YOU KNOW WHY THIS IS GOOD (læs op på det)

}
