package dk.sdu.mmmi.cbse.common.asteroidssystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.World;

public interface IAsteroidSplitter {

    void createSplitAsteroid(Entity entity, World world);
}
