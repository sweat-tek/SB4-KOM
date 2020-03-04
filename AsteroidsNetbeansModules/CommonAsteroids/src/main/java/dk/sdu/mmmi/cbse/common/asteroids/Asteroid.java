package dk.sdu.mmmi.cbse.common.asteroids;

import dk.sdu.mmmi.cbse.common.data.Entity;

/**
 *
 * @author Phillip Olsen
 */
public class Asteroid extends Entity {

    private AsteroidType type;

    public Asteroid(AsteroidType type) {
        this.type = type;
    }

    public String getSize() {
        return type.getSize();
    }
}