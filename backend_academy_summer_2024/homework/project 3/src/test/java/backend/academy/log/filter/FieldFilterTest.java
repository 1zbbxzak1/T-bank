package backend.academy.log.filter;

import backend.academy.log.records.LogRecord;
import java.time.LocalDate;
import java.util.function.Predicate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FieldFilterTest {
    private LogFilter logFilter;

    @BeforeEach
    public void setUp() {
        logFilter = new LogFilter();
    }

    @Test
    public void testFilterByRemoteAddr() {
        String remoteAddr = "127.0.0.1";
        Predicate<LogRecord> predicate = logFilter.filterByField("remote_addr", remoteAddr);

        LogRecord logRecord =
            new LogRecord(remoteAddr, "user", LocalDate.of(2020, 6, 15), "GET / HTTP/1.1", 200, 1234, null, null);
        assertTrue(predicate.test(logRecord));

        LogRecord anotherLogRecord =
            new LogRecord("192.168.1.1", "user", LocalDate.of(2020, 6, 15), "GET / HTTP/1.1", 200, 1234, null, null);
        assertFalse(predicate.test(anotherLogRecord));
    }

    @Test
    public void testFilterByRemoteUser() {
        String remoteUser = "user";
        Predicate<LogRecord> predicate = logFilter.filterByField("remote_user", remoteUser);

        LogRecord logRecord =
            new LogRecord("127.0.0.1", remoteUser, LocalDate.of(2020, 6, 15), "GET / HTTP/1.1", 200, 1234, null, null);
        assertTrue(predicate.test(logRecord));

        LogRecord anotherLogRecord =
            new LogRecord("127.0.0.1", "anotherUser", LocalDate.of(2020, 6, 15), "GET / HTTP/1.1", 200, 1234, null,
                null);
        assertFalse(predicate.test(anotherLogRecord));
    }

    @Test
    public void testFilterByHttpMethod() {
        String httpMethod = "GET";
        Predicate<LogRecord> predicate = logFilter.filterByField("http_method", httpMethod);

        LogRecord logRecord =
            new LogRecord("127.0.0.1", "user", LocalDate.of(2020, 6, 15), httpMethod + " / HTTP/1.1", 200, 1234, null,
                null);
        assertTrue(predicate.test(logRecord));

        LogRecord anotherLogRecord =
            new LogRecord("127.0.0.1", "user", LocalDate.of(2020, 6, 15), "POST / HTTP/1.1", 200, 1234, null, null);
        assertFalse(predicate.test(anotherLogRecord));
    }

    @Test
    public void testFilterByHttpPath() {
        String httpPath = "/test";
        Predicate<LogRecord> predicate = logFilter.filterByField("http_path", httpPath);

        LogRecord logRecord =
            new LogRecord("127.0.0.1", "user", LocalDate.of(2020, 6, 15), "GET " + httpPath + " HTTP/1.1", 200, 1234,
                null, null);
        assertTrue(predicate.test(logRecord));

        LogRecord anotherLogRecord =
            new LogRecord("127.0.0.1", "user", LocalDate.of(2020, 6, 15), "GET /other HTTP/1.1", 200, 1234, null, null);
        assertFalse(predicate.test(anotherLogRecord));
    }

    @Test
    public void testFilterByHttpVersion() {
        String httpVersion = "HTTP/1.1";
        Predicate<LogRecord> predicate = logFilter.filterByField("http_version", httpVersion);

        LogRecord logRecord =
            new LogRecord("127.0.0.1", "user", LocalDate.of(2020, 6, 15), "GET / " + httpVersion, 200, 1234, null,
                null);
        assertTrue(predicate.test(logRecord));

        LogRecord anotherLogRecord =
            new LogRecord("127.0.0.1", "user", LocalDate.of(2020, 6, 15), "GET / HTTP/2.0", 200, 1234, null, null);
        assertFalse(predicate.test(anotherLogRecord));
    }

    @Test
    public void testFilterByStatus() {
        String status = "200";
        Predicate<LogRecord> predicate = logFilter.filterByField("status", status);

        LogRecord logRecord =
            new LogRecord("127.0.0.1", "user", LocalDate.of(2020, 6, 15), "GET / HTTP/1.1", Integer.parseInt(status),
                1234, null, null);
        assertTrue(predicate.test(logRecord));

        LogRecord anotherLogRecord =
            new LogRecord("127.0.0.1", "user", LocalDate.of(2020, 6, 15), "GET / HTTP/1.1", 404, 1234, null, null);
        assertFalse(predicate.test(anotherLogRecord));
    }

    @Test
    public void testFilterByBytesSent() {
        String bytesSent = "1234";
        Predicate<LogRecord> predicate = logFilter.filterByField("bytes_sent", bytesSent);

        LogRecord logRecord = new LogRecord("127.0.0.1", "user", LocalDate.of(2020, 6, 15), "GET / HTTP/1.1", 200,
            Long.parseLong(bytesSent), null, null);
        assertTrue(predicate.test(logRecord));

        LogRecord anotherLogRecord =
            new LogRecord("127.0.0.1", "user", LocalDate.of(2020, 6, 15), "GET / HTTP/1.1", 200, 5678, null, null);
        assertFalse(predicate.test(anotherLogRecord));
    }

    @Test
    public void testFilterByReferer() {
        String referer = "http://example.com";
        Predicate<LogRecord> predicate = logFilter.filterByField("referer", referer);

        LogRecord logRecord =
            new LogRecord("127.0.0.1", "user", LocalDate.of(2020, 6, 15), "GET / HTTP/1.1", 200, 1234, referer, null);
        assertTrue(predicate.test(logRecord));

        LogRecord anotherLogRecord =
            new LogRecord("127.0.0.1", "user", LocalDate.of(2020, 6, 15), "GET / HTTP/1.1", 200, 1234,
                "http://other.com", null);
        assertFalse(predicate.test(anotherLogRecord));
    }

    @Test
    public void testFilterByAgent() {
        String userAgent = "Mozilla";
        Predicate<LogRecord> predicate = logFilter.filterByField("agent", userAgent);

        LogRecord logRecord =
            new LogRecord("127.0.0.1", "user", LocalDate.of(2020, 6, 15), "GET / HTTP/1.1", 200, 1234, null, userAgent);
        assertTrue(predicate.test(logRecord));

        LogRecord anotherLogRecord =
            new LogRecord("127.0.0.1", "user", LocalDate.of(2020, 6, 15), "GET / HTTP/1.1", 200, 1234, null, "Chrome");
        assertFalse(predicate.test(anotherLogRecord));
    }
}
