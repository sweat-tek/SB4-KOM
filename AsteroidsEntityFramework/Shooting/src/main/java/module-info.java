import dk.sdu.mmmi.cbse.bulletsystem.BulletControlSystem;
import dk.sdu.mmmi.cbse.bulletsystem.BulletPlugin;
import dk.sdu.mmmi.cbse.common.bulletsystem.IBulletSPI;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

module Shooting {
    requires Common;
    requires CommonBullet;
    provides IGamePluginService with BulletPlugin;
    provides IEntityProcessingService with BulletControlSystem;
    provides IBulletSPI with BulletControlSystem;
}