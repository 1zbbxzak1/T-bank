package backend.academy.log.formatter;

import backend.academy.log.constants.LogConstants;
import backend.academy.log.formatter.constants.FormatterConstants;

public class ReportMarkdownFormatter extends AbstractReportFormatter {
    @Override
    protected String getTitlePrefix() {
        return FormatterConstants.LINE_TITLE_MARKDOWN;
    }

    @Override
    protected String getLineBreak() {
        return FormatterConstants.LINE_BREAK_DOUBLE;
    }

    @Override
    protected String getTableHeader(String... columns) {
        StringBuilder header = new StringBuilder();
        header.append('|'); // Используем символ вместо строки
        for (String col : columns) {
            header.append(col).append('|'); // Используем символ вместо строки
        }
        header.append('\n'); // Используем символ вместо строки

        // Генерация строки выравнивания для заголовков
        header.append('|');
        for (String col : columns) {
            header.append(':').append("-".repeat(Math.max(0, col.length() - LogConstants.TWO_NUMBER))).append(':')
                .append('|');
        }
        header.append('\n'); // Используем символ вместо строки

        return header.toString();
    }

    @Override
    protected String getTableRow(String... values) {
        StringBuilder row = new StringBuilder();
        row.append('|'); // Используем символ вместо строки
        for (String value : values) {
            row.append(value).append('|'); // Используем символ вместо строки
        }
        row.append('\n'); // Используем символ вместо строки
        return row.toString();
    }

    @Override
    protected String getTableEnd() {
        return FormatterConstants.LINE_BREAK; // Тут изменения не требуются
    }
}
