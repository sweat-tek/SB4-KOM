package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

public class CollisionDetection implements IPostEntityProcessingService {


    @Override
    public void process(GameData gameData, World world) {

        for (Entity entity : world.getEntities()){
            for (Entity collisionDetector : world.getEntities()){
                //gets life on all entities
                LifePart life = entity.getPart(LifePart.class);

                //if entities are the same ignore
                if (entity.getID().equals(collisionDetector.getID())){
                    continue;
                }

                if (this.collision(entity, collisionDetector)){
                    //remove a life
                    if (life.getLife()>0){
                        life.setLife(life.getLife()-1);
                        life.setIsHit(true);
                    }
                    //entity is dead
                    if (life.getLife() <= 0){
                        world.removeEntity(entity);
                    }

                }

            }

        }
    }

    public boolean collision(Entity ent1, Entity ent2){
        PositionPart positionEnt1 = ent1.getPart(PositionPart.class);
        PositionPart positionEnt2 = ent2.getPart(PositionPart.class);
        float dx = (float) positionEnt1.getX() - positionEnt2.getX();
        float dy = (float) positionEnt1.getY() - positionEnt2.getY();
        float m = (float) Math.sqrt(dx * dx + dy * dy);
        if (m < ent1.getRadius() + ent2.getRadius()){
            return true;
        }
        return false;
    }


}
