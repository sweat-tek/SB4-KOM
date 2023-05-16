package dk.sdu.mmmi.cbse.asteroidSystem;

import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;

import java.util.Random;

public class AsteroidControlSystem implements IEntityProcessingService {

    int Points = 6;

    Random randomNumber = new Random(10);
    float angle = 0;
   // float speed = (float) Math.random() * 10f + 40f;



    @Override
    public void process(GameData gameData, World world) {

        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            PositionPart positionPart = asteroid.getPart(PositionPart.class);
            MovingPart movingPart = asteroid.getPart(MovingPart.class);
            LifePart lifePart = asteroid.getPart(LifePart.class);

            if (lifePart.getLife()<= 0) {
                lifePart.isEliminated();
                world.removeEntity(asteroid);
            }



            float speed = (float) Math.random() * 10f + 40f;

            if (randomNumber.nextInt() < 8) {
                movingPart.setMaxSpeed(speed);
                movingPart.setUp(true);
            } else {
                movingPart.setRight(true);
            }
            movingPart.process(gameData,asteroid);
            positionPart.process(gameData,asteroid);
            lifePart.process(gameData,asteroid);


            movingPart.setRight(false);
            movingPart.setUp(false);

            updateShape(asteroid);

        }
    }




    // updates the asteroids shape to show at its next location as a result of its movement
    private void updateShape(Entity asteroid) {

        PositionPart positionPart = asteroid.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        float[] shapex = new float[6];
        float[] shapey = new float[6];

        Asteroid updateAsteroid = (Asteroid) asteroid;

        if(updateAsteroid.getSize().equals("LARGE")){
            for (int i = 0; i < Points; i++) {
                shapex[i] = x + (float) Math.cos(angle + radians) * 26;
                shapey[i] = y + (float) Math.sin(angle + radians) * 26;
                angle += 2 * 3.1415f / Points;
            }
        }
        if(updateAsteroid.getSize().equals("MEDIUM")){
            for (int i = 0; i < Points; i++) {
                shapex[i] = x + (float) Math.cos(angle + radians) * 16;
                shapey[i] = y + (float) Math.sin(angle + radians) * 16;
                angle += 2 * 3.1415f / Points;
            }
        }
        if(updateAsteroid.getSize().equals("SMALL")){
            for (int i = 0; i < Points; i++) {
                shapex[i] = x + (float) Math.cos(angle + radians) * 26;
                shapey[i] = y + (float) Math.sin(angle + radians) * 26;
                angle += 2 * 3.1415f / Points;
            }
        }
        asteroid.setShapeX(shapex);
        asteroid.setShapeY(shapey);
    }

}
