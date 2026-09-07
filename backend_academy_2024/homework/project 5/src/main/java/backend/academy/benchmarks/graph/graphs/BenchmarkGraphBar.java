package backend.academy.benchmarks.graph.graphs;

import backend.academy.benchmarks.constants.Constants;
import backend.academy.benchmarks.graph.enums.GraphColors;
import backend.academy.benchmarks.graph.graphs.abstracts.BenchmarkGraphBase;
import backend.academy.benchmarks.graph.settings.BenchmarkProcessor;
import backend.academy.benchmarks.graph.settings.custom.CustomBarRenderer;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.awt.Color;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.DefaultCategoryDataset;

public class BenchmarkGraphBar extends BenchmarkGraphBase {
    public BenchmarkGraphBar(String title) {
        super(title);

        setupGraph(
            title,
            Constants.BENCHMARKS_RESULT_PATH,
            "Название",
            "Время (ns/op)"
        );
    }

    @Override
    protected JFreeChart createChart(String title, String xAxisLabel, String yAxisLabel, Object dataset) {
        JFreeChart chart = ChartFactory.createBarChart(
            title,
            xAxisLabel,
            yAxisLabel,
            (DefaultCategoryDataset) dataset
        );

        CategoryPlot plot = chart.getCategoryPlot();
        CustomBarRenderer renderer = new CustomBarRenderer(GraphColors.getColors());

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinePaint(Color.BLACK);
        plot.setRangeGridlinePaint(Color.BLACK);

        return chart;
    }

    @Override
    protected DefaultCategoryDataset createDataset(String filePath) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        JsonArray benchmarks = loadBenchmarkData(filePath);

        for (JsonElement benchmark : benchmarks) {
            JsonObject benchmarkObject = benchmark.getAsJsonObject();

            String benchmarkName = benchmarkObject.get("benchmark").getAsString();
            String shortBenchmarkName = BenchmarkProcessor.getShortBenchmarkName(benchmarkName);

            double benchmarkScore = BenchmarkProcessor.extractBenchmarkScoreBar(benchmarkObject);

            dataset.addValue(benchmarkScore, "", shortBenchmarkName);
        }

        return dataset;
    }
}
