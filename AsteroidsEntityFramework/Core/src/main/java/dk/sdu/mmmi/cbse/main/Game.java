package dk.sdu.mmmi.cbse.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import dk.sdu.mmmi.cbse.BulletControlSystem;
import dk.sdu.mmmi.cbse.BulletPlugin;
import dk.sdu.mmmi.cbse.CollisionSystem;
import dk.sdu.mmmi.cbse.asteroid.AsteroidControlSystem;
import dk.sdu.mmmi.cbse.asteroid.AsteroidPlugin;
import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IBulletPluginService;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.mmmi.cbse.enemysystem.EnemyControlSystem;
import dk.sdu.mmmi.cbse.enemysystem.EnemyPlugin;
import dk.sdu.mmmi.cbse.managers.GameInputProcessor;
import dk.sdu.mmmi.cbse.playersystem.PlayerControlSystem;
import dk.sdu.mmmi.cbse.playersystem.PlayerPlugin;
import java.util.ArrayList;
import java.util.List;

public class Game implements ApplicationListener {

    private static OrthographicCamera cam;
    private ShapeRenderer sr;
    private final GameData gameData = new GameData();
    private List<IEntityProcessingService> entityProcessors = new ArrayList<>();
    private List<IPostEntityProcessingService> entityPostProcessorServiceList = new ArrayList<>();
    private List<IGamePluginService> entityPlugins = new ArrayList<>();
    private World world = new World();

    @Override
    public void create() {

        gameData.setDisplayWidth(Gdx.graphics.getWidth());
        gameData.setDisplayHeight(Gdx.graphics.getHeight());

        cam = new OrthographicCamera(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        cam.translate(gameData.getDisplayWidth() / 2, gameData.getDisplayHeight() / 2);
        cam.update();

        sr = new ShapeRenderer();

        Gdx.input.setInputProcessor(
                new GameInputProcessor(gameData)
        );


        IGamePluginService playerPlugin = new PlayerPlugin();
        IEntityProcessingService playerProcess = new PlayerControlSystem();

        IGamePluginService enemyPlugin = new EnemyPlugin();
        IEntityProcessingService enemyProcess = new EnemyControlSystem();

        IGamePluginService asteroidPlugin = new AsteroidPlugin(3);
        IEntityProcessingService asteroidProcess = new AsteroidControlSystem();

        IPostEntityProcessingService collisionProcess = new CollisionSystem();

        // bullet instantiation here

        IEntityProcessingService bulletProcess = new BulletControlSystem();


        // Adding the entities to the game
        entityPlugins.add(playerPlugin);
       // entityPlugins.add(bulletPlugin);
        entityPlugins.add(enemyPlugin);
        entityPlugins.add(asteroidPlugin);

        // Adding the controlsystems to the game
        entityProcessors.add(playerProcess);
        entityProcessors.add(enemyProcess);
        entityProcessors.add(asteroidProcess);
        entityProcessors.add(bulletProcess);

        // Adding the processor of collision to the game
        entityPostProcessorServiceList.add(collisionProcess);

        // bullet instance here

        // Lookup all Game Plugins using ServiceLoader
        for (IGamePluginService iGamePlugin : entityPlugins) {
            iGamePlugin.start(gameData, world);
        }

    }

    @Override
    public void render() {

        // clear screen to black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameData.setDelta(Gdx.graphics.getDeltaTime());

        update();

        draw();

        gameData.getKeys().update();
    }

    private void update() {
        // Update
        for (IEntityProcessingService entityProcessorService : entityProcessors) {
            entityProcessorService.process(gameData, world);
        }

        for (IPostEntityProcessingService entityPostProcessorService : entityPostProcessorServiceList) {
            entityPostProcessorService.process(gameData, world);
        }





    }

    private void draw() {
        // the draw-method uses the shape-renderer
        // calculates the shape
        for (Entity entity : world.getEntities()) {

            sr.setColor(1, 1, 1, 1);

            sr.begin(ShapeRenderer.ShapeType.Line);
            // sr draws the line
            // the update method calculates the shape based on an array of vectors

            float[] shapex = entity.getShapeX();
            float[] shapey = entity.getShapeY();
            // shapex = array of vectors
            for (int i = 0, j = shapex.length - 1;
                    i < shapex.length;
                    j = i++) {
                // draw line between the vectors so the vector gets drawn on the screen
                sr.line(shapex[i], shapey[i], shapex[j], shapey[j]);
            }

            sr.end();
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
}
