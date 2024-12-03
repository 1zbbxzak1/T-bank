package org.example;

import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.*;

public class E {
    private final Scanner scanner = new Scanner(System.in);
    private final PrintStream out = System.out;

    public static void main(String[] args) throws Exception {
        new E().solve();
    }

    private void solve() throws Exception {
        Date startTime = parseStartTime(scanner.nextLine());
        int totalRequests = Integer.parseInt(scanner.nextLine());

        Map<String, TeamPerformance> teamPerformances = new HashMap<>();
        Map<String, Map<Character, Integer>> teamServerFails = new HashMap<>();

        for (int i = 0; i < totalRequests; i++) {
            handleRequest(scanner.nextLine(), startTime, teamPerformances, teamServerFails);
        }

        List<TeamPerformance> performanceList = new ArrayList<>(teamPerformances.values());
        sortPerformanceList(performanceList);

        displayRankedResults(performanceList);
    }

    private Date parseStartTime(String startTimeStr) throws Exception {
        return new SimpleDateFormat("HH:mm:ss").parse(startTimeStr);
    }

    private void handleRequest(String requestData, Date startTime, Map<String, TeamPerformance> teamPerformances, Map<String, Map<Character, Integer>> teamServerFails) throws Exception {
        String[] requestParts = requestData.split(" ");
        String teamName = requestParts[0].replace("\"", "");
        Date requestTime = new SimpleDateFormat("HH:mm:ss").parse(requestParts[1]);
        char serverId = requestParts[2].charAt(0);
        String requestResult = requestParts[3];

        teamPerformances.putIfAbsent(teamName, new TeamPerformance(teamName));
        teamServerFails.putIfAbsent(teamName, new HashMap<>());

        int minutesFromStart = calculateMinutesFromStart(startTime, requestTime);
        processRequestResult(requestResult, teamName, serverId, minutesFromStart, teamPerformances, teamServerFails);
    }

    private int calculateMinutesFromStart(Date startTime, Date requestTime) {
        int minutes = (int) ((requestTime.getTime() - startTime.getTime()) / (1000 * 60));
        if (minutes < 0) {
            minutes += 1440; // В случае отрицательного значения одни сутки
        }
        return minutes;
    }

    private void processRequestResult(String result, String teamName, char serverId, int minutesFromStart, Map<String, TeamPerformance> teamPerformances, Map<String, Map<Character, Integer>> teamServerFails) {
        Map<Character, Integer> serverFails = teamServerFails.get(teamName);

        if (result.equals("ACCESSED")) {
            int penalty = serverFails.getOrDefault(serverId, 0) * 20 + minutesFromStart;
            teamPerformances.get(teamName).addAccess(penalty);
            serverFails.put(serverId, 0); // Сбрасываем неудачные попытки
        } else if (result.equals("DENIED") || result.equals("FORBIDEN")) {
            serverFails.put(serverId, serverFails.getOrDefault(serverId, 0) + 1);
        }
        // Игнорируем PONG запросы
    }

    private void sortPerformanceList(List<TeamPerformance> performances) {
        performances.sort(Comparator.naturalOrder());
    }

    private void displayRankedResults(List<TeamPerformance> performances) {
        int rank = 1;

        for (int i = 0; i < performances.size(); i++) {
            TeamPerformance currentPerformance = performances.get(i);
            if (i > 0 && currentPerformance.compareToCompetition(performances.get(i - 1)) != 0) {
                rank = i + 1;
            }
            out.printf("%d \"%s\" %d %d\n", rank, currentPerformance.name, currentPerformance.serversAccessed, currentPerformance.penaltyTime);
        }
    }

    static class TeamPerformance implements Comparable<TeamPerformance> {
        String name;
        int serversAccessed;
        int penaltyTime;

        public TeamPerformance(String name) {
            this.name = name;
            this.serversAccessed = 0;
            this.penaltyTime = 0;
        }

        public void addAccess(int time) {
            serversAccessed++;
            penaltyTime += time;
        }

        @Override
        public int compareTo(TeamPerformance other) {
            if (this.serversAccessed != other.serversAccessed) {
                return Integer.compare(other.serversAccessed, this.serversAccessed);
            }
            if (this.penaltyTime != other.penaltyTime) {
                return Integer.compare(this.penaltyTime, other.penaltyTime);
            }
            return this.name.compareTo(other.name);
        }

        public int compareToCompetition(TeamPerformance other) {
            if (this.serversAccessed != other.serversAccessed) {
                return Integer.compare(other.serversAccessed, this.serversAccessed);
            }
            if (this.penaltyTime != other.penaltyTime) {
                return Integer.compare(this.penaltyTime, other.penaltyTime);
            }

            return 0;
        }
    }
}
