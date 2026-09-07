package backend.academy.log.settings;

import backend.academy.log.settings.records.ArgumentValues;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ArgumentsParser {
    public static ArgumentValues processArguments(String[] args) {
        ArgumentValues arguments = new ArgumentValues();
        JCommander jCommander = JCommander.newBuilder()
            .addObject(arguments)
            .build();

        try {
            jCommander.parse(args);
        } catch (ParameterException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }

        if (arguments.help()) {
            throw new IllegalArgumentException("Help called");
        }

        return arguments;
    }
}
