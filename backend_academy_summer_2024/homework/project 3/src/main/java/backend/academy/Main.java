package backend.academy;

import backend.academy.log.pipeline.LogProcessor;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {
    public static void main(String[] args) {
        LogProcessor.start(args);
    }
}
