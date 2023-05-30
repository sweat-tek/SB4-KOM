package core.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import common.data.Entity;
import common.data.GameData;
import common.data.World;
import common.services.IEntityProcessingService;
import common.services.IGamePluginService;
import common.services.IPostEntityProcessingService;
import core.managers.GameInputProcessor;

import java.util.List;

public class Game implements ApplicationListener {

    private static OrthographicCamera cam;
    private ShapeRenderer sr;

    private final GameData gameData = new GameData();

    private final List<IEntityProcessingService> entityProcessors;
    private final List<IPostEntityProcessingService> postEntityProcessors;
    private final List<IGamePluginService> entityPlugins;

    private World world = new World();

    public Game(List<IEntityProcessingService> entityProcessors,
                List<IPostEntityProcessingService> postEntityProcessors,
                List<IGamePluginService> entityPlugins){
        this.entityProcessors = entityProcessors;
        this.postEntityProcessors = postEntityProcessors;
        this.entityPlugins = entityPlugins;
    }

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
        for (IEntityProcessingService iEntityProcessingService : entityProcessors) {
            iEntityProcessingService.process(gameData, world);
        }

        for (IPostEntityProcessingService postEntityProcessingService : postEntityProcessors) {
            postEntityProcessingService.process(gameData, world);
        }
    }

    private void draw() {

        for (Entity entity : world.getEntities()) {

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

}
