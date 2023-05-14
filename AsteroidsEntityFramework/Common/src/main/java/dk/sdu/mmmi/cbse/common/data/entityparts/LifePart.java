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
 * @author Someone
 */
public class LifePart implements EntityPart {

    private int life;
    private boolean isHit;
    private float expiration;

    private boolean isAlive;

    public LifePart(int life, float expiration) {
        this.life = life;
        this.expiration = expiration;
        this.isHit = false;
        this.isAlive = true;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public boolean isIsHit() {
        return this.isHit;
    }

    public void setIsHit(boolean isHit) {
        this.isHit = isHit;
    }

    public float getExpiration() {
        return expiration;
    }

    public void setExpiration(float expiration) {
        this.expiration = expiration;
    }  
    
    public void reduceExpiration(float delta){
        this.expiration -= delta;
    }

    public boolean isAlive() {
        return this.isAlive;
    }

    public void setAlive(boolean alive) {
        this.isAlive = alive;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
        LifePart lifePart = new LifePart(3,1000);
        if (lifePart.isHit) {
            this.life = -1;
            this.isHit = false;
        }
        if (lifePart.getLife()<=0) {
            isAlive = false;
        }
    }
}
