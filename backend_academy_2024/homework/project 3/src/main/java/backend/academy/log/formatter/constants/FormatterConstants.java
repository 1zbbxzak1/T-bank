package backend.academy.log.formatter.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public class FormatterConstants {
    public static final String TITLE_GENERAL_INFORMATION = "Общая информация";
    public static final String COL_GI_METRIC = "Метрика";
    public static final String COL_GI_MEANING = "Значение";
    public static final String PARAM_GI_STARTING_DATE = "Начальная дата";
    public static final String PARAM_GI_END_DATE = "Конечная дата";
    public static final String PARAM_GI_NUMBER_REQUESTS = "Количество запросов";
    public static final String PARAM_GI_NUMBER_AVG_RESPONSE_SIZE = "Средний размер ответа";
    public static final String PARAM_GI_NUMBER_AVG_RESPONSE_IP = "Среднее количество запросов от уникального IP";
    public static final String PARAM_GI_95_PERCENTILE = "95p размера ответа";

    public static final String TITLE_REQUESTED_RESOURCES = "Запрашиваемые ресурсы";
    public static final String COL_RR_RESOURCE = "Ресурс";

    public static final String TITLE_RESPONSE_CODES = "Коды ответа";
    public static final String COL_RC_CODE = "Код";
    public static final String COL_RC_NAME = "Имя";

    public static final String TITLE_RESPONSE_DAYS = "Распределение запросов по дням";
    public static final String COL_RD_DAY = "День";

    public static final String COL_QUANTITY = "Количество";
    public static final String UNIT_MEASUREMENT = "b";

    public static final String COLS_HEADER = "[cols=\"1,1\",options=\"header\"]";
    public static final String COLS_CODE_RESPONSE = "[cols=\"1,2,1\",options=\"header\"]";
    public static final String LINE_TITLE_ADOC = "== ";
    public static final String LINE_TITLE_2_ADOC = "\n== ";
    public static final String TABLE_START_SEPARATOR = "\n|===\n";
    public static final String TABLE_END_SEPARATOR = "|===\n\n";

    public static final String LINE_TITLE_MARKDOWN = "#### ";
    public static final String LINE_NEW_TITLE_MARKDOWN = "\n#### ";

    public static final String LINE_BREAK = "\n";
    public static final String LINE_BREAK_DOUBLE = "\n\n";
    public static final String TABLE_ROW_SEPARATOR = "| ";
    public static final String TABLE_ROW_END_SEPARATOR = " |\n";
    public static final String TABLE_ROW_END_2_SEPARATOR = " |\n\n";
    public static final String TABLE_ROW_END_3_SEPARATOR = "\n| ";
    public static final String TABLE_BODY_SEPARATOR = " | ";
}
