package backend.academy.benchmarks.graph;

import backend.academy.benchmarks.constants.Constants;
import backend.academy.benchmarks.graph.settings.BenchmarkProcessor;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BenchmarkProcessorTest {
    @Test
    void existingName() {
        String inputName = "backend.academy.benchmarks.reflections.ReflectionBenchmark.directAccess";
        String expectedName = "Прямой доступ";

        String result = BenchmarkProcessor.getShortBenchmarkName(inputName);

        Assertions.assertEquals(expectedName, result, "Ожидается правильное сокращённое имя.");
    }

    @Test
    void nonExistingName() {
        String inputName = "non.existent.benchmark";

        String result = BenchmarkProcessor.getShortBenchmarkName(inputName);

        Assertions.assertEquals(inputName, result,
            "Ожидается, что метод вернёт исходное имя для неизвестного бенчмарка.");
    }

    @Test
    void validBenchmarkObjectScoreBar() {
        JsonObject benchmarkObject = new JsonObject();
        JsonObject primaryMetric = new JsonObject();
        primaryMetric.addProperty("score", 123.45);
        benchmarkObject.add(Constants.PRIMARY_METRIC, primaryMetric);

        double result = BenchmarkProcessor.extractBenchmarkScoreBar(benchmarkObject);

        Assertions.assertEquals(123.45, result, 0.001, "Ожидается правильное значение score.");
    }

    @Test
    void missingScore() {
        JsonObject benchmarkObject = new JsonObject();
        benchmarkObject.add(Constants.PRIMARY_METRIC, new JsonObject());

        Assertions.assertThrows(NullPointerException.class, () ->
                BenchmarkProcessor.extractBenchmarkScoreBar(benchmarkObject),
            "Ожидается исключение, если score отсутствует.");
    }

    @Test
    void validBenchmarkObjectScoreLine() {
        JsonObject benchmarkObject = new JsonObject();
        JsonObject primaryMetric = new JsonObject();
        JsonArray rawData = new JsonArray();
        rawData.add(100.1);
        rawData.add(200.2);
        primaryMetric.add("rawData", rawData);
        benchmarkObject.add(Constants.PRIMARY_METRIC, primaryMetric);

        JsonArray result = BenchmarkProcessor.extractBenchmarkScoreLine(benchmarkObject);

        Assertions.assertNotNull(result, "Ожидается, что результат не null.");
        Assertions.assertEquals(2, result.size(), "Ожидается 2 элемента в массиве rawData.");
        Assertions.assertEquals(100.1, result.get(0).getAsDouble(), 0.001, "Первый элемент должен быть равен 100.1.");
        Assertions.assertEquals(200.2, result.get(1).getAsDouble(), 0.001, "Второй элемент должен быть равен 200.2.");
    }

    @Test
    void missingRawData() {
        JsonObject benchmarkObject = new JsonObject();
        benchmarkObject.add(Constants.PRIMARY_METRIC, new JsonObject());

        Assertions.assertThrows(NullPointerException.class, () ->
                BenchmarkProcessor.extractBenchmarkScoreLine(benchmarkObject),
            "Ожидается исключение, если rawData отсутствует.");
    }
}
