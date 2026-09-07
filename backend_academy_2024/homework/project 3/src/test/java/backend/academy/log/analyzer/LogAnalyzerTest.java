package backend.academy.log.analyzer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LogAnalyzerTest {

    private static final String TEST_LOG_FILE = "test_logs.txt";
    private LogAnalyzer logAnalyzer;

    @BeforeEach
    public void setUp() throws IOException {
        String logData = """
            93.180.71.3 - - [17/May/2015:08:05:32 +0000] "GET /downloads/product_1 HTTP/1.1" 304 0 "-" "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)"
            93.180.71.3 - - [17/May/2015:08:05:23 +0000] "GET /downloads/product_1 HTTP/1.1" 304 0 "-" "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)"
            80.91.33.133 - - [17/May/2015:08:05:24 +0000] "GET /downloads/product_1 HTTP/1.1" 304 0 "-" "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.17)"
            217.168.17.5 - - [17/May/2015:08:05:34 +0000] "GET /downloads/product_1 HTTP/1.1" 200 490 "-" "Debian APT-HTTP/1.3 (0.8.10.3)"
            217.168.17.5 - - [17/May/2015:08:05:09 +0000] "GET /downloads/product_2 HTTP/1.1" 200 490 "-" "Debian APT-HTTP/1.3 (0.8.10.3)"
                    """;

        Files.writeString(Path.of(TEST_LOG_FILE), logData);
        logAnalyzer = new LogAnalyzer(TEST_LOG_FILE);
    }

    @AfterEach
    public void tearDown() throws IOException {
        Files.deleteIfExists(Path.of(TEST_LOG_FILE));
    }

    @Test
    public void testCountRequests() {
        assertEquals(5, logAnalyzer.countRequests());
    }

    @Test
    public void testStartDate() {
        assertEquals("17.05.2015", logAnalyzer.startDate());
    }

    @Test
    public void testEndDate() {
        assertEquals("17.05.2015", logAnalyzer.endDate());
    }

    @Test
    public void testMostRequestedResources() {
        Map<String, Long> mostRequested = logAnalyzer.mostRequestedResources();
        assertEquals(2, mostRequested.size());
        assertEquals(4, mostRequested.get("GET /downloads/product_1 HTTP/1.1"));
        assertEquals(1, mostRequested.get("GET /downloads/product_2 HTTP/1.1"));
    }

    @Test
    public void testResponseCodes() {
        Map<Integer, Long> responseCodes = logAnalyzer.responseCodes();
        assertEquals(2, responseCodes.size());
        assertEquals(3, (long) responseCodes.get(304));
        assertEquals(2, (long) responseCodes.get(200));
    }

    @Test
    public void testAverageResponseSize() {
        assertEquals(196, logAnalyzer.averageResponseSize());
    }

    @Test
    public void testPercentile95ResponseSize() {
        assertEquals(490, logAnalyzer.percentile95ResponseSize());
    }

    @Test
    public void testRequestsPerDay() {
        Map<String, Long> requestsPerDay = logAnalyzer.requestsPerDay();
        assertEquals(1, requestsPerDay.size());
        assertEquals(5, (long) requestsPerDay.get("17.05.2015"));
    }

    @Test
    public void testAverageRequestsPerIP() {
        assertEquals(1.6666666666666667, logAnalyzer.averageRequestsPerIP(), 0.0001);
    }
}
