module Common {
    exports asteroids.common.data;
    exports asteroids.common.events;
    exports asteroids.common.components;
    exports asteroids.common.entities;
    exports asteroids.common.util;
    exports asteroids.common.math;
    exports asteroids.common.services;

    // Define all the service provider interfaces
    uses asteroids.common.services.IGamePluginService;
    uses asteroids.common.services.IEntityProcessingService;
    uses asteroids.common.services.IPostEntityProcessingService;
}