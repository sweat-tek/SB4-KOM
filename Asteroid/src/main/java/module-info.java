import asteroidsystem.AsteroidControlSystem;
import asteroidsystem.AsteroidPlugin;
import common.services.IEntityProcessingService;
import common.services.IGamePluginService;

module Asteroid {
    exports asteroidsystem;
    requires Common;

    provides IEntityProcessingService with AsteroidControlSystem;
    provides IGamePluginService with AsteroidPlugin;
}