package backend.academy.benchmarks.graph;

import backend.academy.benchmarks.graph.settings.DataLoader;
import com.google.gson.JsonArray;
import com.google.gson.JsonSyntaxException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class DataLoaderTest {
    @TempDir
    private Path tempDir;

    @Test
    public void validJsonFile() throws IOException {
        File tempFile = tempDir.resolve("test.json").toFile();
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("[\"element1\", \"element2\", \"element3\"]");
        }

        JsonArray result = DataLoader.loadData(tempFile.getAbsolutePath());

        Assertions.assertNotNull(result, "Результаты не должны быть нулевыми.");
        Assertions.assertEquals(3, result.size(), "JSON array должен содержать 3 элемента.");
        Assertions.assertEquals("element1", result.get(0).getAsString(), "Несоответствие первого элемента.");
        Assertions.assertEquals("element2", result.get(1).getAsString(), "Несоответствие второго элемента.");
        Assertions.assertEquals("element3", result.get(2).getAsString(), "Несоответствие третьего элемента.");
    }

    @Test
    public void emptyJsonFile() throws IOException {
        File tempFile = tempDir.resolve("empty.json").toFile();
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("[]");
        }

        JsonArray result = DataLoader.loadData(tempFile.getAbsolutePath());

        Assertions.assertNotNull(result, "\"Результаты не должны быть нулевыми.");
        Assertions.assertTrue(result.isJsonArray(), "Результатом должен быть JSON array.");
        Assertions.assertEquals(0, result.size(), "JSON array не должен быть пустым.");
    }

    @Test
    public void invalidJsonFile() {
        File tempFile = tempDir.resolve("invalid.json").toFile();
        try (FileWriter writer = new FileWriter(tempFile)) {
            writer.write("Это не JSON файл.");
        } catch (IOException e) {
            Assertions.fail("Не удалось настроить тест: " + e.getMessage());
        }

        Assertions.assertThrows(JsonSyntaxException.class, () -> DataLoader.loadData(tempFile.getAbsolutePath()),
            "Для недопустимого JSON должно быть создано исключение JsonSyntaxException.");
    }

    @Test
    public void fileNotFound() {
        String nonExistentFilePath = tempDir.resolve("nonexistent.json").toString();

        Assertions.assertThrows(IOException.class, () -> DataLoader.loadData(nonExistentFilePath),
            "Для несуществующего файла должно быть выдано исключение IOException.");
    }
}
