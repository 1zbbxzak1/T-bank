package backend.academy.benchmarks.graph.graphs.abstracts;

import backend.academy.benchmarks.constants.Constants;
import backend.academy.benchmarks.graph.settings.DataLoader;
import com.google.gson.JsonArray;
import java.io.IOException;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ui.ApplicationFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BenchmarkGraphBase extends ApplicationFrame {
    private static final Logger LOGGER = LoggerFactory.getLogger(BenchmarkGraphBase.class);

    public BenchmarkGraphBase(String title) {
        super(title);
    }

    protected abstract JFreeChart createChart(
        String title,
        String xAxisLabel,
        String yAxisLabel,
        Object dataset
    );

    protected abstract Object createDataset(String filePath);

    public void setupGraph(String title, String filePath, String xAxisLabel, String yAxisLabel) {
        Object dataset = createDataset(filePath);
        JFreeChart chart = createChart(title, xAxisLabel, yAxisLabel, dataset);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(Constants.GRAPH_WIDTH, Constants.GRAPH_HEIGHT));
        setContentPane(chartPanel);
    }

    protected JsonArray loadBenchmarkData(String filePath) {
        try {
            return DataLoader.loadData(filePath);
        } catch (IOException e) {
            LOGGER.error("Error loading benchmark data from file: {}", filePath, e);
            return new JsonArray();
        }
    }
}
