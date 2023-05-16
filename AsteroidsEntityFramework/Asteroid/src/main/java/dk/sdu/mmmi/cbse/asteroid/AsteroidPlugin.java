package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.data.entityparts.SplitterPart;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.MovingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import java.util.Random;


import static dk.sdu.mmmi.cbse.asteroid.AsteroidType.SMALL;
import static dk.sdu.mmmi.cbse.asteroid.AsteroidType.MEDIUM;
import static dk.sdu.mmmi.cbse.asteroid.AsteroidType.LARGE;

public class AsteroidPlugin extends Asteroid implements IGamePluginService {
        private Entity asteroid;
        public int life;
        private float deacceleration, acceleration, maxSpeed, rotationSpeed;


        private int numOfPoints;
        private float[] distance;

        AsteroidType asteroidType;

        Random randomNumber = new Random();

        public AsteroidPlugin(int life) {
            this.life = life;
            this.deacceleration = 0;
            this.acceleration = 1000;
            this.maxSpeed = 5000;
            this.rotationSpeed = 1;
            this.numOfPoints = 6;
        }

        // Fortsæt med logik, evaluer på "life"
        // Hvis life == 2, create medium asteroids osv.
        // overvej hvordan du vil process dette
        // se om du kan få type og splitter ind i Plugin og/eller control system
        public Entity InitializeAsteroid (GameData gameData) {
            float x = gameData.getDisplayWidth() / 2 + 100;
            float y = gameData.getDisplayHeight() / 2 +150;
            float radians = 3.1415f / 2 + (float) Math.random();
            float initialSpeed = 50;

            Entity asteroid = new Asteroid(LARGE);
            asteroid.setRadius(15);
            asteroid.setShapeX(new float[this.numOfPoints]);
            asteroid.setShapeY(new float[this.numOfPoints]);
            asteroid.add(new LifePart(3,10));
            asteroid.add(new MovingPart(deacceleration,acceleration,maxSpeed,rotationSpeed));
            asteroid.add(new PositionPart(x,y,radians));

            return asteroid;
        }
        public AsteroidPlugin() {
            this(3);
        }



        @Override
        public void start(GameData gameData, World world) {
            asteroid = createLargeAsteroid(gameData);
            world.addEntity(asteroid);
        }


        private Entity createLargeAsteroid(GameData gameData){

            float x = gameData.getDisplayWidth() / 2 + 100;
            float y = gameData.getDisplayHeight() / 2 + 150;
            float radians = 3.1415f / 2 + (float) Math.random();

            this.deacceleration = 0;
            this.acceleration = (float) Math.random() * 10f + 40f;
            this.maxSpeed = 50000;
            this.rotationSpeed = 1;

            Entity asteroid = new Asteroid(AsteroidType.LARGE);

            asteroid.setRadius(15);
            asteroid.add(new MovingPart(deacceleration,acceleration,maxSpeed,rotationSpeed));
            asteroid.add(new PositionPart(x, y, radians));
            asteroid.add(new LifePart(this.life, 1));
            asteroid.add(new SplitterPart());

           // this.buildAsteroid(gameData, asteroid, x, y, radians);



            return asteroid;
        }

    private Asteroid createMediumAsteroid(float x, float y) {
        float speed = (float) Math.random() * 10f + 40f;
        float radians = 3.1415f / 2 + (float) Math.random();

        Entity asteroid = new Asteroid(AsteroidType.MEDIUM);

        asteroid.add(new MovingPart(0, speed, speed, 0));
        asteroid.add(new PositionPart(x + randomNumber.nextInt(50),
                y + randomNumber.nextInt(50), radians));
        asteroid.add(new LifePart(6, 69));
        asteroid.add(new SplitterPart());
        asteroid.setRadius(15);

        return (Asteroid) asteroid;
    }

    private void CreateMediumAsteroid(GameData gameData, World world, Entity asteroid) {
            world.removeEntity(asteroid);
            PositionPart positionPart = asteroid.getPart(PositionPart.class);
            MovingPart movingPart = asteroid.getPart(MovingPart.class);
            LifePart lifePart = asteroid.getPart(LifePart.class);

            this.life = lifePart.getLife() - 1;

            // if the amount of life is below or equal to 1, the code after the return statement will not be executed.
            // used in case the asteroid is small
            if (lifePart.getLife() <= 1) {
                return;
            }
            createSmallAsteroid(50,50);
    }

    private Asteroid createSmallAsteroid(float x, float y ) {
        float speed = (float) Math.random() * 10f + 5f;
        float radians = 3.1415f / 2 + (float) Math.random();

        Entity asteroid = new Asteroid(SMALL);

        asteroid.add(new MovingPart(0,speed,speed,0));
        asteroid.add(new PositionPart(x + randomNumber.nextInt(50),
                y + randomNumber.nextInt(50), radians));
        asteroid.add(new LifePart(2,69));
        asteroid.add(new SplitterPart());
        asteroid.setRadius(15);


        return (Asteroid) asteroid;
    }

        @Override
        public void stop(GameData gameData, World world) {
            world.removeEntity(asteroid);
    }



    }
