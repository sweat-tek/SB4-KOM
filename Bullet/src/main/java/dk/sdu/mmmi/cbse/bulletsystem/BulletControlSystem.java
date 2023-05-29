package dk.sdu.mmmi.cbse.bulletsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.BulletSPI;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

public class BulletControlSystem implements IEntityProcessingService, BulletSPI {
    @Override
    public void process(GameData gameData, World world) {

        for (Entity bullet: world.getEntities(Bullet.class)) {
            PositionPart positionPart = bullet.getPart(PositionPart.class);
            MovingPart movingPart = bullet.getPart(MovingPart.class);

            movingPart.setUp(true);

            positionPart.process(gameData, bullet);
            movingPart.process(gameData, bullet);

            updateShape(bullet);
        }
    }

    @Override
    public Entity createBullet(Entity shooter, GameData gameData) {
        PositionPart shooterPosition = shooter.getPart(PositionPart.class);

        float radians = shooterPosition.getRadians();
        float x = shooterPosition.getX();
        float y = shooterPosition.getY();
        float speed = 400;

        Entity bullet = new Bullet();
        bullet.setRadius(2);

        float bx = (float) Math.cos(radians) * shooter.getRadius() * bullet.getRadius();
        float by = (float) Math.sin(radians) * shooter.getRadius() * bullet.getRadius();

        bullet.add(new PositionPart(bx + x, by + y, radians));
        bullet.add(new MovingPart(0, 99999, speed, 0));
        bullet.add(new LifePart(1,0.01f));

        updateShape(bullet);

        return bullet;
    }

    private void updateShape(Entity bullet) {
        float[] shapex = bullet.getShapeX();
        float[] shapey = bullet.getShapeY();
        PositionPart positionPart = bullet.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();

        int size = 2;

        shapex[0] = (float) (x + Math.cos((3 * Math.PI) / 4) * size);
        shapey[0] = (float) (y + Math.sin((3 * Math.PI) / 4) * size);

        shapex[1] = (float) (x + Math.cos((5 * Math.PI) / 4) * size);
        shapey[1] = (float) (y + Math.sin((5 * Math.PI) / 4) * size);

        shapex[2] = (float) (x + Math.cos((7 * Math.PI) / 4) * size);
        shapey[2] = (float) (y + Math.sin((7 * Math.PI) / 4) * size);

        shapex[3] = (float) (x + Math.cos(Math.PI / 4) * size);
        shapey[3] = (float) (y + Math.sin(Math.PI / 4) * size);


    }
}
