package backend.academy.log.formatter;

import backend.academy.log.analyzer.LogAnalyzer;
import backend.academy.log.formatter.constants.FormatterConstants;
import backend.academy.log.formatter.enums.HttpStatusCode;

public abstract class AbstractReportFormatter {
    protected abstract String getTitlePrefix();

    protected abstract String getLineBreak();

    protected abstract String getTableHeader(String... columns);

    protected abstract String getTableRow(String... values);

    protected abstract String getTableEnd();

    public String format(LogAnalyzer analyzer) {
        StringBuilder sb = new StringBuilder();

        // General Information
        sb.append(getTitlePrefix()).append(FormatterConstants.TITLE_GENERAL_INFORMATION)
            .append(getLineBreak())
            .append(getTableHeader(FormatterConstants.COL_GI_METRIC, FormatterConstants.COL_GI_MEANING))
            .append(getTableRow(FormatterConstants.PARAM_GI_STARTING_DATE, analyzer.startDate()))
            .append(getTableRow(FormatterConstants.PARAM_GI_END_DATE, analyzer.endDate()))
            .append(getTableRow(FormatterConstants.PARAM_GI_NUMBER_REQUESTS, String.valueOf(analyzer.countRequests())))
            .append(getTableRow(FormatterConstants.PARAM_GI_NUMBER_AVG_RESPONSE_SIZE,
                analyzer.averageResponseSize() + FormatterConstants.UNIT_MEASUREMENT))
            .append(getTableRow(FormatterConstants.PARAM_GI_NUMBER_AVG_RESPONSE_IP,
                String.valueOf(analyzer.averageRequestsPerIP())))
            .append(getTableRow(FormatterConstants.PARAM_GI_95_PERCENTILE,
                analyzer.percentile95ResponseSize() + FormatterConstants.UNIT_MEASUREMENT))
            .append(getTableEnd());

        // Requested Resources
        sb.append(getTitlePrefix()).append(FormatterConstants.TITLE_REQUESTED_RESOURCES)
            .append(getLineBreak())
            .append(getTableHeader(FormatterConstants.COL_RR_RESOURCE, FormatterConstants.COL_QUANTITY));

        analyzer.mostRequestedResources().forEach((res, count) ->
            sb.append(getTableRow(res, String.valueOf(count))));

        sb.append(getTableEnd());

        // Response Codes
        sb.append(getTitlePrefix()).append(FormatterConstants.TITLE_RESPONSE_CODES)
            .append(getLineBreak())
            .append(getTableHeader(FormatterConstants.COL_RC_CODE, FormatterConstants.COL_RC_NAME,
                FormatterConstants.COL_QUANTITY));

        analyzer.responseCodes().forEach((code, count) -> {
            String description;
            try {
                description = HttpStatusCode.fromCode(code).description();
            } catch (IllegalArgumentException e) {
                description = "Unknown";
            }
            sb.append(getTableRow(String.valueOf(code), description, String.valueOf(count)));
        });

        sb.append(getTableEnd());

        // Requests Per Day
        sb.append(getTitlePrefix()).append(FormatterConstants.TITLE_RESPONSE_DAYS)
            .append(getLineBreak())
            .append(getTableHeader(FormatterConstants.COL_RD_DAY, FormatterConstants.COL_QUANTITY));

        analyzer.requestsPerDay().forEach((day, count) ->
            sb.append(getTableRow(day, String.valueOf(count))));

        sb.append(getTableEnd());

        return sb.toString();
    }
}

