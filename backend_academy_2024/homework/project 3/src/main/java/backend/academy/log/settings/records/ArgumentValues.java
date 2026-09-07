package backend.academy.log.settings.records;

import backend.academy.log.settings.enums.ReportFormat;
import com.beust.jcommander.Parameter;
import lombok.Getter;

@Getter
public class ArgumentValues {
    @Parameter(description = "Main command")
    private String mainCommand;
    @Parameter(names = "--path", description = "Path to the log file", required = true)
    private String path;
    @Parameter(names = "--from", description = "Start date for filtering logs")
    private String fromDate;
    @Parameter(names = "--to", description = "End date for filtering logs")
    private String toDate;
    @Parameter(names = "--filter-field", description = "Field to filter logs by")
    private String filterField;
    @Parameter(names = "--filter-value", description = "Value of the field to filter logs by")
    private String filterValue;
    @Parameter(names = "--format", description = "Report format (e.g., markdown or adoc)")
    private ReportFormat format = ReportFormat.MARKDOWN;
    @Parameter(names = "--help", help = true, description = "Display help information")
    private boolean help;
}
