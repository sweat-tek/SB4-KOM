package asteroids.common.data;

import asteroids.common.entities.Entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The world holds all the entities that currently exist.
 */
public class World {
    private final Map<String, Entity> entityMap = new ConcurrentHashMap<>();

    /**
     * Add a new entity to the world.
     */
    public String addEntity(Entity entity) {
        entityMap.put(entity.getID(), entity);
        return entity.getID();
    }

    /**
     * Remove an entity from the world.
     */
    public void removeEntity(Entity entity) {
        entityMap.remove(entity.getID());
    }

    /**
     * Get an entity given its ID
     */
    public Entity getEntity(String ID) {
        return entityMap.get(ID);
    }

    /**
     * Get a list of all the entities in the world.
     */
    public Collection<Entity> getEntities() {
        return entityMap.values();
    }

    /**
     * Get a list of entities of a specific type.
     */
    public <E extends Entity> List<Entity> getEntities(Class<E> entityType) {
        List<Entity> r = new ArrayList<>();
        for (Entity e : getEntities()) {
            if (entityType.equals(e.getClass())) {
                r.add(e);
            }
        }
        return r;
    }
}
