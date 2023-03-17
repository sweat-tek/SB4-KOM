package asteroids.common.services;

import asteroids.common.data.GameData;
import asteroids.common.data.World;

public interface IGamePluginService {

    /**
     * This will be called when the plugin is initialized
     */
    void start(GameData gameData, World world);

    /**
     * This will be called when the plugin is unloaded or the game stops.
     */
    void stop(GameData gameData, World world);
}
