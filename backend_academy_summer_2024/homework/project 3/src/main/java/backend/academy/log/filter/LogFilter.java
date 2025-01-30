package backend.academy.log.filter;

import backend.academy.log.filter.enums.FieldFilter;
import backend.academy.log.filter.enums.MatchType;
import backend.academy.log.filter.interfaces.ILogFilter;
import backend.academy.log.records.LogRecord;
import java.time.LocalDate;
import java.util.function.Predicate;

public class LogFilter implements ILogFilter {
    @Override
    public Predicate<LogRecord> filterByDate(LocalDate from, LocalDate to) {
        return logRecord -> isDateWithinRange(logRecord.time(), from, to);
    }

    @Override
    public Predicate<LogRecord> filterByField(String field, String value) {
        FieldFilter filter = getFieldFilter(field);

        return logRecord -> filterLogRecord(filter, logRecord, value);
    }

    private boolean isDateWithinRange(LocalDate time, LocalDate from, LocalDate to) {
        return (from == null || !time.isBefore(from)) && (to == null || !time.isAfter(to));
    }

    private FieldFilter getFieldFilter(String field) {
        try {
            return FieldFilter.fromString(field);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private boolean filterLogRecord(FieldFilter filter, LogRecord logRecord, String value) {
        if (filter == null) {
            return true;
        }

        return switch (filter) {
            case REMOTE_ADDR -> logRecord.remoteAddr().equals(value);
            case REMOTE_USER -> matchNullableField(logRecord.remoteUser(), value);
            case HTTP_METHOD -> MatchType.matchRequestField(logRecord.request(), value, MatchType.STARTS_WITH);
            case HTTP_PATH -> MatchType.matchRequestField(logRecord.request(), value, MatchType.CONTAINS);
            case HTTP_VERSION -> MatchType.matchRequestField(logRecord.request(), value, MatchType.ENDS_WITH);
            case STATUS -> String.valueOf(logRecord.status()).equals(value);
            case BYTES_SENT -> String.valueOf(logRecord.bodyBytesSent()).equals(value);
            case REFERER -> matchNullableField(logRecord.referer(), value);
            case AGENT -> logRecord.userAgent() != null && logRecord.userAgent().contains(value);
            default -> true;
        };
    }

    private boolean matchNullableField(String fieldValue, String targetValue) {
        return fieldValue == null ? targetValue.equals("-") : fieldValue.equals(targetValue);
    }
}
