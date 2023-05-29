import common.services.IEntityProcessingService;
import common.services.IGamePluginService;
import playersystem.PlayerControlSystem;
import playersystem.PlayerPlugin;

module Player {
    requires Common;

    provides IEntityProcessingService with PlayerControlSystem;
    provides IGamePluginService with PlayerPlugin;
}