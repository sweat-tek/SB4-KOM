package dk.sdu.mmmi.cbse.laserweapon;

import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

    public void start(BundleContext context) throws Exception {
        context.registerService(IEntityProcessingService.class, new LaserSystem(), null);
    }

    public void stop(BundleContext context) throws Exception {
    }

}
