package backend.academy.benchmarks.graph.settings;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DataLoader {
    public static JsonArray loadData(String filePath) throws IOException {
        try (FileReader reader = new FileReader(filePath, StandardCharsets.UTF_8)) {
            return JsonParser.parseReader(reader).getAsJsonArray();
        }
    }
}
