package dk.sdu.mmmi.cbse.asteroidsystem;

import com.badlogic.gdx.math.MathUtils;
import dk.sdu.mmmi.cbse.common.asteroidssystem.Asteroid;
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

            movingPart.setLeft(MathUtils.randomBoolean());
            movingPart.setRight(MathUtils.randomBoolean());
            movingPart.setUp(MathUtils.randomBoolean());

            movingPart.process(gameData, asteroid);
            positionPart.process(gameData, asteroid);

            //TODO: implement lifePart

            updateShape(asteroid);
        }


    }
    private void updateShape(Entity entity) {

        float[] shapex = entity.getShapeX();
        float[] shapey = entity.getShapeY();
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        shapex[0] = (float) (x + Math.cos(radians) * 10);
        shapey[0] = (float) (y + Math.sin(radians) * 10);

        shapex[1] = (float) (x + Math.cos(radians - 3 * 3.1415f / 5) * 8);
        shapey[1] = (float) (y + Math.sin(radians - 3 * 3.1145f / 5) * 8);

        shapex[2] = (float) (x + Math.cos(radians + 3.1415f) * 10);
        shapey[2] = (float) (y + Math.sin(radians + 3.1415f) * 10);

        shapex[3] = (float) (x + Math.cos(radians + 3 * 3.1415f / 5) * 12);
        shapey[3] = (float) (y + Math.sin(radians + 3 * 3.1415f / 5) * 12);

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }
}
