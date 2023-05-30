import common.services.IEntityProcessingService;
import common.services.IGamePluginService;
import common.services.IPostEntityProcessingService;

module Core {
    requires Common;
    requires java.desktop;
    requires com.badlogic.gdx;
    requires spring.context;
    requires spring.core;
    requires spring.beans;

    exports core.main;

    opens core.main to spring.core;

    uses IEntityProcessingService;
    uses IPostEntityProcessingService;
    uses IGamePluginService;
}