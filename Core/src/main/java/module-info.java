import common.services.IEntityProcessingService;
import common.services.IGamePluginService;
import common.services.IPostEntityProcessingService;

module Core {
    requires Common;
    requires java.desktop;
    requires com.badlogic.gdx;

    uses IEntityProcessingService;
    uses IPostEntityProcessingService;
    uses IGamePluginService;
}