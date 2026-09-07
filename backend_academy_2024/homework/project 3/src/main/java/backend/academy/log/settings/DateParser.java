package backend.academy.log.settings;

import java.io.PrintStream;
import java.time.LocalDate;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DateParser {
    public static LocalDate parseDate(String date, PrintStream out) {
        try {
            return date != null ? LocalDate.parse(date) : null;
        } catch (Exception e) {
            out.println("Ошибка: некорректный формат даты. Используйте ISO8601 (например, 2024-09-01).");
            return null;
        }
    }
}
