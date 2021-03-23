package dk.sdu.mmmi.cbse.laserweapon;

import dk.sdu.mmmi.cbse.common.bullet.BulletSPI;
import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println("LOADED");

        LaserSystem ls = new LaserSystem();
        context.registerService(IEntityProcessingService.class, ls, null);
        context.registerService(BulletSPI.class, ls, null);
    }

    public void stop(BundleContext context) throws Exception {
    }

}
