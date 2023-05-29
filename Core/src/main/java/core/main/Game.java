package core.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import asteroidsystem.Asteroid;
import common.services.IPostEntityProcessingService;
import common.util.SPILocator;
import common.data.Entity;
import common.data.GameData;
import common.data.World;
import common.services.IEntityProcessingService;
import common.services.IGamePluginService;
import core.managers.GameInputProcessor;
import enemysystem.Enemy;
import playersystem.Player;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Game
        implements ApplicationListener {

    private static OrthographicCamera cam;
    private ShapeRenderer sr;

    private final GameData gameData = new GameData();
    private List<IEntityProcessingService> entityProcessors = new ArrayList<>();
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


        // Lookup all Game Plugins using ServiceLoader
        for (IGamePluginService iGamePlugin : getPluginServices()) {
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
        for (IEntityProcessingService iEntityProcessingService: getEntityProcessingServices()){
            iEntityProcessingService.process(gameData,world);
        }

        for (IPostEntityProcessingService postEntityProcessingService: getPostEntityProcessingServices()){
            postEntityProcessingService.process(gameData,world);
        }
    }

    private void draw() {

        for (Entity entity : world.getEntities()) {
            if (entity instanceof Enemy) {
                sr.setColor(Color.RED);
            } else if (entity instanceof Player) {
                sr.setColor(Color.LIME);
            } else if (entity instanceof Asteroid) {
                //Makes the asteroids have random colors
                int random = (int) (Math.random() * 10);
                if (random == 0){
                    sr.setColor(Color.YELLOW);
                } else if (random == 1){
                    sr.setColor(Color.ORANGE);
                } else if (random == 2){
                    sr.setColor(Color.PURPLE);
                } else if (random == 3){
                    sr.setColor(Color.PINK);
                } else if (random == 4){
                    sr.setColor(Color.MAGENTA);
                } else if (random == 5){
                    sr.setColor(Color.LIGHT_GRAY);
                } else if (random == 6){
                    sr.setColor(Color.GOLD);
                } else if (random == 7){
                    sr.setColor(Color.MAROON);
                } else if (random == 8){
                    sr.setColor(Color.CYAN);
                } else if (random == 9){
                    sr.setColor(Color.BLUE);
                }
            }

            sr.begin(ShapeRenderer.ShapeType.Line);

            float[] shapex = entity.getShapeX();
            float[] shapey = entity.getShapeY();

            for (int i = 0, j = shapex.length - 1;
                    i < shapex.length;
                    j = i++) {

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

    private Collection<? extends IGamePluginService> getPluginServices() {
        return SPILocator.locateAll(IGamePluginService.class);
    }

    private Collection<? extends IEntityProcessingService> getEntityProcessingServices() {
        return SPILocator.locateAll(IEntityProcessingService.class);
    }

    private Collection<? extends IPostEntityProcessingService> getPostEntityProcessingServices() {
        return SPILocator.locateAll(IPostEntityProcessingService.class);
    }
}
