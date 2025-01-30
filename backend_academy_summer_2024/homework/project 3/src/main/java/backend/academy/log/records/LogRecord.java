package backend.academy.log.records;

import java.time.LocalDate;

public record LogRecord(
    String remoteAddr,
    String remoteUser,
    LocalDate time,
    String request,
    int status,
    long bodyBytesSent,
    String referer,
    String userAgent
) {
}
