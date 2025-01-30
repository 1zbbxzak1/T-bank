package backend.academy.log.filter.enums;

public enum MatchType {
    STARTS_WITH,
    CONTAINS,
    ENDS_WITH;

    public static boolean matchRequestField(String request, String value, MatchType matchType) {
        if (request == null) {
            return false;
        }
        return switch (matchType) {
            case STARTS_WITH -> request.startsWith(value);
            case CONTAINS -> request.contains(value);
            case ENDS_WITH -> request.endsWith(value);
        };
    }
}
