package asteroids.main;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;


class Main {
    public static void main(String[] args) {
        var cfg = new LwjglApplicationConfiguration();
        cfg.title = "Asteroids";
        cfg.width = 500;
        cfg.height = 400;
        cfg.resizable = false;
        cfg.vSyncEnabled = true;

        new LwjglApplication(new AsteroidsGame(), cfg);
    }
}
