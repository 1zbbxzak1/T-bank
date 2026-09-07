package backend.academy.log.settings;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileWriterTest {

    private final String fileName = "testOutput.log";
    private ByteArrayOutputStream outContent;
    private PrintStream printStream;

    @BeforeEach
    public void setUp() {
        outContent = new ByteArrayOutputStream();
        printStream = new PrintStream(outContent);
    }

    @AfterEach
    public void tearDown() throws IOException {
        Files.deleteIfExists(Paths.get(fileName));
    }

    @Test
    public void testWriteToFile() throws IOException {
        String content = "This is a test content";

        FileWriter.writeToFile(fileName, content, printStream);

        // Проверка, что файл создан и содержит ожидаемый контент
        Path filePath = Paths.get(fileName);
        assertTrue(Files.exists(filePath));
        assertEquals(content, Files.readString(filePath, StandardCharsets.UTF_8));

        // Проверка, что вывод содержит ожидаемое сообщение
        String output = outContent.toString();
        assertTrue(output.contains("Результаты сохранены в файл: " + fileName));
    }
}
