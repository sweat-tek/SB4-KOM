package enemysystem;

import common.data.Entity;
import common.data.GameData;
import common.data.World;
import common.data.entityparts.LifePart;
import common.data.entityparts.MovingPart;
import common.data.entityparts.PositionPart;
import common.services.IGamePluginService;

public class EnemyPlugin implements IGamePluginService {

    private Entity player;

    public EnemyPlugin() {
    }

    @Override
    public void start(GameData gameData, World world) {
        
        // Add entities to the world
        player = createEnemyShip(gameData);
        world.addEntity(player);
    }

    private Entity createEnemyShip(GameData gameData) {

        float deacceleration = 10;
        float acceleration = 200;
        float maxSpeed = 250;
        float rotationSpeed = 5;
        float random = (float) Math.random();
        float x = random * gameData.getDisplayWidth();
        float y = random * gameData.getDisplayHeight();
        float radians = 3.1415f / 2;
        
        Entity enemyShip = new Enemy();
        enemyShip.setRadius(8);
        enemyShip.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));
        enemyShip.add(new PositionPart(x, y, radians));
        enemyShip.add(new LifePart(2,1));
        
        return enemyShip;
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(player);
    }

}
