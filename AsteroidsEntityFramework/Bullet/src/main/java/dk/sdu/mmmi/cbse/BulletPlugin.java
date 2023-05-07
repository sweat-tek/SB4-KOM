package dk.sdu.mmmi.cbse;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

public class BulletPlugin implements IGamePluginService {

    // Gør Bullet funktionel på Enemy også.
    // Følg video efter help.Albrech
    // Tilføj TimerPart
    // Benyt evt. blaster

    private Entity bullet;
    private Entity blaster;


    public BulletPlugin(Entity blaster) {

    }

    @Override
    public void start(GameData gameData, World world) {

        bullet = createBullet(gameData);
        world.addEntity(bullet);

    }

    private Entity createBullet(GameData gameData) {
        float deceleration = 0;
        float acceleration = 200;
        float maxSpeed = 20;
        float rotationSpeed = 30;
        float x = gameData.getDisplayWidth() / 4;
        float y = gameData.getDisplayHeight() / 4;
        float radians = 5;

        Entity bulletPlayer = new Bullet();

        bulletPlayer.add(new MovingPart(deceleration,acceleration,maxSpeed,rotationSpeed));
        bulletPlayer.add(new PositionPart(x,y,radians));
        bulletPlayer.add(new LifePart(1,1000));

        return bulletPlayer;
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(bullet);

    }


}
