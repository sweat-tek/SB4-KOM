package dk.sdu.mmmi.cbse.osgiupdater.utilities;

import java.nio.file.Path;

public interface FileWatchListener {
    void created(Path filename);

    void modified(Path filename);

    void deleted(Path filename);
}
