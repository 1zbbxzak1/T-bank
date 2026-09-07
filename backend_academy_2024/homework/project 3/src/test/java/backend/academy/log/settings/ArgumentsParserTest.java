package backend.academy.log.settings;

import backend.academy.log.settings.enums.ReportFormat;
import backend.academy.log.settings.records.ArgumentValues;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ArgumentsParserTest {

    @Test
    public void testProcessArgumentsWithAllArgs() {
        String[] args = {
            "mainCommand",
            "--path", "logfile.log",
            "--from", "2020-01-01",
            "--to", "2020-12-31",
            "--filter-field", "status",
            "--filter-value", "200",
            "--format", "adoc"
        };

        ArgumentValues arguments = ArgumentsParser.processArguments(args);

        assertEquals("mainCommand", arguments.mainCommand());
        assertEquals("logfile.log", arguments.path());
        assertEquals("2020-01-01", arguments.fromDate());
        assertEquals("2020-12-31", arguments.toDate());
        assertEquals("status", arguments.filterField());
        assertEquals("200", arguments.filterValue());
        assertEquals(ReportFormat.ADOC, arguments.format());
        assertFalse(arguments.help());
    }

    @Test
    public void testProcessArgumentsWithHelp() {
        String[] args = {"--help"};

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
            ArgumentsParser.processArguments(args)
        );

        assertNotNull(exception);
        assertEquals("Help called", exception.getMessage());
    }

    @Test
    public void testProcessArgumentsMissingRequiredArgs() {
        String[] args = {"mainCommand"};

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
            ArgumentsParser.processArguments(args)
        );

        assertNotNull(exception);
    }

    @Test
    public void testProcessArgumentsWithDefaultFormat() {
        String[] args = {
            "mainCommand",
            "--path", "logfile.log"
        };

        ArgumentValues arguments = ArgumentsParser.processArguments(args);

        assertEquals("mainCommand", arguments.mainCommand());
        assertEquals("logfile.log", arguments.path());
        assertEquals(ReportFormat.MARKDOWN, arguments.format());
    }
}
