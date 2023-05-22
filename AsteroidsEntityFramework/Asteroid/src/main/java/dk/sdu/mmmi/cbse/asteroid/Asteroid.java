package dk.sdu.mmmi.cbse.asteroid;

import dk.sdu.mmmi.cbse.common.data.Entity;


public class Asteroid extends Entity {
    private AsteroidType type;

    public Asteroid() {

    }

    public Asteroid(AsteroidType type) {
        this.type = type;
    }

    public String getSize() {
        return type.getSize();
    }

}
