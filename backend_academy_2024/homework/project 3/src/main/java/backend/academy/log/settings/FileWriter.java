package backend.academy.log.settings;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FileWriter {
    public static void writeToFile(String fileName, String content, PrintStream out) throws IOException {
        File file = new File(fileName);
        try (java.io.FileWriter writer = new java.io.FileWriter(file, StandardCharsets.UTF_8)) {
            writer.write(content);
        }
        out.println("Результаты сохранены в файл: " + fileName);
    }
}
