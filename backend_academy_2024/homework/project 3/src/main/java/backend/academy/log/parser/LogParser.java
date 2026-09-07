package backend.academy.log.parser;

import backend.academy.log.constants.LogConstants;
import backend.academy.log.parser.interfaces.ILogParser;
import backend.academy.log.records.LogRecord;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LogParser implements ILogParser {
    private static final DateTimeFormatter DATE_TIME_FORMATTER =
        DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH);

    @Override
    public LogRecord parse(String line) {
        try {
            String[] parts = line.split(LogConstants.SPACE_SEPARATOR);

            String dateTimeString =
                parts[LogConstants.THREE_NUMBER].substring(1) + LogConstants.SPACE_SEPARATOR
                    + parts[LogConstants.FOUR_NUMBER].substring(LogConstants.ZERO_NUMBER,
                    parts[LogConstants.FOUR_NUMBER].length() - LogConstants.ONE_NUMBER);

            ZonedDateTime time = ZonedDateTime.parse(dateTimeString, DATE_TIME_FORMATTER);

            String remoteAddr = parts[LogConstants.ZERO_NUMBER];
            String remoteUser = "-".equals(parts[LogConstants.TWO_NUMBER]) ? null : parts[LogConstants.TWO_NUMBER];

            String request =
                parts[LogConstants.FIVE_NUMBER].substring(1) + LogConstants.SPACE_SEPARATOR
                    + parts[LogConstants.SIX_NUMBER] + LogConstants.SPACE_SEPARATOR
                    + parts[LogConstants.SEVEN_NUMBER].substring(0,
                    parts[LogConstants.SEVEN_NUMBER].length() - LogConstants.ONE_NUMBER);

            int status = Integer.parseInt(parts[LogConstants.EIGHT_NUMBER]);

            long bodyBytesSent = Long.parseLong(parts[LogConstants.NINE_NUMBER]);

            String referer = "-".equals(parts[LogConstants.TEN_NUMBER]) ? null : parts[LogConstants.TEN_NUMBER];

            String userAgent = null;
            if (parts.length > LogConstants.ELEVEN_NUMBER && !"-".equals(parts[LogConstants.ELEVEN_NUMBER])) {
                StringBuilder userAgentBuilder = new StringBuilder();
                for (int i = LogConstants.ELEVEN_NUMBER; i < parts.length; i++) {
                    userAgentBuilder.append(parts[i]).append(" ");
                }
                String rawUserAgent = userAgentBuilder.toString().trim();
                userAgent = rawUserAgent.startsWith("\"") && rawUserAgent.endsWith("\"")
                    ? rawUserAgent.substring(1, rawUserAgent.length() - 1)
                    : rawUserAgent;
            }

            return new LogRecord(
                remoteAddr,
                remoteUser,
                time.toLocalDate(),
                request,
                status,
                bodyBytesSent,
                referer,
                userAgent
            );
        } catch (Exception e) {
            throw e;
        }
    }
}
