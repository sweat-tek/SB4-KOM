package dk.sdu.mmmi.cbse.osgiupdater;

import dk.sdu.mmmi.cbse.osgiupdater.utilities.FileWatchListener;
import dk.sdu.mmmi.cbse.osgiupdater.utilities.FileWatcher;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.osgi.framework.*;
import org.osgi.framework.wiring.FrameworkWiring;

public class Updater implements BundleActivator {

    private final ScheduledExecutorService exector = Executors.newScheduledThreadPool(1);
    private static FileWatcher watcher;
    private static BundleContext bundleContext;
    private final List<Bundle> failedBundles = new ArrayList<>();

    public void start(BundleContext bundleContext) throws Exception {
        Path MODULES_DIRECTORY = Paths.get("/Users/jcs/OSGiModules");

        this.bundleContext = bundleContext;

        watcher = new FileWatcher(MODULES_DIRECTORY);
        watcher.addFileWatchListener(new FileWatchListener() {

            @Override
            public void created(Path filename) {
                if (filename.toString().toLowerCase().endsWith(".jar")) {
                    System.out.println("Loading: " + filename.getFileName());

                    try {
                        start(install(filename.toFile()));
                    } catch (BundleException ex) {
                        ex.printStackTrace();
                    }
                }
            }

            @Override
            public void modified(Path filename) {

            }

            @Override
            public void deleted(Path filename) {
                if (filename.toString().toLowerCase().endsWith(".jar")) {
                    System.out.println("Unloading: " + filename.getFileName());

                    try {
                        final Bundle bundle = findBundleFromFilename(filename.getFileName().toString());

                        if (bundle != null)
                            uninstall(bundle);
                    } catch (BundleException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        exector.scheduleAtFixedRate(doCheck, 10000, 10000, TimeUnit.MILLISECONDS);
    }

    private static final Runnable doCheck = new Runnable() {
        @Override
        public void run() {
            try {
                System.out.println("Check for update");
                watcher.poll();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    };

    public void stop(BundleContext bundleContext) throws Exception {

    }

    private BundleContext getBundleContext() {
        return bundleContext;
    }

    private Bundle[] getBundles() {
        return getBundleContext().getBundles();
    }

    private Bundle install(File file) throws BundleException {
        return getBundleContext().installBundle("file:" + file.getAbsoluteFile().toString());
    }

    private boolean uninstall(Bundle bundle) throws BundleException {
        bundle.stop();
        bundle.uninstall();
        refresh();
        return true;
    }

    private void refresh() {
        final Bundle systemBundle = getBundleContext().getBundle(0);
        FrameworkWiring fw = (FrameworkWiring) systemBundle.adapt(FrameworkWiring.class);
        fw.refreshBundles(null, new FrameworkListener[]{});
    }

    private void start(Bundle bundle) {
        try {
            bundle.start();
        } catch (BundleException ex) {
            failedBundles.add(bundle);
            ex.printStackTrace();
            return;
        }

        final Iterator<Bundle> it = failedBundles.iterator();

        while (it.hasNext()) {
            final Bundle failedBundle = it.next();

            try {
                failedBundle.start();
                it.remove();
            } catch (BundleException ex) {

            }
        }
    }

    private Bundle findByArtifactId(String artifactId) {
        for (Bundle bundle : getBundleContext().getBundles()) {
            if (bundle.getSymbolicName().equalsIgnoreCase(artifactId)) {
                return bundle;
            }
        }

        return null;
    }

    private Bundle findBundleFromFilename(String filename) {
        for (Bundle bundle : getBundles()) {
            if (bundle.getSymbolicName().contains(filename.substring(0, filename.indexOf("-")))) {
                return bundle;
            }
        }

        return null;
    }
}
