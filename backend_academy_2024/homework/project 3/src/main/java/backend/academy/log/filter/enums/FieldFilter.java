package backend.academy.log.filter.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FieldFilter {
    REMOTE_ADDR("remote_addr"),
    REMOTE_USER("remote_user"),
    HTTP_METHOD("http_method"),
    HTTP_PATH("http_path"),
    HTTP_VERSION("http_version"),
    STATUS("status"),
    BYTES_SENT("bytes_sent"),
    REFERER("referer"),
    AGENT("agent");

    private final String field;

    public static FieldFilter fromString(String field) {
        for (FieldFilter filter : FieldFilter.values()) {
            if (filter.field().equals(field)) {
                return filter;
            }
        }
        throw new IllegalArgumentException("Неизвестный тип фильтра: " + field);
    }
}
