package backend.academy.log.formatter;

import backend.academy.log.constants.LogConstants;
import backend.academy.log.formatter.constants.FormatterConstants;

public class ReportAdocFormatter extends AbstractReportFormatter {
    @Override
    protected String getTitlePrefix() {
        return FormatterConstants.LINE_TITLE_ADOC;
    }

    @Override
    protected String getLineBreak() {
        return FormatterConstants.LINE_BREAK_DOUBLE;
    }

    @Override
    protected String getTableHeader(String... columns) {
        StringBuilder header = new StringBuilder();

        // Определяем формат таблицы
        if (columns.length == LogConstants.THREE_NUMBER) {
            header.append(FormatterConstants.COLS_CODE_RESPONSE).append(FormatterConstants.TABLE_START_SEPARATOR);
        } else {
            header.append(FormatterConstants.COLS_HEADER).append(FormatterConstants.TABLE_START_SEPARATOR);
        }

        // Добавляем заголовки
        for (String col : columns) {
            header.append('|').append(' ').append(col);
        }
        header.append('\n'); // Используем char вместо строки

        return header.toString();
    }

    @Override
    protected String getTableRow(String... values) {
        StringBuilder row = new StringBuilder();
        for (String value : values) {
            row.append('|').append(' ').append(value);
        }
        row.append('\n'); // Используем char вместо строки
        return row.toString();
    }

    @Override
    protected String getTableEnd() {
        return FormatterConstants.TABLE_END_SEPARATOR;
    }
}

