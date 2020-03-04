package org.netbeans.modules.autoupdate.silentupdate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.netbeans.api.autoupdate.InstallSupport;
import org.netbeans.api.autoupdate.InstallSupport.Installer;
import org.netbeans.api.autoupdate.InstallSupport.Validator;
import org.netbeans.api.autoupdate.OperationContainer;
import org.netbeans.api.autoupdate.OperationContainer.OperationInfo;
import org.netbeans.api.autoupdate.OperationException;
import org.netbeans.api.autoupdate.OperationSupport;
import org.netbeans.api.autoupdate.OperationSupport.Restarter;
import org.netbeans.api.autoupdate.UpdateElement;
import org.netbeans.api.autoupdate.UpdateManager;
import org.netbeans.api.autoupdate.UpdateUnit;
import org.netbeans.api.autoupdate.UpdateUnitProvider;
import org.netbeans.api.autoupdate.UpdateUnitProviderFactory;

/**
 *
 */
public final class UpdateHandler {

    public static final String SILENT_UC_CODE_NAME = "org_netbeans_modules_autoupdate_silentupdate_update_center"; // NOI18N
    private static Collection<UpdateElement> locallyInstalled = new ArrayList<>();
    private static final Logger LOGGER = Logger.getLogger(UpdateHandler.class.getPackage().getName());

    public static boolean timeToCheck() {
        // every startup
        return true;
    }

    public static class UpdateHandlerException extends Exception {

        public UpdateHandlerException(String msg) {
            super(msg);
        }

        public UpdateHandlerException(String msg, Throwable th) {
            super(msg, th);
        }
    }

    public static void checkAndHandleUpdates() {

        locallyInstalled = findLocalInstalled();

        // refresh silent update center first
        refreshSilentUpdateProvider();

        Collection<UpdateElement> updates = findUpdates();
        Collection<UpdateElement> available = findNewModules();
        Collection<UpdateElement> uninstalls = findUnstalls();

        if (updates.isEmpty() && available.isEmpty() && uninstalls.isEmpty()) {
            // none for install
            LOGGER.info("None for update/install/uninstall");
            return;
        }

        // create a container for install
        OperationContainer<InstallSupport> containerForInstall = feedContainer(available, false);
        if (containerForInstall != null) {
            try {
                handleInstall(containerForInstall);
                LOGGER.info("Install new modules done.");
            } catch (UpdateHandlerException ex) {
                LOGGER.log(Level.INFO, ex.getLocalizedMessage(), ex.getCause());
                return;
            }
        }

        // create a container for update
        OperationContainer<InstallSupport> containerForUpdate = feedContainer(updates, true);
        if (containerForUpdate != null) {
            try {
                handleInstall(containerForUpdate);
                LOGGER.info("Update done.");
            } catch (UpdateHandlerException ex) {
                LOGGER.log(Level.INFO, ex.getLocalizedMessage(), ex.getCause());
                return;
            }
        }

        // create a container for uninstall
        OperationContainer<OperationSupport> containerForUninstall = feedUninstallContainer(uninstalls);
        if (containerForUninstall != null) {
            try {
                handleUninstall(containerForUninstall);
                LOGGER.info("Unstall modules done.");
            } catch (UpdateHandlerException ex) {
                LOGGER.log(Level.INFO, ex.getLocalizedMessage(), ex.getCause());
                return;
            }
        }
    }

    public static boolean isLicenseApproved(String license) {
        // place your code there
        return true;
    }

    // package private methods
    static void handleInstall(OperationContainer<InstallSupport> container) throws UpdateHandlerException {
        // check licenses
        if (!allLicensesApproved(container)) {
            // have a problem => cannot continue
            throw new UpdateHandlerException("Cannot continue because license approval is missing for some updates.");
        }

        // download
        InstallSupport support = container.getSupport();
        Validator v = null;
        try {
            v = doDownload(support);
        } catch (OperationException ex) {
            // caught a exception
            throw new UpdateHandlerException("A problem caught while downloading, cause: ", ex);
        }
        if (v == null) {
            // have a problem => cannot continue
            throw new UpdateHandlerException("Missing Update Validator => cannot continue.");
        }

        // verify
        Installer i = null;
        try {
            i = doVerify(support, v);
        } catch (OperationException ex) {
            // caught a exception
            throw new UpdateHandlerException("A problem caught while verification of updates, cause: ", ex);
        }
        if (i == null) {
            // have a problem => cannot continue
            throw new UpdateHandlerException("Missing Update Installer => cannot continue.");
        }

        // install
        Restarter r = null;
        try {
            r = doInstall(support, i);
        } catch (OperationException ex) {
            // caught a exception
            throw new UpdateHandlerException("A problem caught while installation of updates, cause: ", ex);
        }

        // restart later
        support.doRestartLater(r);
        return;
    }

    static void handleUninstall(OperationContainer<OperationSupport> cont) throws UpdateHandlerException {

        if (!cont.listAll().isEmpty()) {
            try {
                Restarter rs = cont.getSupport().doOperation(null);
                if (rs != null) {
                    cont.getSupport().doRestart(rs, null);
                }
            } catch (OperationException ex) {
                throw new UpdateHandlerException("A problem caught while uninstall, cause: ", ex);
            }
        }
    }

    static Collection<UpdateElement> findLocalInstalled() {

        // Find uninstalls
        Collection<UpdateElement> locals = new HashSet<>();
        List<UpdateUnit> updateUnits = getSilentUpdateProvider().getUpdateUnits();

        for (UpdateUnit updateUnit : updateUnits) {
            if (updateUnit.getInstalled() != null) {
                locals.add(updateUnit.getInstalled());
            }
        }

        return locals;
    }

