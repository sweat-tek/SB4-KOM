package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

public class CollisionDetector implements IPostEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        //Nested for loop to check compare all entities in the world
        for (Entity entity1: world.getEntities()){
            for (Entity entity2: world.getEntities()) {
                LifePart lifePart1 = entity1.getPart(LifePart.class);

                //Check if the current eneties are the same, and skip detection for them
                if (entity1.getID().equals(entity2.getID())) {
                    continue;
                }

                //Check if the entities are colliding
                if (this.isColliding(entity1, entity2)) {
                    //Reduce hp or remove entity with 0 hp
                    if (lifePart1.getLife() > 0) {
                        lifePart1.setLife(lifePart1.getLife() - 1);
                        lifePart1.setIsHit(true);
                        if (lifePart1.getLife() <= 0) {
                            world.removeEntity(entity1);
                        }
                    }
                }
            }
        }
    }

    private boolean isColliding(Entity entity1, Entity entity2){
        //Gets the position of the entities
        PositionPart entity1Position = entity1.getPart(PositionPart.class);
        PositionPart entity2Position = entity2.getPart(PositionPart.class);
        //Calculates the distance between the entities
        float dx = entity1Position.getX() - entity2Position.getX();
        float dy = entity1Position.getY() - entity2Position.getY();
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        //Checks if the distance is less than the sum of the radius of the entities
        if (distance < (entity1.getRadius() + entity2.getRadius())) {
            return true;
        }
        return false;
    }
}
