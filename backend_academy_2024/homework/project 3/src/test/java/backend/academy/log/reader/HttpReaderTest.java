package backend.academy.log.reader;

import backend.academy.log.constants.LogConstants;
import java.io.File;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class HttpReaderTest {

    @Mock
    private HttpClient client;

    @Mock
    private HttpResponse<Path> response;

    private Path tempFile;

    @BeforeEach
    public void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        tempFile = Paths.get(System.getProperty("user.dir")).resolve("logs-temp.txt");
    }

    @AfterEach
    public void tearDown() throws IOException {
        Files.deleteIfExists(tempFile);
    }

    @Test
    public void testReadHttpSuccess() throws IOException, InterruptedException {
        Mockito.when(response.statusCode()).thenReturn(LogConstants.OK_STATUS);
        Mockito.when(response.body()).thenReturn(tempFile);

        Mockito.when(client.send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofFile(tempFile))))
            .thenReturn(response);

        List<File> result = HttpReader.readHttp(
            "https://raw.githubusercontent.com/elastic/examples/master/Common%20Data%20Formats/nginx_logs/nginx_logs");

        assertEquals(1, result.size());
        assertEquals(tempFile.toFile(), result.getFirst());
    }

    @Test
    public void testReadHttpFailure() throws IOException, InterruptedException {
        Mockito.when(response.statusCode()).thenReturn(404);

        Mockito.when(client.send(any(HttpRequest.class), eq(HttpResponse.BodyHandlers.ofFile(tempFile))))
            .thenReturn(response);

        IOException exception =
            assertThrows(IOException.class, () -> HttpReader.readHttp("http://example.com/logfile"));

        assertEquals("Не удалось загрузить файл. Код ошибки: 404", exception.getMessage());
    }
}

