/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.data.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

/**
 *
 * @author Alexander
 */
public class TimerPart
        implements EntityPart {

    private float expiration;

    public TimerPart(float expiration) {
        this.expiration = expiration;
    }

    public float getExpiration() {
        return expiration;
    }

    public void setExpiration(float expiration) {
        this.expiration = expiration;
    }

    public void reduceExpiration(float delta) {
        this.expiration -= delta;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
        if (expiration > 0) {
            reduceExpiration(gameData.getDelta());
        }
    }

}
