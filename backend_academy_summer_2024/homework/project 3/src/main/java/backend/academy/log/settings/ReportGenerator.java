package backend.academy.log.settings;

import backend.academy.log.analyzer.LogAnalyzer;
import backend.academy.log.formatter.AbstractReportFormatter;
import backend.academy.log.formatter.ReportAdocFormatter;
import backend.academy.log.formatter.ReportMarkdownFormatter;
import backend.academy.log.settings.enums.ReportFormat;
import java.io.PrintStream;
import java.time.LocalDate;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ReportGenerator {
    public static String generateReport(
        ReportFormat format,
        String logRecordPath,
        LocalDate from,
        LocalDate to,
        String field,
        String value,
        PrintStream out
    ) {
        LogAnalyzer analyzer = new LogAnalyzer(logRecordPath).withDateFilter(from, to).withFieldFilter(field, value);

        if (format == null) {
            out.println("Ошибка: Неверный формат вывода. Используйте markdown или adoc.");
            return null;
        }

        AbstractReportFormatter formatter = switch (format) {
            case MARKDOWN -> new ReportMarkdownFormatter();
            case ADOC -> new ReportAdocFormatter();
        };
        return formatter.format(analyzer);
    }
}
