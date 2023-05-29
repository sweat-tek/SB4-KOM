import common.services.*;

module Common {
    requires java.desktop;

    exports common.data;
    exports common.events;
    exports common.services;
    exports common.util;

    uses IEntityProcessingService;
    uses IPostEntityProcessingService;
    uses IGamePluginService;
    uses BulletSPI;
}