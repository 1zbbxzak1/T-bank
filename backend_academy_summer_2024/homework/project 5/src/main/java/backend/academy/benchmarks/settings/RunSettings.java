package backend.academy.benchmarks.settings;

import backend.academy.benchmarks.constants.Constants;
import backend.academy.benchmarks.graph.graphs.BenchmarkGraphBar;
import backend.academy.benchmarks.graph.graphs.BenchmarkGraphLine;
import backend.academy.benchmarks.reflections.ReflectionBenchmark;
import lombok.experimental.UtilityClass;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@UtilityClass
public class RunSettings {
    public static void start() throws RunnerException {
        Options options = new OptionsBuilder()
            .include(ReflectionBenchmark.class.getSimpleName())
            .shouldFailOnError(true)
            .shouldDoGC(true)
            .result("benchmark-results.json")
            .resultFormat(ResultFormatType.JSON)
            .build();

        new Runner(options).run();

        BenchmarkGraphBar graphBar = new BenchmarkGraphBar(Constants.GRAPH_TITLE);
        graphBar.pack();
        graphBar.setVisible(true);

        BenchmarkGraphLine graphLine = new BenchmarkGraphLine(Constants.GRAPH_TITLE);
        graphLine.pack();
        graphLine.setVisible(true);
    }
}
