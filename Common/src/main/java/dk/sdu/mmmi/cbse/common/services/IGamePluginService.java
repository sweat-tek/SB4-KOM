package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

public interface IGamePluginService {

    /**
     *
     * @param gameData Contains time since last update, display width and height,
     * @param world Contains all the entities and vector data needed to draw the world on the client.
     *
     * The start method adds data when you install a component.
     * The stop method removes and cleans up data when you uninstall a component.
     */

    void start(GameData gameData, World world);

    void stop(GameData gameData, World world);
}
