package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 * This interface is used to define which module to be loaded.
 */
public interface IGamePluginService {
    /**
     * This method is to be implemented if plugin implements this interface.
     * This defines what should happen when the module is loaded.
     * @param gameData This contains information about the game world (Delta time, display size, input keys etc.)
     * @param world This contains a reference to all entities in the game world.
     */
    void start(GameData gameData, World world);

    /**
     * This method is to be implemented if plugin implements this interface.
     * This defines what should happen when the module is unloaded.
     * @param gameData This contains information about the game world (Delta time, display size, input keys etc.)
     * @param world This contains a reference to all entities in the game world.
     */
    void stop(GameData gameData, World world);
}
