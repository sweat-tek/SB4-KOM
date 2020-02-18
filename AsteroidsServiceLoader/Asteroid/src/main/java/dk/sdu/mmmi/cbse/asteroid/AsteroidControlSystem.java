/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.SplitterPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import java.util.Random;


/**
 *
 * @author Phillip O
 */
//@ServiceProvider(service = IEntityProcessingService.class)
public class AsteroidControlSystem implements IEntityProcessingService {

    int numPoints = 6;
    Random rnd = new Random(10);
    float angle = 0;
    
    @Override
    public void process(GameData gameData, World world) {
        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            PositionPart positionPart = asteroid.getPart(PositionPart.class);
            MovingPart movingPart = asteroid.getPart(MovingPart.class);
            
            
            float speed = (float) Math.random() * 10f + 40f;
            if (rnd.nextInt() < 8) {
                movingPart.setMaxSpeed(speed);
                movingPart.setUp(true);
            } else {
                movingPart.setLeft(true);
            }

            movingPart.process(gameData, asteroid);
            positionPart.process(gameData, asteroid);
            updateShape(asteroid);
            movingPart.setLeft(false);
            movingPart.setUp(false);
        }
    }

    private void updateShape(Entity asteroid) {

        PositionPart positionPart = asteroid.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();
       
        float[] shapex = new float[6];
        float[] shapey = new float[6];
        Asteroid asAsteroid = (Asteroid) asteroid;
//        SplitterPart splitter = asAsteroid.getPart(SplitterPart.class);
        if(asAsteroid.getSize().equals("LARGE")){
        for (int i = 0; i < numPoints; i++) {
            shapex[i] = x + (float) Math.cos(angle + radians) * 26;
            shapey[i] = y + (float) Math.sin(angle + radians) * 26;
            angle += 2 * 3.1415f / numPoints;
        }
        }
        if(asAsteroid.getSize().equals("MEDIUM")){
        for (int i = 0; i < numPoints; i++) {
            shapex[i] = x + (float) Math.cos(angle + radians) * 16;
            shapey[i] = y + (float) Math.sin(angle + radians) * 16;
            angle += 2 * 3.1415f / numPoints;
        }
        }
        if(asAsteroid.getSize().equals("SMALL")){
        for (int i = 0; i < numPoints; i++) {
            shapex[i] = x + (float) Math.cos(angle + radians) * 26;
            shapey[i] = y + (float) Math.sin(angle + radians) * 26;
            angle += 2 * 3.1415f / numPoints;
        }
        }


        asteroid.setShapeX(shapex);
        asteroid.setShapeY(shapey);
    }

}
