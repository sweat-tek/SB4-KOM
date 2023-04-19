package dk.sdu.mmmi.cbse.common.bulletsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

public interface IBulletSPI {
    Entity createBullet(Entity entity, GameData gameData);


}
