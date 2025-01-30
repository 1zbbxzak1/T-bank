package backend.academy.log.reader;

import com.google.common.collect.Streams;
import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.List;
import java.util.stream.Stream;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FileReader {
    private static final String ROOT_SEARCH = ".";
    private static final String GLOB_SEARCH = "glob:";

    public static List<File> readFilesFromDirectory(String pathDir) {
        PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher(GLOB_SEARCH + pathDir);
        Stream<File> fileStream = Streams.stream(Files.fileTraverser().depthFirstPreOrder(new File(ROOT_SEARCH)));

        return fileStream
            .map(file -> {
                Path path = file.toPath();
                return path.normalize();
            })
            .filter(pathMatcher::matches)
            .map(Path::toFile)
            .toList();
    }

    public static List<File> readAbsoluteFile(String path) throws IOException {
        Path filePath = Path.of(path);
        if (filePath.isAbsolute()) {
            File file = filePath.toFile();
            if (file.exists() && file.isFile()) {
                return List.of(file);
            } else {
                throw new IOException("Файл не найден: " + path);
            }
        }

        throw new IllegalArgumentException("Путь должен быть абсолютным: " + path);
    }
}
