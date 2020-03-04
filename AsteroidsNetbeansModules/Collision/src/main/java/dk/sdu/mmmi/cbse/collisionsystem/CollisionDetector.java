package dk.sdu.mmmi.cbse.collisionsystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.data.entityparts.LifePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.ProjectilePart;
import dk.sdu.mmmi.cbse.common.data.entityparts.ShootingPart;
import dk.sdu.mmmi.cbse.common.data.entityparts.SplitterPart;
import dk.sdu.mmmi.cbse.common.services.IPostEntityProcessingService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

@ServiceProviders(value = {
    @ServiceProvider(service = IPostEntityProcessingService.class),})
public class CollisionDetector implements IPostEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity e : world.getEntities()) {
            for (Entity f : world.getEntities()) {
                if (e.getID().equals(f.getID())) {
                    continue;
                }

                if (circleCollision(e, f)) {
                    // avoid bullets damaging the entity that created said bullet
                    if (e.getPart(ProjectilePart.class) != null) {
                        ProjectilePart epp = e.getPart(ProjectilePart.class);
                        if (f.getPart(ShootingPart.class) != null) {
                            ShootingPart fsp = f.getPart(ShootingPart.class);
                            if (epp.getID().equals(fsp.getID())) {
                                continue;
                            }
                        }
                    }
                    // avoid bullets damaging the entity that created said bullet
                    if (f.getPart(ProjectilePart.class) != null) {
                        ProjectilePart fpp = f.getPart(ProjectilePart.class);
                        if (e.getPart(ShootingPart.class) != null) {
                            ShootingPart esp = e.getPart(ShootingPart.class);
                            if (fpp.getID().equals(esp.getID())) {
                                continue;
                            }
                        }
                    }
                    // in case it's an asteroid, let it split
                    if (f.getPart(SplitterPart.class) != null) {
                        SplitterPart fsp = f.getPart(SplitterPart.class);

                        // in case an asteroid collides with its children
                        if (e.getPart(SplitterPart.class) != null) {
                            SplitterPart esp = e.getPart(SplitterPart.class);
                            if (fsp.getID().equals(esp.getID())) {
                                continue;
                            }
                        }
                        // if its the first time the asteroid has been processed, let it split
                        if (!fsp.hasSplit()) {
                            fsp.setShouldSplit(true);
                            continue;
                        }

                    }
                    // in case it's an asteroid, let it split
                    if (e.getPart(SplitterPart.class) != null) {
                        SplitterPart esp = e.getPart(SplitterPart.class);

                        // in case an asteroid collides with its children
                        if (f.getPart(SplitterPart.class) != null) {
                            SplitterPart fsp = f.getPart(SplitterPart.class);
                            if (esp.getID().equals(fsp.getID())) {
                                continue;
                            }
                        }
                        // if its the first time the asteroid has been processed, let it split
                        if (!esp.hasSplit()) {
                            esp.setShouldSplit(true);
                            continue;
                        }

                    }

                    world.removeEntity(f);
                }

                LifePart lpe = e.getPart(LifePart.class);
                if (lpe.isDead()) {
                    world.removeEntity(e);
                }

                LifePart lpf = f.getPart(LifePart.class);
                if (lpf.isDead()) {
                    world.removeEntity(f);
                }

            }
        }
    }

    private boolean circleCollision(Entity e, Entity f) {
        PositionPart ep = e.getPart(PositionPart.class);
        PositionPart fp = f.getPart(PositionPart.class);

        if ((ep.getX() - fp.getX()) * (ep.getX() - fp.getX())
                + (ep.getY() - fp.getY()) * (ep.getY() - fp.getY())
                < (e.getRadius() + f.getRadius()) * (e.getRadius() + f.getRadius())) {
            return true;
        }

        return false;
    }

}
