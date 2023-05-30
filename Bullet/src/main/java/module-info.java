import bulletsystem.BulletControlSystem;
import bulletsystem.BulletPlugin;
import common.services.BulletSPI;
import common.services.IEntityProcessingService;
import common.services.IGamePluginService;

module Bullet {
    requires Common;

    provides IEntityProcessingService with BulletControlSystem;
    provides IGamePluginService with BulletPlugin;
    provides BulletSPI with BulletControlSystem;
}