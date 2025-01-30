package backend.academy.log.reader;

import backend.academy.log.reader.interfaces.ILogReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

public class LogReader implements ILogReader {
    private static final String HTTP_SEARCH = "http";

    @Override
    public List<File> read(String path) throws IOException {
        if (path.startsWith(HTTP_SEARCH)) {
            return HttpReader.readHttp(path);
        } else if (Paths.get(path).isAbsolute()) {
            return FileReader.readAbsoluteFile(path);
        } else {
            return FileReader.readFilesFromDirectory(path);
        }
    }
}
