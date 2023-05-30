package enemysystem;

import common.data.Entity;
import common.data.GameData;

import common.data.World;
import common.data.entityparts.LifePart;
import common.data.entityparts.MovingPart;
import common.data.entityparts.PositionPart;
import common.services.BulletSPI;
import common.services.IEntityProcessingService;
import common.util.SPILocator;

/**
 *
 * @author jcs
 */
public class EnemyControlSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {

        for (Entity enemy : world.getEntities(Enemy.class)) {
            PositionPart positionPart = enemy.getPart(PositionPart.class);
            MovingPart movingPart = enemy.getPart(MovingPart.class);
            LifePart lifePart = enemy.getPart(LifePart.class);

            boolean shoot = false;

            int random = (int) (Math.random() * 4);
            if (random == 0) {
                movingPart.setUp(true);
                movingPart.setLeft(false);
                movingPart.setRight(false);
            } else if (random == 1) {
                movingPart.setUp(false);
                movingPart.setLeft(true);
                movingPart.setRight(false);
            } else if (random == 2) {
                movingPart.setUp(false);
                movingPart.setLeft(false);
                movingPart.setRight(true);
            } else if (random == 3) {
                movingPart.setUp(false);
                movingPart.setLeft(false);
                movingPart.setRight(false);
                shoot = true;
            }

            if (shoot) {
                for (BulletSPI bullet : SPILocator.locateAll(BulletSPI.class)){
                    world.addEntity(bullet.createBullet(enemy,gameData));
                }
            }

            movingPart.process(gameData, enemy);
            positionPart.process(gameData, enemy);
            lifePart.process(gameData, enemy);

            updateShape(enemy);
        }
    }

    private void updateShape(Entity entity) {
        float[] shapex = entity.getShapeX();
        float[] shapey = entity.getShapeY();
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        shapex[0] = (float) (x + Math.cos(radians) * 8);
        shapey[0] = (float) (y + Math.sin(radians) * 8);

        shapex[1] = (float) (x + Math.cos(radians - 4 * 3.1415f / 5) * 8);
        shapey[1] = (float) (y + Math.sin(radians - 4 * 3.1145f / 5) * 8);

        shapex[2] = (float) (x + Math.cos(radians + 3.1415f) * 5);
        shapey[2] = (float) (y + Math.sin(radians + 3.1415f) * 5);

        shapex[3] = (float) (x + Math.cos(radians + 4 * 3.1415f / 5) * 8);
        shapey[3] = (float) (y + Math.sin(radians + 4 * 3.1415f / 5) * 8);

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }
}
