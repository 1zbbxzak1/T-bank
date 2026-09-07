package backend.academy;

import backend.academy.benchmarks.settings.RunSettings;
import lombok.experimental.UtilityClass;
import org.openjdk.jmh.runner.RunnerException;

@UtilityClass
public class Main {
    public static void main(String[] args) throws RunnerException {
        RunSettings.start();
    }
}
