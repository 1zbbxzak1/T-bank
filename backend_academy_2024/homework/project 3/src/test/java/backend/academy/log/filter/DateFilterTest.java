package backend.academy.log.filter;

import backend.academy.log.records.LogRecord;
import java.time.LocalDate;
import java.util.function.Predicate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DateFilterTest {
    private LogFilter logFilter;

    @BeforeEach
    public void setUp() {
        logFilter = new LogFilter();
    }

    @Test
    public void testFilterByDateWithinRange() {
        LocalDate from = LocalDate.of(2020, 1, 1);
        LocalDate to = LocalDate.of(2020, 12, 31);

        Predicate<LogRecord> predicate = logFilter.filterByDate(from, to);

        LogRecord logRecord =
            new LogRecord("127.0.0.1", "user", LocalDate.of(2020, 6, 15), "GET / HTTP/1.1", 200, 1234, null, null);
        assertTrue(predicate.test(logRecord));
    }

    @Test
    public void testFilterByDateBeforeRange() {
        LocalDate from = LocalDate.of(2020, 1, 1);
        LocalDate to = LocalDate.of(2020, 12, 31);

        Predicate<LogRecord> predicate = logFilter.filterByDate(from, to);

        LogRecord logRecord =
            new LogRecord("127.0.0.1", "user", LocalDate.of(2019, 12, 31), "GET / HTTP/1.1", 200, 1234, null, null);
        assertFalse(predicate.test(logRecord));
    }

    @Test
    public void testFilterByDateAfterRange() {
        LocalDate from = LocalDate.of(2020, 1, 1);
        LocalDate to = LocalDate.of(2020, 12, 31);

        Predicate<LogRecord> predicate = logFilter.filterByDate(from, to);

        LogRecord logRecord =
            new LogRecord("127.0.0.1", "user", LocalDate.of(2021, 1, 1), "GET / HTTP/1.1", 200, 1234, null, null);
        assertFalse(predicate.test(logRecord));
    }

    @Test
    public void testFilterByDateOpenStartRange() {
        LocalDate from = null;
        LocalDate to = LocalDate.of(2020, 12, 31);

        Predicate<LogRecord> predicate = logFilter.filterByDate(from, to);

        LogRecord logRecord =
            new LogRecord("127.0.0.1", "user", LocalDate.of(2020, 6, 15), "GET / HTTP/1.1", 200, 1234, null, null);
        assertTrue(predicate.test(logRecord));

        LogRecord logRecordBeforeRange =
            new LogRecord("127.0.0.1", "user", LocalDate.of(2019, 12, 31), "GET / HTTP/1.1", 200, 1234, null, null);
        assertTrue(predicate.test(logRecordBeforeRange));

        LogRecord logRecordAfterRange =
            new LogRecord("127.0.0.1", "user", LocalDate.of(2021, 1, 1), "GET / HTTP/1.1", 200, 1234, null, null);
        assertFalse(predicate.test(logRecordAfterRange));
    }

    @Test
    public void testFilterByDateOpenEndRange() {
        LocalDate from = LocalDate.of(2020, 1, 1);
        LocalDate to = null;

        Predicate<LogRecord> predicate = logFilter.filterByDate(from, to);

        LogRecord logRecord =
            new LogRecord("127.0.0.1", "user", LocalDate.of(2020, 6, 15), "GET / HTTP/1.1", 200, 1234, null, null);
        assertTrue(predicate.test(logRecord));

        LogRecord logRecordBeforeRange =
            new LogRecord("127.0.0.1", "user", LocalDate.of(2019, 12, 31), "GET / HTTP/1.1", 200, 1234, null, null);
        assertFalse(predicate.test(logRecordBeforeRange));

        LogRecord logRecordAfterRange =
            new LogRecord("127.0.0.1", "user", LocalDate.of(2021, 1, 1), "GET / HTTP/1.1", 200, 1234, null, null);
        assertTrue(predicate.test(logRecordAfterRange));
    }
}
