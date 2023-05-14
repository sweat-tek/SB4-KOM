package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.data.entityparts.SplitterPart;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

public class AsteroidPlugin implements IGamePluginService {
        private Entity asteroid;
        private int life;
        private float deacceleration, acceleration, maxSpeed, rotationSpeed;

        private int shapePointCount;


        public AsteroidPlugin(int life) {
            this.life = life;
            this.deacceleration = 0;
            this.acceleration = 1000;
            this.maxSpeed = 5000;
            this.rotationSpeed = 1;
        }






        @Override
        public void start(GameData gameData, World world) {
            asteroid = createLargeAsteroid(gameData);
            world.addEntity(asteroid);
        }



        private Entity createLargeAsteroid(GameData gameData){

            float x = gameData.getDisplayWidth() / 2 + 100;
            float y = gameData.getDisplayHeight() / 2 + 50;
            float radians = 3.1415f / 2 + (float) Math.random();

            float speed = (float) Math.random() * 10f + 40f;

            Entity asteroid = new Asteroid(AsteroidType.LARGE);

            asteroid.setRadius(15);
            asteroid.add(new MovingPart(0,speed,speed,1));
            asteroid.add(new PositionPart(x, y, radians));
            asteroid.add(new LifePart(6, 1));
            asteroid.add(new SplitterPart());

           // this.buildAsteroid(gameData, asteroid, x, y, radians);

            return asteroid;
        }

        @Override
        public void stop(GameData gameData, World world) {
            world.removeEntity(asteroid);
    }



    }
