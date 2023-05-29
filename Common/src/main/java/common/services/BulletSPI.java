package common.services;

import common.data.Entity;
import common.data.GameData;

public interface BulletSPI {
    Entity createBullet(Entity e, GameData gameData);
}
