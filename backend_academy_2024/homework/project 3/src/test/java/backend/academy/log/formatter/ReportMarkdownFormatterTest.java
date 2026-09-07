package backend.academy.log.formatter;

import backend.academy.log.analyzer.LogAnalyzer;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReportMarkdownFormatterTest {
    private AbstractReportFormatter formatter;

    @Mock
    private LogAnalyzer logAnalyzer;

    @BeforeEach
    public void setUp() {
        formatter = new ReportMarkdownFormatter();
    }

    @Test
    public void testFormatMarkdown() {
        when(logAnalyzer.startDate()).thenReturn("01.01.2020");
        when(logAnalyzer.endDate()).thenReturn("01.01.2020");
        when(logAnalyzer.countRequests()).thenReturn(1L);
        when(logAnalyzer.averageResponseSize()).thenReturn(1234.0);
        when(logAnalyzer.averageRequestsPerIP()).thenReturn(1.0);
        when(logAnalyzer.percentile95ResponseSize()).thenReturn(1234L);
        when(logAnalyzer.mostRequestedResources()).thenReturn(Map.of("GET /resource HTTP/1.1", 1L));
        when(logAnalyzer.responseCodes()).thenReturn(Map.of(200, 1L));
        when(logAnalyzer.requestsPerDay()).thenReturn(Map.of("01.01.2020", 1L));

        String expectedReport = """
            #### Общая информация

            |Метрика|Значение|
            |:-----:|:------:|
            |Начальная дата|01.01.2020|
            |Конечная дата|01.01.2020|
            |Количество запросов|1|
            |Средний размер ответа|1234.0b|
            |Среднее количество запросов от уникального IP|1.0|
            |95p размера ответа|1234b|

            #### Запрашиваемые ресурсы

            |Ресурс|Количество|
            |:----:|:--------:|
            |GET /resource HTTP/1.1|1|

            #### Коды ответа

            |Код|Имя|Количество|
            |:-:|:-:|:--------:|
            |200|OK|1|

            #### Распределение запросов по дням

            |День|Количество|
            |:--:|:--------:|
            |01.01.2020|1|

            """;

        String actualReport = formatter.format(logAnalyzer);
        assertEquals(expectedReport, actualReport);
    }
}
