package dk.sdu.mmmi.cbse.common.data.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

public class TimePart implements EntityPart{

    private float expiration;

    public TimePart(float expiration) {
        this.expiration = expiration;
    }

    public float getExpiration() {
        return expiration;
    }

    public void setExpiration(float expiration) {
        this.expiration = expiration;
    }

    public void decreaseExpiration(float delta){
        this.expiration -= delta;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
        if (expiration > 0){
            decreaseExpiration(gameData.getDelta());
        }
        if (expiration <= 0){
            LifePart life = entity.getPart(LifePart.class);
            life.setLife(0);
        }


    }
}
