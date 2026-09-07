package backend.academy.log.analyzer.interfaces;

import java.util.Map;

public interface ILogAnalyzer {
    long countRequests();

    String startDate();

    String endDate();

    Map<String, Long> mostRequestedResources();

    Map<Integer, Long> responseCodes();

    double averageResponseSize();

    long percentile95ResponseSize();

    Map<String, Long> requestsPerDay();

    double averageRequestsPerIP();
}
