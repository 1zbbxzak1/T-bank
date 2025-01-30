package backend.academy.benchmarks.graph.graphs;

import backend.academy.benchmarks.constants.Constants;
import backend.academy.benchmarks.graph.enums.GraphColors;
import backend.academy.benchmarks.graph.graphs.abstracts.BenchmarkGraphBase;
import backend.academy.benchmarks.graph.settings.BenchmarkProcessor;
import backend.academy.benchmarks.graph.settings.custom.CustomLineRenderer;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Ellipse2D;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class BenchmarkGraphLine extends BenchmarkGraphBase {
    public BenchmarkGraphLine(String title) {
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
        JFreeChart chart = ChartFactory.createXYLineChart(
            title,
            xAxisLabel,
            yAxisLabel,
            (XYSeriesCollection) dataset
        );

        XYPlot plot = chart.getXYPlot();
        CustomLineRenderer renderer = new CustomLineRenderer(GraphColors.getColors());

        for (int i = 0; i < ((XYSeriesCollection) dataset).getSeriesCount(); i++) {
            renderer.setSeriesStroke(i, new BasicStroke(2.0f));
            renderer.setSeriesShape(i, new Ellipse2D.Double(
                Constants.GRAPH_POINT,
                Constants.GRAPH_POINT,
                Constants.POINT_WIDTH_AND_HEIGHT,
                Constants.POINT_WIDTH_AND_HEIGHT
            ));
            renderer.setSeriesPaint(i, GraphColors.getColors()[i % GraphColors.getColors().length]);
        }

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinePaint(Color.BLACK);
        plot.setRangeGridlinePaint(Color.BLACK);

        return chart;
    }

    @Override
    protected XYSeriesCollection createDataset(String filePath) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        JsonArray benchmarks = loadBenchmarkData(filePath);

        for (JsonElement benchmark : benchmarks) {
            JsonObject benchmarkObject = benchmark.getAsJsonObject();

            String benchmarkName = benchmarkObject.get("benchmark").getAsString();
            String shortBenchmarkName = BenchmarkProcessor.getShortBenchmarkName(benchmarkName);

            XYSeries series = new XYSeries(shortBenchmarkName);

            JsonArray rawData = BenchmarkProcessor.extractBenchmarkScoreLine(benchmarkObject);
            int iteration = 1;
            for (JsonElement fork : rawData) {
                for (JsonElement iterationValue : fork.getAsJsonArray()) {
                    series.add(iteration++, iterationValue.getAsDouble());
                }
            }

            dataset.addSeries(series);
        }

        return dataset;
    }
}
