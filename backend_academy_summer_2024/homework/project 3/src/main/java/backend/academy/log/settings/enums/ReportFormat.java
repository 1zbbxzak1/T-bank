package backend.academy.log.settings.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReportFormat {
    MARKDOWN("markdown"),
    ADOC("adoc");

    private final String format;

    public static ReportFormat fromString(String format) {
        for (ReportFormat reportFormat : values()) {
            if (reportFormat.format.equalsIgnoreCase(format)) {
                return reportFormat;
            }
        }
        return null;
    }
}
