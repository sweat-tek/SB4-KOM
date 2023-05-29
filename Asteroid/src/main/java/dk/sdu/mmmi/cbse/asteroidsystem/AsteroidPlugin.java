package dk.sdu.mmmi.cbse.asteroidsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

public class AsteroidPlugin implements IGamePluginService {

    private Entity asteroid;

    public AsteroidPlugin() {
    }


    @Override
    public void start(GameData gameData, World world) {

        for (int i = 0; i<5; i++){
            asteroid = createAsteroid(gameData);
            world.addEntity(asteroid);
        }

    }

    private Entity createAsteroid(GameData gameData) {

        float deacceleration = 15;
        float acceleration = 100;
        float maxSpeed = 100;
        float rotationSpeed = 8;
        float x = (float) Math.random() * gameData.getDisplayWidth();
        float y = (float) Math.random() * gameData.getDisplayHeight();
        float radians = (float) Math.random() * 2 * (float) Math.PI;

        Entity asteroid = new Asteroid();
        asteroid.setRadius(24);
        asteroid.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));
        asteroid.add(new PositionPart(x, y, radians));
        asteroid.add(new LifePart(3,1));

        return asteroid;
    }

    @Override
    public void stop(GameData gameData, World world) {

        world.removeEntity(asteroid);
    }
}
