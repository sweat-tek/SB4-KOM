package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

public interface IBulletPluginService {

     Entity start(Entity entity, GameData gameData);


     Entity stop();


}
