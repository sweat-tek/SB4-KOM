import asteroids.common.services.IGamePluginService;

module Player {
    requires Common;
    provides IGamePluginService with asteroids.playersystem.PlayerPlugin;
}