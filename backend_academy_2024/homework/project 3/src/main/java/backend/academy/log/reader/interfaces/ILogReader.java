package backend.academy.log.reader.interfaces;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface ILogReader {
    List<File> read(String path) throws IOException;
}
