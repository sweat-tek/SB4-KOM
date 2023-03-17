package asteroids.main;

import asteroids.common.components.Graphics;
import asteroids.common.data.GameData;
import asteroids.common.data.World;
import asteroids.common.entities.Entity;
import asteroids.common.services.IEntityProcessingService;
import asteroids.common.services.IGamePluginService;
import asteroids.common.services.IPostEntityProcessingService;
import asteroids.common.util.SPILocator;
import asteroids.managers.GameInputProcessor;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.Collection;

public class AsteroidsGame extends Game {
    private static OrthographicCamera cam;
    private final GameData gameData = new GameData();
    private final World world = new World();
    private ShapeRenderer sr;

    @Override
    public void create() {
        // Capture window size
        gameData.setDisplayWidth(Gdx.graphics.getWidth());
        gameData.setDisplayHeight(Gdx.graphics.getHeight());

        // Setup 2d camera
        cam = new OrthographicCamera(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        cam.translate(gameData.getDisplayWidth() / 2.f, gameData.getDisplayHeight() / 2.f);
        cam.update();

        // Create a vector renderer
        sr = new ShapeRenderer();

        // Setup input handling
        Gdx.input.setInputProcessor(
                new GameInputProcessor(gameData)
        );

        // Load all Game Plugins using ServiceLoader
        for (IGamePluginService iGamePlugin : getPluginServices()) {
            iGamePlugin.start(gameData, world);
        }
    }


    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameData.setDeltaTime(Gdx.graphics.getDeltaTime());

        update();

        draw();

        gameData.getKeys().update();
    }


    private void update() {
        for (IEntityProcessingService entityProcessorService : getEntityProcessingServices()) {
            entityProcessorService.process(gameData, world);
        }

        for (IPostEntityProcessingService postEntityProcessorService : getPostEntityProcessingServices()) {
            postEntityProcessorService.process(gameData, world);
        }
    }

    private void draw() {
        for (Entity entity : world.getEntities()) {
            var graphics = entity.getComponent(Graphics.class);

            if (graphics == null) {
                continue;
            }

            sr.setColor(1, 1, 1, 1);

            sr.begin(ShapeRenderer.ShapeType.Line);

            for (int i = 0; i < graphics.shape.size() - 1; i++) {
                var current = graphics.shape.get(i);
                var next = graphics.shape.get(i + 1);
                sr.line(current.x, current.y, next.x, next.y);
            }

            sr.end();
        }
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
