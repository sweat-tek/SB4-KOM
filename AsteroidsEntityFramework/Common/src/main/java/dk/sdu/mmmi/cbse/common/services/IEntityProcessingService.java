package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 *
 *
 *
 */

public interface IEntityProcessingService {
    /**
     *Pre-condition: GameData and World object is provided
     *Post-condition: The movement of the Entity has been processed
     * @param gameData
     * @param world
     */

    void process(GameData gameData, World world);
}
