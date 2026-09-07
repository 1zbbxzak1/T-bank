package backend.academy.log.pipeline;

import backend.academy.log.settings.enums.ReportFormat;
import java.io.PrintStream;
import java.time.LocalDate;

public class Context {
    public String path;
    public LocalDate fromDate;
    public LocalDate toDate;
    public ReportFormat format;
    public String filterField;
    public String filterValue;
    public String reportContent;
    public PrintStream out = System.out;
    public boolean hasError = false;

    public void setError(String errorMessage) {
        out.println(errorMessage);
        hasError = true;
    }
}
