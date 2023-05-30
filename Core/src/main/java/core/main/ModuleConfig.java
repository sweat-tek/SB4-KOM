package core.main;

import common.services.IEntityProcessingService;
import common.services.IGamePluginService;
import common.services.IPostEntityProcessingService;
import common.util.SPILocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ModuleConfig {

    public ModuleConfig() {
    }

    @Bean
    public Game game() {
        return new Game(entityProcessingServices(), postEntityProcessingServices(), gamePluginServices());
    }

    @Bean
    public List<IEntityProcessingService> entityProcessingServices() {
        return SPILocator.locateAll(IEntityProcessingService.class);
    }

    @Bean
    public List<IPostEntityProcessingService> postEntityProcessingServices() {
        return SPILocator.locateAll(IPostEntityProcessingService.class);
    }

    @Bean
    public List<IGamePluginService> gamePluginServices() {
        return SPILocator.locateAll(IGamePluginService.class);
    }

}
