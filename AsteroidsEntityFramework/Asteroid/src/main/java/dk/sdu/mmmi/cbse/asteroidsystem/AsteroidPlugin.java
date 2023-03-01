package dk.sdu.mmmi.cbse.asteroidsystem;

import dk.sdu.mmmi.cbse.common.asteroidssystem.Asteroid;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

public class AsteroidPlugin implements IGamePluginService {
    private Entity asteroids;

    public AsteroidPlugin(){

    }

    @Override
    public void start(GameData gameData, World world) {
        // Add entities to the world
        asteroids = createAsteroids(gameData);
        world.addEntity(asteroids);
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(asteroids);
    }

    private Entity createAsteroids(GameData gameData){
        float deacceleration = 10;
        float acceleration = 80;
        float maxSpeed = 100;
        float rotationSpeed = 10;
        float x = gameData.getDisplayWidth() / 2;
        float y = gameData.getDisplayHeight();
        float radians = 3.1415f / 2;


        Entity asteroid = new Asteroid();
        asteroid.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));
        asteroid.add(new PositionPart(x, y, radians));

        return asteroid;
    }
}
