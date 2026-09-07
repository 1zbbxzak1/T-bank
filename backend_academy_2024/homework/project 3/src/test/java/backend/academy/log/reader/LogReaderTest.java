package backend.academy.log.reader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class LogReaderTest {
    private final String HTTP_PATH =
        "https://raw.githubusercontent.com/elastic/examples/master/Common%20Data%20Formats/nginx_logs/nginx_logs";
    private final String FILE_PATH = "local.log";

    @InjectMocks
    private LogReader logReader;

    @BeforeEach
    public void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        Files.deleteIfExists(Path.of("logs.tmp"));
        Files.deleteIfExists(Path.of(FILE_PATH));
    }

    @Test
    public void testReadHttp() throws IOException {
        File mockFile = new File("logs.tmp");
        try (MockedStatic<HttpReader> mockedStatic = Mockito.mockStatic(HttpReader.class)) {
            mockedStatic.when(() -> HttpReader.readHttp(anyString()))
                .thenReturn(Collections.singletonList(mockFile));

            List<File> result = logReader.read(HTTP_PATH);

            assertEquals(1, result.size());
            assertEquals(mockFile, result.getFirst());
        }
    }

    @Test
    public void testReadFile() throws IOException {
        File mockFile = new File("local.log");
        try (MockedStatic<FileReader> mockedStatic = Mockito.mockStatic(FileReader.class)) {
            mockedStatic.when(() -> FileReader.readFilesFromDirectory(anyString()))
                .thenReturn(Collections.singletonList(mockFile));

            List<File> result = logReader.read(FILE_PATH);

            assertEquals(1, result.size());
            assertEquals(mockFile, result.getFirst());
        }
    }

    @Test
    public void testReadHttpFailure() {
        try (MockedStatic<HttpReader> mockedStatic = Mockito.mockStatic(HttpReader.class)) {
            mockedStatic.when(() -> HttpReader.readHttp(anyString()))
                .thenThrow(new IOException("HTTP error"));

            IOException exception = assertThrows(IOException.class, () -> logReader.read(HTTP_PATH));

            assertEquals("HTTP error", exception.getMessage());
        }
    }

    @Test
    public void testReadFileFailure() {
        try (MockedStatic<FileReader> mockedStatic = Mockito.mockStatic(FileReader.class)) {
            mockedStatic.when(() -> FileReader.readFilesFromDirectory(anyString()))
                .thenThrow(new RuntimeException("File error"));

            RuntimeException exception = assertThrows(RuntimeException.class, () -> logReader.read(FILE_PATH));

            assertEquals("File error", exception.getMessage());
        }
    }
}
