package common.services;

import common.data.GameData;
import common.data.World;

public interface IEntityProcessingService {

    /**
     *
     * @param gameData Contains time since last update, display width and height,
     * @param world Contains all the entities and vector data needed to draw the world on the client.
     *
     * The process method is called at the end of every game loop.
     *
     */

    void process(GameData gameData, World world);
}
