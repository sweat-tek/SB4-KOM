package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 *
 * @author jcs
 */
public interface IPostEntityProcessingService  {

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
