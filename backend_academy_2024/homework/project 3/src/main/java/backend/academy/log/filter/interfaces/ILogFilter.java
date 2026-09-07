package backend.academy.log.filter.interfaces;

import backend.academy.log.records.LogRecord;
import java.time.LocalDate;
import java.util.function.Predicate;

public interface ILogFilter {
    Predicate<LogRecord> filterByDate(LocalDate from, LocalDate to);

    Predicate<LogRecord> filterByField(String field, String value);
}
