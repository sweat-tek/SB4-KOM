package dk.sdu.mmmi.cbse;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IBulletPluginService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

public class BulletPlugin implements IBulletPluginService {

    public BulletPlugin() {
    }



    private Entity createBullet(Entity entity) {

        PositionPart positionPart = entity.getPart(PositionPart.class);

        float deceleration = 0;
        float acceleration = 2000;
        float maxSpeed = 200;
        float rotationSpeed = 30;
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        Entity bulletPlayer = new Bullet();
        // Ændre senere til at lave bullets størrelse om. Kopier samme shape som player og giv anden farve + størrelse.

        bulletPlayer.add(new MovingPart(deceleration,acceleration,maxSpeed,rotationSpeed));
        bulletPlayer.add(new PositionPart(x,y,radians));
        bulletPlayer.add(new LifePart(1,1000));

        return bulletPlayer;
    }


    @Override
    public Entity start(Entity entity, GameData gameData) {
        entity = createBullet(entity);
        return entity;
    }

    @Override
    public Entity stop() {
        return null;
    }

}
