package dk.sdu.mmmi.cbse;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

public class BulletControlSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        // Looks for bullets in the world to process
        for (Entity bullet : world.getEntities(Bullet.class)) {

            PositionPart positionPart = bullet.getPart(PositionPart.class);
            MovingPart movingPart = bullet.getPart(MovingPart.class);
            LifePart lifePart = bullet.getPart(LifePart.class);

            positionPart.process(gameData, bullet);
            movingPart.process(gameData,bullet);
            lifePart.reduceExpiration(gameData.getDelta());
            lifePart.process(gameData,bullet);

            movingPart.setUp(true);

            updateShape(bullet);

            if (lifePart.getExpiration() <= 0) {
                world.removeEntity(bullet);
            }

            updateShape(bullet);
        }
    }

    private void updateShape(Entity entity) {
        float[] shapeX = entity.getShapeX();
        float[] shapeY = entity.getShapeY();
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        shapeX[0] = (float) (x + Math.cos(radians) * 2);
        shapeY[0] = (float) (y + Math.sin(radians) * 2);

        shapeX[1] = (float) (x + Math.cos(radians) * 0);
        shapeY[1] = (float) (y + Math.sin(radians) * 0);

        shapeX[2] = (float) (x + Math.cos(radians) * 2);
        shapeY[2] = (float) (y + Math.sin(radians) * 2);

        shapeX[3] = (float) (x + Math.cos(radians) * -1);
        shapeY[3] = (float) (y + Math.sin(radians) * -1);

        entity.setShapeX(shapeX);
        entity.setShapeY(shapeY);


    }
}
