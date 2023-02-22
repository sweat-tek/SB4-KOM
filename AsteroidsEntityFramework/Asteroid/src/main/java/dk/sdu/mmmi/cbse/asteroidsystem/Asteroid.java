package dk.sdu.mmmi.cbse.asteroidsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;

/**
 *
 * @author corfixen
 */
public class Asteroid extends Entity {
    public int getSegments() {
        return segments;
    }

    private int segments;

    public int getSeed() {
        return seed;
    }

    private int seed;

    public Asteroid() {
        this.segments = 15;
        this.setShapeX(new float[this.segments]);
        this.setShapeY(new float[this.segments]);
    }
}
