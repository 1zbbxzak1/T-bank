package backend.academy.log.reader;

import backend.academy.log.constants.LogConstants;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class HttpReader {
    private static final Path TEMP_DIR = Paths.get(System.getProperty("user.dir"));

    public static List<File> readHttp(String url) throws IOException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .build();

        Path tempFile = TEMP_DIR.resolve("logs-temp.txt");

        try {
            HttpResponse<Path> response = client.send(request, HttpResponse.BodyHandlers.ofFile(tempFile));
            if (response.statusCode() != LogConstants.OK_STATUS) {
                throw new IOException("Не удалось загрузить файл. Код ошибки: " + response.statusCode());
            }

            // Преобразуем загруженный файл в объект File и возвращаем его в список
            File downloadedFile = tempFile.toFile();

            return Collections.singletonList(downloadedFile);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IOException("Загрузка прервана", e);
        }
    }
}
