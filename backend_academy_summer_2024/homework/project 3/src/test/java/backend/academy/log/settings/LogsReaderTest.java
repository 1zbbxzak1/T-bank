package backend.academy.log.settings;

import backend.academy.log.parser.LogParser;
import backend.academy.log.reader.FileReader;
import backend.academy.log.reader.HttpReader;
import backend.academy.log.records.LogRecord;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class LogsReaderTest {
    private LogParser logParser;
    private List<File> mockFiles;

    @Mock
    private File mockFile;
    @Mock
    private Path mockPath;

    @BeforeEach
    public void setUp() {
        logParser = new LogParser();

        when(mockFile.toPath()).thenReturn(mockPath);
        mockFiles = Collections.singletonList(mockFile);
    }

    @Test public void testReadLogsFromHttp() throws IOException {
        LogRecord logRecord =
            new LogRecord("127.0.0.1", "user1",
                LocalDate.of(2020, 1, 1),
                "GET /resource HTTP/1.1", 200, 1234,
                "http://referer1.com", "Mozilla/5.0");

        try (MockedStatic<HttpReader> httpReaderMock = mockStatic(HttpReader.class);
             MockedStatic<Files> filesMock = mockStatic(Files.class)) {
            httpReaderMock.when(() -> HttpReader.readHttp(anyString())).thenReturn(mockFiles);

            filesMock.when(() -> Files.lines(any(Path.class))).thenReturn(Stream.of(
                "127.0.0.1 - user1 [01/Jan/2020:00:00:00 +0000] \"GET /resource HTTP/1.1\" 200 1234 \"http://referer1.com\" \"Mozilla/5.0\""));

            List<LogRecord> result =
                LogsReader.readLogs("http://example.com/logfile", LocalDate.of(2020, 1, 1), LocalDate.of(2020, 12, 31),
                    "status", "200").toList();

            assertEquals(1, result.size());
            assertEquals(logRecord, logParser.parse(
                "127.0.0.1 - user1 [01/Jan/2020:00:00:00 +0000] \"GET /resource HTTP/1.1\" 200 1234 http://referer1.com \"Mozilla/5.0\""));
        }
    }

    @Test
    public void testReadLogsFromFile() throws IOException {
        LogRecord logRecord =
            new LogRecord("127.0.0.1", "user1",
                LocalDate.of(2020, 1, 1),
                "GET /resource HTTP/1.1", 200, 1234,
                "http://referer1.com", "Mozilla/5.0");

        try (MockedStatic<FileReader> fileReaderMock = mockStatic(FileReader.class);
             MockedStatic<Files> filesMock = mockStatic(Files.class)) {
            fileReaderMock.when(() -> FileReader.readFilesFromDirectory(anyString())).thenReturn(mockFiles);

            filesMock.when(() -> Files.lines(any(Path.class))).thenReturn(Stream.of(
                "127.0.0.1 - user1 [01/Jan/2020:00:00:00 +0000] \"GET /resource HTTP/1.1\" 200 1234 \"http://referer1.com\" \"Mozilla/5.0\""));

            List<LogRecord> result =
                LogsReader.readLogs("path/to/logfile", LocalDate.of(2020, 1, 1), LocalDate.of(2020, 12, 31), "status",
                    "200").toList();

            assertEquals(1, result.size());
            assertEquals(logRecord, logParser.parse(
                "127.0.0.1 - user1 [01/Jan/2020:00:00:00 +0000] \"GET /resource HTTP/1.1\" 200 1234 http://referer1.com \"Mozilla/5.0\""));
        }
    }
}
