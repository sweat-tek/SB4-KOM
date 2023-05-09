package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 * This interface is used to run processes for every entity
 */
public interface IEntityProcessingService {

    /**
     * This method is to process entities.
     * It should contain the entities functions that should be run in every game loop.
     * @param gameData This contains information about the game world (Delta time, display size, input keys etc.)
     * @param world This contains a reference to all entities in the game world.
     */
    void process(GameData gameData, World world);
}
