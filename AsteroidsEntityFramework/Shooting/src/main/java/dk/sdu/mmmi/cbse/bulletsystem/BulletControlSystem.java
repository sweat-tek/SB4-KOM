package dk.sdu.mmmi.cbse.bulletsystem;

import dk.sdu.mmmi.cbse.common.bulletsystem.Bullet;
import dk.sdu.mmmi.cbse.common.bulletsystem.IBulletSPI;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.TimePart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class BulletControlSystem implements IEntityProcessingService, IBulletSPI {
    @Override
    public void process(GameData gameData, World world) {

        for (Entity bullet : world.getEntities(Bullet.class)) {
            PositionPart positionPart = bullet.getPart(PositionPart.class);
            MovingPart movingPart = bullet.getPart(MovingPart.class);
            TimePart timePart = bullet.getPart(TimePart.class);

            if (timePart.getExpiration() < 0){
                world.removeEntity(bullet);
            }

            movingPart.process(gameData, bullet);
            positionPart.process(gameData, bullet);
            timePart.process(gameData, bullet);

            updateShape(bullet);

        }

    }

    public Entity createBullet(Entity shooter, GameData gameData){
        PositionPart shooterPosition = shooter.getPart(PositionPart.class);

        float x = shooterPosition.getX();
        float y = shooterPosition.getY();
        float radians = shooterPosition.getRadians();
        float dt = gameData.getDelta();
        float speed = 300;

        Entity bullet = new Entity();
        bullet.setRadius(3);

        float shotX = (float) cos(radians) * shooter.getRadius() * bullet.getRadius();
        float shotY = (float) sin(radians) * shooter.getRadius() * bullet.getRadius();

        bullet.add(new PositionPart(shotX + x, shotY +y, radians));
        bullet.add(new MovingPart(0, 400, 500, 0));
        bullet.add(new LifePart(1));
        //bullet.add(new TimePart(1));

        bullet.setShapeX(new float[2]);
        bullet.setShapeY(new float[2]);

        return bullet;
    }

    private void updateShape(Entity entity){
        float[] shapeX = entity.getShapeX();
        float[] shapeY = entity.getShapeY();
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        shapeX[0] = x;
        shapeY[0] = y;
        shapeX[1] = (float) (x + Math.cos(radians - 4 * 3.1415f / 5));
        shapeY[1] = (float) (y + Math.sin(radians - 4 * 3.1415 / 5 ));

        entity.setShapeX(shapeX);
        entity.setShapeY(shapeY);
    }
}
