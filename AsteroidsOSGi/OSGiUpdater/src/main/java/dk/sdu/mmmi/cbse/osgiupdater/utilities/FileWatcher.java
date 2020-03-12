package dk.sdu.mmmi.cbse.osgiupdater.utilities;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

public class FileWatcher {
    private WatchService watcher;

    private Map<WatchKey, Path> keys;
    private boolean followSubdirectories;

    private List<FileWatchListener> listeners = new ArrayList<>();

    public FileWatcher(Path directory) throws IOException {
        this(directory, true);
    }

    public FileWatcher(Path directory, boolean followSubdirectories) throws IOException {
        watcher = FileSystems.getDefault().newWatchService();

        keys = new HashMap<>();
        listeners = new ArrayList<>();
        this.followSubdirectories = followSubdirectories;

        if (followSubdirectories)
            registerDirectories(directory);
        else
            register(directory);
    }

    private void register(Path directory) throws IOException {
        WatchKey key = directory.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
        keys.put(key, directory);
    }

    private void registerDirectories(Path directory) throws IOException {
        Files.walk(directory)
                .filter(filePath -> Files.isDirectory(filePath))
                .forEach(filePath -> {
                    try {
                        register(filePath);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
    }

    public void poll() throws IOException {
        WatchKey key = watcher.poll();

        while (key != null) {
            Path directory = keys.get(key);

            if (directory != null) {
                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind kind = event.kind();

                    Path basename = (Path) event.context();
                    Path filename = directory.resolve(basename);

                    if (Files.isDirectory(filename)) {
                        if (followSubdirectories) {
                            if (kind == ENTRY_CREATE) {
                                registerDirectories(filename);

                                Files.walk(filename)
                                        .filter(filePath -> !Files.isDirectory(filePath))
                                        .forEach(filePath -> {
                                            for (FileWatchListener listener : listeners)
                                                listener.created(filePath);
                                        });
                            } else if (kind == ENTRY_DELETE) {
                                registerDirectories(filename);

                                Files.walk(filename)
                                        .filter(filePath -> !Files.isDirectory(filePath))
                                        .forEach(filePath -> {
                                            System.out.println("Dir Delete: " + filePath);
                                            for (FileWatchListener listener : listeners)
                                                listener.deleted(filePath);
                                        });
                            }
                        }
                    } else {
                        for (FileWatchListener listener : listeners) {
                            if (kind == ENTRY_CREATE)
                                listener.created(filename);
                            else if (kind == ENTRY_MODIFY)
                                listener.modified(filename);
                            else if (kind == ENTRY_DELETE)
                                listener.deleted(filename);
                        }
                    }
                }
            }

            if (!key.reset())
                keys.remove(key);

            key = watcher.poll();
        }
    }

    public void addFileWatchListener(FileWatchListener listener) {
        listeners.add(listener);
    }

    public void removeFileWatchListener(FileWatchListener listener) {
        listeners.remove(listener);
    }
}
