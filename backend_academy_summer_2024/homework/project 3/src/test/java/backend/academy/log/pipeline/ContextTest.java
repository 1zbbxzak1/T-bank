package backend.academy.log.pipeline;

import backend.academy.log.settings.enums.ReportFormat;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ContextTest {

    private Context context;
    private ByteArrayOutputStream outContent;

    @BeforeEach
    public void setUp() {
        context = new Context();
        outContent = new ByteArrayOutputStream();
        context.out = new PrintStream(outContent);
    }

    @Test
    public void testInitialization() {
        assertNull(context.path);
        assertNull(context.fromDate);
        assertNull(context.toDate);
        assertNull(context.format);
        assertNull(context.filterField);
        assertNull(context.filterValue);
        assertNull(context.reportContent);
        assertNotNull(context.out);
        assertFalse(context.hasError);
    }

    @Test
    public void testSetError() {
        String errorMessage = "Test error message";
        context.setError(errorMessage);

        assertTrue(context.hasError);
        String output = outContent.toString();
        assertTrue(output.contains(errorMessage));
    }

    @Test
    public void testFieldAssignments() {
        context.path = "path/to/logfile";
        context.fromDate = LocalDate.of(2020, 1, 1);
        context.toDate = LocalDate.of(2020, 12, 31);
        context.format = ReportFormat.MARKDOWN;
        context.filterField = "status";
        context.filterValue = "200";

        context.reportContent = "Sample Report Content";

        assertEquals("path/to/logfile", context.path);
        assertEquals(LocalDate.of(2020, 1, 1), context.fromDate);
        assertEquals(LocalDate.of(2020, 12, 31), context.toDate);
        assertEquals(ReportFormat.MARKDOWN, context.format);
        assertEquals("status", context.filterField);
        assertEquals("200", context.filterValue);
        assertEquals("Sample Report Content", context.reportContent);
    }
}
