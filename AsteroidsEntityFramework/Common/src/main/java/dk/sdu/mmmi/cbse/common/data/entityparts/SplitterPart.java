package dk.sdu.mmmi.cbse.common.data.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

public class SplitterPart implements EntityPart{

    boolean splitAsteroid = false;

    @Override
    public void process(GameData gameData, Entity entity) {

    }

    public boolean SplitAsteroid() {
        return splitAsteroid;
    }

    public void setSplitAsteroid(boolean splitAsteroid) {
        this.splitAsteroid = splitAsteroid;
    }


}
