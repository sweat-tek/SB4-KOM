package dk.sdu.mmmi.cbse;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;

public class CollisionSystem implements IPostEntityProcessingService {


    // Gamedata og world processes the two for-loops and adds the entities to the game
    @Override
    public void process(GameData gameData, World world) {
        // two for loops for all entities in the world
        for (Entity entity : world.getEntities()) {
            for (Entity collisionDetection : world.getEntities()) {
                // get life parts on entities and the colliding entity's life
                LifePart entityLife = entity.getPart(LifePart.class);
                LifePart collisionLife = collisionDetection.getPart(LifePart.class);

                // if the two entities are identical, skip the iteration
                if (entity.getID().equals(collisionDetection.getID())) {
                    continue;
                }
                // remove entities with zero in expiration
                if (entityLife.getExpiration() <= 0) {
                    world.removeEntity(entity);
                    // if colliding entity expiration is zero or below, remove.
                    if (collisionLife.getExpiration() <= 0) {
                        world.removeEntity(collisionDetection);
                    }
                }

                // CollisionDetection
                if (this.Collides(entity, collisionDetection)) {
                    // if entity has been hit, the entity will have its life reduced
                    if (entityLife.getLife() > 0) {
                        entityLife.setLife(entityLife.getLife() - 1);
                        // if entity is out of life - remove from the game world
                        if (entityLife.getLife() <= 0) {
                            world.removeEntity(entity);
                        }
                    }
                }
            }
        }
    }

    public Boolean Collides(Entity entity, Entity entity2) {
        // Two PositionPart-objects will be used to define the
        PositionPart entMov = entity.getPart(PositionPart.class);
        PositionPart entMov2 = entity2.getPart(PositionPart.class);
        float dx = (float) entMov.getX() - (float) entMov2.getX();
        float dy = (float) entMov.getY() - (float) entMov2.getY();
        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        if (distance < (entity.getRadius() + entity2.getRadius())) {
            return true;
        }
        return false;
    }

}
