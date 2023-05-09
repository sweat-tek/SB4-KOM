package dk.sdu.mmmi.cbse.asteroidsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

public class AsteroidPlugin implements IGamePluginService {
    private Entity asteroid;
    @Override
    public void start(GameData gameData, World world) {
        // Add entities to the world
        asteroid = createAsteroid(gameData);
        world.addEntity(asteroid);
    }

    private Entity createAsteroid(GameData gameData) {

        float deacceleration = 10;
        float acceleration = 25;
        float maxSpeed = 100;
        float rotationSpeed = 5;
        float x = (float) (gameData.getDisplayWidth() / Math.random() * 1);
        float y = (float) (gameData.getDisplayHeight() / Math.random() * 1);
        float radians = 3.1415f / 2;

        Entity newAsteroid = new Asteroid();
        newAsteroid.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));
        newAsteroid.add(new PositionPart(x, y, radians));

        return newAsteroid;
    }


    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(asteroid);
    }
}
