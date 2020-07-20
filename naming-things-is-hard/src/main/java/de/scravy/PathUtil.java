package de.scravy;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public final class PathUtil {

    private PathUtil() {
        throw new UnsupportedOperationException();
    }

    public static Path getCurrentWorkingDirectory() {
        final String workingDirectory = System.getProperty("user.dir");
        if (workingDirectory == null || workingDirectory.isEmpty()) {
            return Paths.get(".");
        }
        return Paths.get(workingDirectory);
    }

    public static Optional<Path> findFile(final String fileName) {
        return findFile(getCurrentWorkingDirectory(), fileName);
    }

    public static Optional<Path> findFile(final Path directory, final String fileName) {
        Path currentPath = directory;
        do {
            Path maybeTheFile = currentPath.resolve(fileName);
            if (Files.isRegularFile(maybeTheFile)) {
                return Optional.of(maybeTheFile);
            }
            currentPath = currentPath.getParent();
        } while (currentPath != null);
        return Optional.empty();
    }
}