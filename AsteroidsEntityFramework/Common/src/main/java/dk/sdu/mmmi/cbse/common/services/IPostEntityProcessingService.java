package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 * This interface is used to run post processes for every entity.
 * This should be used if something should run after the EntityProcess.
 */
public interface IPostEntityProcessingService  {
        /**
         * This method is to post process the entities in the game world.
         * @param gameData This contains information about the game world (Delta time, display size, input keys etc.)
         * @param world This contains a reference to all entities in the game world.
         */
        void process(GameData gameData, World world);
}
