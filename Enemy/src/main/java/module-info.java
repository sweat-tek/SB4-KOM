import common.services.IEntityProcessingService;
import common.services.IGamePluginService;
import enemysystem.EnemyControlSystem;
import enemysystem.EnemyPlugin;

module Enemy {
    requires Common;

    provides IEntityProcessingService with EnemyControlSystem;
    provides IGamePluginService with EnemyPlugin;
}