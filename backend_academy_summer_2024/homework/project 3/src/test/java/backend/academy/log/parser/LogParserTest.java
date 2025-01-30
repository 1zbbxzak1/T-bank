package backend.academy.log.parser;

import backend.academy.log.records.LogRecord;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LogParserTest {
    private LogParser logParser;

    @BeforeEach
    public void setUp() {
        logParser = new LogParser();
    }

    @Test
    public void testParseValidLine() {
        String line =
            "54.154.27.80 - frank [26/May/2015:06:05:13 +0000] \"GET /downloads/product_1 HTTP/1.1\" 404 313 \"-\" \"Debian APT-HTTP/1.3 (1.0.1ubuntu2)\"";

        LogRecord logRecord = logParser.parse(line);

        assertNotNull(logRecord);
        assertEquals("54.154.27.80", logRecord.remoteAddr());
        assertEquals("frank", logRecord.remoteUser());
        assertEquals(LocalDate.of(2015, 05, 26), logRecord.time());
        assertEquals("GET /downloads/product_1 HTTP/1.1", logRecord.request());
        assertEquals(404, logRecord.status());
        assertEquals(313, logRecord.bodyBytesSent());
        assertEquals("\"-\"", logRecord.referer());
        assertEquals("Debian APT-HTTP/1.3 (1.0.1ubuntu2)", logRecord.userAgent());
    }

    @Test
    public void testParseInvalidLine() {
        String line = "Invalid log line";

        Exception exception = assertThrows(Exception.class, () -> {
            logParser.parse(line);
        });

        assertNotNull(exception);
    }
}
