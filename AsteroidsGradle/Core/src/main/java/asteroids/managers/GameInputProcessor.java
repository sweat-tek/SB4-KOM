package asteroids.managers;

import asteroids.common.data.GameData;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

/**
 * Handles all keyboard input from the game
 */
public class GameInputProcessor extends InputAdapter {
    private final GameData gameData;

    public GameInputProcessor(GameData gameData) {
        this.gameData = gameData;
    }

    public boolean keyDown(int key) {
        gameData.getKeys().setKey(key, true);
        return true;
    }

    public boolean keyUp(int key) {
        gameData.getKeys().setKey(key, false);
        return true;
    }

    /**
     * Map the keys over to Core library
     */
    public static class Keys {
        public static final int UP = Input.Keys.UP;
        public static final int LEFT = Input.Keys.LEFT;
        public static final int DOWN = Input.Keys.DOWN;
        public static final int RIGHT = Input.Keys.RIGHT;
        public static final int ENTER = Input.Keys.ENTER;
        public static final int ESCAPE = Input.Keys.ESCAPE;
        public static final int SPACE = Input.Keys.SPACE;
        public static final int SHIFT = Input.Keys.SHIFT_LEFT;
    }
}








