package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 * Pre-condition start-method: GameData and World object is provided
 * Post-condition start-method: Data is added on the installation of the component
 *
 * Pre-condition stop-method: The start-method has been invoked and the game is running
 * Post-condition stop-method: Data is removed/cleaned up as the component is uninstalled
 */
public interface IGamePluginService {
    void start(GameData gameData, World world);

    void stop(GameData gameData, World world);
}
