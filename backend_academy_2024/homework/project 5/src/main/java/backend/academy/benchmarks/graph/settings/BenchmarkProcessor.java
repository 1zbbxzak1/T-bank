package backend.academy.benchmarks.graph.settings;

import backend.academy.benchmarks.constants.Constants;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.tuple.Pair;

@UtilityClass
public class BenchmarkProcessor {
    private static final Map<String, String> BENCHMARK_NAME_MAP = new HashMap<>();

    static {
        List<Pair<String, String>> mappings = List.of(
            Pair.of(
                "backend.academy.benchmarks.reflections.ReflectionBenchmark.directAccess",
                "Прямой доступ"
            ),
            Pair.of(
                "backend.academy.benchmarks.reflections.ReflectionBenchmark.setReflectionMethod",
                "Method"
            ),
            Pair.of(
                "backend.academy.benchmarks.reflections.ReflectionBenchmark.setReflectionMethodHandle",
                "MethodHandles"
            ),
            Pair.of(
                "backend.academy.benchmarks.reflections.ReflectionBenchmark.setLambdaMethod",
                "LambdaMetafactory"
            )
        );

        for (Pair<String, String> mapping : mappings) {
            BENCHMARK_NAME_MAP.put(mapping.getLeft(), mapping.getRight());
        }
    }

    public static String getShortBenchmarkName(String benchmarkName) {
        return BENCHMARK_NAME_MAP.getOrDefault(benchmarkName, benchmarkName);
    }

    public static double extractBenchmarkScoreBar(JsonObject benchmarkObject) {
        return benchmarkObject
            .get(Constants.PRIMARY_METRIC).getAsJsonObject()
            .get("score").getAsDouble();
    }

    public static JsonArray extractBenchmarkScoreLine(JsonObject benchmarkObject) {
        return benchmarkObject
            .get(Constants.PRIMARY_METRIC).getAsJsonObject()
            .get("rawData").getAsJsonArray();
    }
}
