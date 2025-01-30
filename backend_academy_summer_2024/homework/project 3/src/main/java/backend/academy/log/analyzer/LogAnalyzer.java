package backend.academy.log.analyzer;

import backend.academy.log.analyzer.interfaces.ILogAnalyzer;
import backend.academy.log.constants.LogConstants;
import backend.academy.log.records.LogRecord;
import backend.academy.log.settings.LogsReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LogAnalyzer implements ILogAnalyzer {
    private static final double PERCENTILE_VALUE = 0.95;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private final String logFilePath;

    private LocalDate fromDate;
    private LocalDate toDate;
    private String filterField;
    private String filterValue;

    public LogAnalyzer withDateFilter(LocalDate from, LocalDate to) {
        this.fromDate = from;
        this.toDate = to;
        return this;
    }

    public LogAnalyzer withFieldFilter(String field, String value) {
        this.filterField = field;
        this.filterValue = value;
        return this;
    }

    @Override
    public long countRequests() {
        try (Stream<LogRecord> logStream = getLogStream()) {
            return logStream.count();
        } catch (IOException e) {
            throw new RuntimeException(LogConstants.ERROR_LOG_READER + e.getMessage(), e);
        }
    }

    @Override
    public String startDate() {
        try (Stream<LogRecord> logStream = getLogStream()) {
            return logStream
                .min(Comparator.comparing(LogRecord::time))
                .map(rec -> rec.time().format(FORMATTER))
                .orElse("-");
        } catch (IOException e) {
            throw new RuntimeException(LogConstants.ERROR_LOG_READER + e.getMessage(), e);
        }
    }

    @Override
    public String endDate() {
        try (Stream<LogRecord> logStream = getLogStream()) {
            return logStream
                .max(Comparator.comparing(LogRecord::time))
                .map(rec -> rec.time().format(FORMATTER))
                .orElse("-");
        } catch (IOException e) {
            throw new RuntimeException(LogConstants.ERROR_LOG_READER + e.getMessage(), e);
        }
    }

    @Override
    public Map<String, Long> mostRequestedResources() {
        try (Stream<LogRecord> logStream = getLogStream()) {
            Map<String, Long> resourceCounts = new HashMap<>();
            logStream.forEach(logEntry ->
                resourceCounts.merge(logEntry.request(), 1L, Long::sum)
            );
            return resourceCounts;
        } catch (IOException e) {
            throw new RuntimeException(LogConstants.ERROR_LOG_READER + e.getMessage(), e);
        }
    }

    @Override
    public Map<Integer, Long> responseCodes() {
        try (Stream<LogRecord> logStream = getLogStream()) {
            Map<Integer, Long> responseCodeCounts = new HashMap<>();
            logStream.forEach(logEntry ->
                responseCodeCounts.merge(logEntry.status(), 1L, Long::sum)
            );
            return responseCodeCounts;
        } catch (IOException e) {
            throw new RuntimeException(LogConstants.ERROR_LOG_READER + e.getMessage(), e);
        }
    }

    @Override
    public double averageResponseSize() {
        try (Stream<LogRecord> logStream = getLogStream()) {
            return logStream
                .mapToLong(LogRecord::bodyBytesSent)
                .average()
                .orElse(LogConstants.ZERO_NUMBER);
        } catch (IOException e) {
            throw new RuntimeException(LogConstants.ERROR_LOG_READER + e.getMessage(), e);
        }
    }

    @Override
    public long percentile95ResponseSize() {
        try (Stream<LogRecord> logStream = getLogStream()) {
            long[] sizes = logStream
                .mapToLong(LogRecord::bodyBytesSent)
                .sorted()
                .toArray();

            double percentileIndex = PERCENTILE_VALUE * (sizes.length - LogConstants.ONE_NUMBER);
            int lowerIndex = (int) Math.floor(percentileIndex);
            int upperIndex = (int) Math.ceil(percentileIndex);

            if (lowerIndex == upperIndex) {
                return sizes[lowerIndex];
            } else {
                long lowerValue = sizes[lowerIndex];
                long upperValue = sizes[upperIndex];
                return Math.round((upperValue - lowerValue) * (percentileIndex - lowerIndex) + lowerValue);
            }
        } catch (IOException e) {
            throw new RuntimeException(LogConstants.ERROR_LOG_READER + e.getMessage(), e);
        }
    }

    @Override
    public Map<String, Long> requestsPerDay() {
        try (Stream<LogRecord> logStream = getLogStream()) {
            Map<String, Long> requestsByDay = new HashMap<>();
            logStream.forEach(logEntry -> {
                String date = logEntry.time().format(FORMATTER);
                requestsByDay.merge(date, 1L, Long::sum);
            });
            return requestsByDay;
        } catch (IOException e) {
            throw new RuntimeException(LogConstants.ERROR_LOG_READER + e.getMessage(), e);
        }
    }

    @Override
    public double averageRequestsPerIP() {
        try (Stream<LogRecord> logStream = getLogStream()) {
            Map<String, Long> requestsPerIP = new HashMap<>();
            logStream.forEach(logEntry ->
                requestsPerIP.merge(logEntry.remoteAddr(), 1L, Long::sum)
            );

            return requestsPerIP.values().stream()
                .mapToLong(Long::longValue)
                .average()
                .orElse(LogConstants.ZERO_NUMBER);
        } catch (IOException e) {
            throw new RuntimeException(LogConstants.ERROR_LOG_READER + e.getMessage(), e);
        }
    }

    private Stream<LogRecord> getLogStream() throws IOException {
        return LogsReader.readLogs(logFilePath, fromDate, toDate, filterField, filterValue);
    }
}
