package dk.sdu.mmmi.cbse.asteroidsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

public class AsteroidControlSystem implements IEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {

        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            PositionPart positionPart = asteroid.getPart(PositionPart.class);
            MovingPart movingPart = asteroid.getPart(MovingPart.class);
            LifePart lifePart = asteroid.getPart(LifePart.class);

            int size = 40;
            if (lifePart.getLife() == 2){
                size = 20;
            } else if (lifePart.getLife() == 1){
                size = 10;
            }
            movingPart.setUp(true);

            movingPart.process(gameData, asteroid);
            positionPart.process(gameData, asteroid);
            lifePart.process(gameData, asteroid);

            if (lifePart.isIsHit()) {
                asteroidSplitter(asteroid, world);
            }

            updateShape(asteroid,size);
        }

    }

    private void asteroidSplitter(Entity asteroid, World world) {
        PositionPart positionPart = asteroid.getPart(PositionPart.class);
        LifePart lifePart = asteroid.getPart(LifePart.class);
        float radians = positionPart.getRadians();
        int radius = 0;
        float speed = 80;
        int life = lifePart.getLife() - 1;
        if (life == 1) {
            radius = 8;
        } else if (life == 2) {
            radius = 10;
        } else if (life <= 0) {
            world.removeEntity(asteroid);
            return;
        }

        Entity asteroid1 = new Asteroid();

        asteroid1.setRadius(radius);
        float radians1 = radians + 0.5f;

        float by1 = (float) (Math.cos(radians1) * 40);
        float bx1 = (float) (Math.sin(radians1) * 40);

        PositionPart positionPart1 = new PositionPart(positionPart.getX() + bx1, positionPart.getY() + by1, radians1);
        asteroid1.add(new MovingPart(0,9999, speed, 0));
        asteroid1.add(positionPart1);
        asteroid1.add(new LifePart(life, 1));

        world.addEntity(asteroid1);


        Entity asteroid2 = new Asteroid();

        asteroid2.setRadius(radius);
        float radians2 = radians - 0.5f;

        float by2 = (float) (Math.cos(radians2) * 40);
        float bx2 = (float) (Math.sin(radians2) * 40);

        PositionPart positionPart2 = new PositionPart(positionPart.getX() + bx2, positionPart.getY() + by2, radians2);
        asteroid2.add(new MovingPart(0,9999, speed, 0));
        asteroid2.add(positionPart2);
        asteroid2.add(new LifePart(life, 1));

        world.addEntity(asteroid2);

        world.removeEntity(asteroid);

    }

    private void updateShape(Entity entity, int size) {
        float[] shapex = entity.getShapeX();
        float[] shapey = entity.getShapeY();
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();

        shapex[0] = (float) (x + Math.cos(Math.PI/2) * size);
        shapey[0] = (float) (y + Math.sin(Math.PI/2) * size);

        shapex[1] = (float) (x + Math.cos(Math.PI) * size);
        shapey[1] = (float) (y + Math.sin(Math.PI) * size);

        shapex[2] = (float) (x + Math.cos((Math.PI * 3) / 2) * size);
        shapey[2] = (float) (y + Math.sin((Math.PI * 3) / 2) * size);

        shapex[3] = (float) (x + Math.cos(0) * size);
        shapey[3] = (float) (y + Math.sin(0) * size);

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }
}
