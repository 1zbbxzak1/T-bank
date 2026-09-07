package backend.academy.benchmarks.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {
    public static final String BENCHMARKS_RESULT_PATH = System.getProperty("user.dir") + "/benchmark-results.json";
    public static final int GRAPH_WIDTH = 800;
    public static final int GRAPH_HEIGHT = 600;
    public static final int GRAPH_POINT = -4;
    public static final int POINT_WIDTH_AND_HEIGHT = 8;
    public static final String GRAPH_TITLE = "Результаты Benchmark-тестов";

    public static final String PRIMARY_METRIC = "primaryMetric";
}
