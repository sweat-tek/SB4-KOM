module Core {
    requires Common;
    requires CommonEnemy;
    requires CommonBullet;
    requires CommonAsteroids;
    requires java.desktop;
    requires com.badlogic.gdx;
    uses dk.sdu.mmmi.cbse.common.services.IGamePluginService;
    uses dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
    uses dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
}