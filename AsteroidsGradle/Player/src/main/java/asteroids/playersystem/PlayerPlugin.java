package asteroids.playersystem;

import asteroids.common.data.GameData;
import asteroids.common.data.World;
import asteroids.common.services.IGamePluginService;

public class PlayerPlugin implements IGamePluginService {
    @Override
    public void start(GameData gameData, World world) {
        System.out.println("Start player");
    }

    @Override
    public void stop(GameData gameData, World world) {
        System.out.println("Stop player");
    }
}
