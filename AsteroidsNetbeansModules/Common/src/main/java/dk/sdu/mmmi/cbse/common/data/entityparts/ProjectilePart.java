/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.mmmi.cbse.common.data.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

/**
 * Only used as a bullet identifier for better collision
 *
 * @author Phillip O
 */
public class ProjectilePart implements EntityPart {

    String ID;

    @Override
    public void process(GameData gameData, Entity entity) {
    }

    public ProjectilePart(String id) {
        this.ID = id;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

}