    static Collection<UpdateElement> findUnstalls() {

        if (locallyInstalled.isEmpty()) {
            return locallyInstalled;
        }

        Collection<UpdateElement> updateUnits = findLocalInstalled();
        Collection<UpdateElement> uninstalls = new HashSet<>(locallyInstalled);
        uninstalls.removeAll(updateUnits);

        return uninstalls;
    }

    static Collection<UpdateElement> findUpdates() {
        // check updates
        Collection<UpdateElement> elements4update = new HashSet<>();
        List<UpdateUnit> updateUnits = getSilentUpdateProvider().getUpdateUnits();
        for (UpdateUnit unit : updateUnits) {
            if (unit.getInstalled() != null) { // means the plugin already installed
                if (!unit.getAvailableUpdates().isEmpty()) { // has updates
                    elements4update.add(unit.getAvailableUpdates().get(0)); // add plugin with highest version
                }
            }
        }
        return elements4update;
    }

    static Collection<UpdateElement> findNewModules() {
        // check updates
        Collection<UpdateElement> elements4install = new HashSet<>();
        List<UpdateUnit> updateUnits = UpdateManager.getDefault().getUpdateUnits();
        for (UpdateUnit unit : updateUnits) {
            if (unit.getInstalled() == null) { // means the plugin is not installed yet
                if (!unit.getAvailableUpdates().isEmpty()) { // is available
                    elements4install.add(unit.getAvailableUpdates().get(0)); // add plugin with highest version                    
                }
            }
        }
        return elements4install;
    }

    static void refreshSilentUpdateProvider() {
        UpdateUnitProvider silentUpdateProvider = getSilentUpdateProvider();
        if (silentUpdateProvider == null) {
            // have a problem => cannot continue
            LOGGER.info("Missing Silent Update Provider => cannot continue.");
            return;
        }

        try {
            silentUpdateProvider.refresh(null, true);
        } catch (IOException ex) {
            // caught a exception
            LOGGER.log(Level.INFO, "A problem caught while refreshing Update Centers, cause: ", ex);
        }
    }

    static UpdateUnitProvider getSilentUpdateProvider() {
        List<UpdateUnitProvider> providers = UpdateUnitProviderFactory.getDefault().getUpdateUnitProviders(true);
        for (UpdateUnitProvider p : providers) {
            if (SILENT_UC_CODE_NAME.equals(p.getName())) {
                return p;
            }
        }
        return null;
    }

    static OperationContainer<OperationSupport> feedUninstallContainer(Collection<UpdateElement> uninstalls) {
        if (uninstalls == null || uninstalls.isEmpty()) {
            return null;
        }

        OperationContainer<OperationSupport> cont = OperationContainer.createForDirectUninstall();

        // loop all uninstalls and add to container for update
        for (UpdateElement ue : uninstalls) {
            if (cont.canBeAdded(ue.getUpdateUnit(), ue)) {
                LOGGER.log(Level.INFO, "Uninstall found: {0}", ue);
                OperationInfo<OperationSupport> operationInfo = cont.add(ue);
                if (operationInfo == null) {
                    continue;
                }
                cont.add(operationInfo.getRequiredElements());
                if (!operationInfo.getBrokenDependencies().isEmpty()) {
                    // have a problem => cannot continue
                    LOGGER.log(Level.INFO, "There are broken dependencies => cannot continue, broken deps: {0}", operationInfo.getBrokenDependencies());
                    return null;
                }
            }
        }
        return cont;
    }

    static OperationContainer<InstallSupport> feedContainer(Collection<UpdateElement> updates, boolean update) {
        if (updates == null || updates.isEmpty()) {
            return null;
        }
        // create a container for update
        OperationContainer<InstallSupport> container;
        if (update) {
            container = OperationContainer.createForUpdate();
        } else {
            container = OperationContainer.createForInstall();
        }

        // loop all updates and add to container for update
        for (UpdateElement ue : updates) {
            if (container.canBeAdded(ue.getUpdateUnit(), ue)) {
                LOGGER.log(Level.INFO, "Update found: {0}", ue);
                OperationInfo<InstallSupport> operationInfo = container.add(ue);
                if (operationInfo == null) {
                    continue;
                }
                container.add(operationInfo.getRequiredElements());
                if (!operationInfo.getBrokenDependencies().isEmpty()) {
                    // have a problem => cannot continue
                    LOGGER.log(Level.INFO, "There are broken dependencies => cannot continue, broken deps: {0}", operationInfo.getBrokenDependencies());
                    return null;
                }
            }
        }
        return container;
    }

    static boolean allLicensesApproved(OperationContainer<InstallSupport> container) {
        if (!container.listInvalid().isEmpty()) {
            return false;
        }
        for (OperationInfo<InstallSupport> info : container.listAll()) {
            String license = info.getUpdateElement().getLicence();
            if (!isLicenseApproved(license)) {
                return false;
            }
        }
        return true;
    }

    static Validator doDownload(InstallSupport support) throws OperationException {
        return support.doDownload(null, true);
    }

    static Installer doVerify(InstallSupport support, Validator validator) throws OperationException {

        Installer installer = support.doValidate(validator, null); // validates all plugins are correctly downloaded
        // XXX: use there methods to make sure updates are signed and trusted
        // installSupport.isSigned(installer, <an update element>);
        // installSupport.isTrusted(installer, <an update element>);
        return installer;
    }

    static Restarter doInstall(InstallSupport support, Installer installer) throws OperationException {
        return support.doInstall(installer, null);
    }

}
