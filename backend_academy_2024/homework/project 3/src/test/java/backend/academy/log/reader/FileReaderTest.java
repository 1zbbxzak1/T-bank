package backend.academy.log.reader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileReaderTest {
    private static final String TEST_DIRECTORY = "test_files";
    private static final String FILE1 = "file1.txt";
    private static final String FILE2 = "file2.log";

    @BeforeEach
    void setup() throws IOException {
        Path testDir = Path.of(TEST_DIRECTORY);
        if (Files.exists(testDir)) {
            Files.walk(testDir)
                .sorted(Comparator.reverseOrder())
                .forEach(path -> {
                    try {
                        Files.delete(path);
                    } catch (IOException e) {
                        throw new RuntimeException("Ошибка при очистке тестовой директории", e);
                    }
                });
        }

        Files.createDirectory(testDir);
        try {
            Files.createFile(testDir.resolve(FILE1));
            Files.createFile(testDir.resolve(FILE2));
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при создании тестовых файлов", e);
        }
    }

    @Test
    void testReadFileMatchingPattern() {
        // Проверяем, что метод находит файлы по шаблону
        String pattern = TEST_DIRECTORY + "/*.txt";
        List<File> files = FileReader.readFilesFromDirectory(pattern);

        assertEquals(1, files.size());
        assertEquals(FILE1, files.getFirst().getName());
    }

    @Test
    void testReadFileNoMatches() {
        // Проверяем, что метод возвращает пустой список, если нет совпадений
        String pattern = TEST_DIRECTORY + "/*.xml";
        List<File> files = FileReader.readFilesFromDirectory(pattern);

        assertTrue(files.isEmpty());
    }

    @Test
    void testReadFileMultipleMatches() throws IOException {
        // Создаем дополнительные файлы для проверки
        Path additionalFile1 = Path.of(TEST_DIRECTORY, "test1.txt");
        Path additionalFile2 = Path.of(TEST_DIRECTORY, "test2.txt");
        Files.createFile(additionalFile1);
        Files.createFile(additionalFile2);

        try {
            String pattern = TEST_DIRECTORY + "/*.txt";
            List<File> files = FileReader.readFilesFromDirectory(pattern);

            assertEquals(3, files.size());
            assertTrue(
                files.stream().anyMatch(file -> file.getName().equals(additionalFile1.getFileName().toString())));
            assertTrue(
                files.stream().anyMatch(file -> file.getName().equals(additionalFile2.getFileName().toString())));
        } finally {
            // Удаляем дополнительные файлы
            Files.deleteIfExists(additionalFile1);
            Files.deleteIfExists(additionalFile2);
        }
    }

    @Test
    void testReadFileNot() throws IOException {
        Path restrictedFile = Path.of(TEST_DIRECTORY, "restricted.txt");
        Files.createFile(restrictedFile);

        try {
            String pattern = TEST_DIRECTORY + "/*.log";
            List<File> files = FileReader.readFilesFromDirectory(pattern);

            assertTrue(
                files.stream().noneMatch(file -> file.getName().equals(restrictedFile.getFileName().toString())));
        } finally {
            Files.deleteIfExists(restrictedFile);
        }
    }
}
