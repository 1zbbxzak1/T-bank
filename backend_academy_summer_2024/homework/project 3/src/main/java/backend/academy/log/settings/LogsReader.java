package backend.academy.log.settings;

import backend.academy.log.filter.LogFilter;
import backend.academy.log.parser.LogParser;
import backend.academy.log.reader.LogReader;
import backend.academy.log.records.LogRecord;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;
import lombok.experimental.UtilityClass;

@UtilityClass
public class LogsReader {
    public static Stream<LogRecord> readLogs(
        String path,
        LocalDate from,
        LocalDate to,
        String filterField,
        String filterValue
    ) throws IOException {
        LogReader reader = new LogReader();
        LogParser parser = new LogParser();
        LogFilter filter = new LogFilter();

        Predicate<LogRecord> dateFilter = filter.filterByDate(from, to);
        Predicate<LogRecord> fieldFilter;

        if (filterField != null && filterValue != null) {
            fieldFilter = filter.filterByField(filterField, filterValue);
        } else {
            fieldFilter = logRecord -> true;
        }

        List<File> files = reader.read(path);

        return files.stream()
            .flatMap(file -> {
                try {
                    return Files.lines(file.toPath())
                        .map(parser::parse) // Преобразуем строки в LogRecord
                        .filter(dateFilter.and(fieldFilter)); // Применяем фильтрацию на уровне потока
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
    }
}
