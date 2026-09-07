package backend.academy.log.settings;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DateParserTest {

    private ByteArrayOutputStream outContent;
    private PrintStream printStream;

    @BeforeEach
    public void setUp() {
        outContent = new ByteArrayOutputStream();
        printStream = new PrintStream(outContent);
    }

    @Test
    public void testParseValidDate() {
        String dateStr = "2024-09-01";
        LocalDate expectedDate = LocalDate.of(2024, 9, 1);

        LocalDate result = DateParser.parseDate(dateStr, printStream);

        assertEquals(expectedDate, result);
        assertTrue(outContent.toString().isEmpty());
    }

    @Test
    public void testParseInvalidDate() {
        String dateStr = "invalid-date";

        LocalDate result = DateParser.parseDate(dateStr, printStream);

        assertNull(result);
        assertTrue(outContent.toString()
            .contains("Ошибка: некорректный формат даты. Используйте ISO8601 (например, 2024-09-01)."));
    }

    @Test
    public void testParseNullDate() {
        LocalDate result = DateParser.parseDate(null, printStream);

        assertNull(result);
        assertTrue(outContent.toString().isEmpty());
    }
}
