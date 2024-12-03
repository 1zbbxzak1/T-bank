package org.example;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class C {
    private final Scanner scanner = new Scanner(System.in);
    private final PrintStream out = System.out;

    public static void main(String[] args) {
        new C().solve();
    }

    private void solve() {
        // Чтение входных данных
        String input = scanner.nextLine();
        String requiredChars = scanner.nextLine();
        int maxLength = Integer.parseInt(scanner.nextLine());

        // Создание карты с требованиями к символам
        Map<Character, Integer> requiredCounts = buildRequiredCountsMap(requiredChars);

        // Поиск наиболее правой подстроки, соответствующей требованиям
        String result = findRightmostValidSubstring(input, requiredCounts, maxLength);

        out.println(result);
    }

    // Создание карты требований к символам из строки
    private Map<Character, Integer> buildRequiredCountsMap(String requiredChars) {
        Map<Character, Integer> requiredCounts = new HashMap<>();
        for (char c : requiredChars.toCharArray()) {
            requiredCounts.put(c, requiredCounts.getOrDefault(c, 0) + 1);
        }
        return requiredCounts;
    }

    // Поиск наиболее правой подстроки, удовлетворяющей условиям
    private String findRightmostValidSubstring(String input, Map<Character, Integer> requiredCounts, int maxLength) {
        int inputLength = input.length();
        String result = "-1"; // Если подходящего пароля нет, результатом будет "-1"

        // Проверяем подстроки длиной от maxLength до длины requiredChars
        for (int length = Math.min(maxLength, inputLength); length >= requiredCounts.size(); length--) {
            String substring = checkSubstringWithLength(input, requiredCounts, length);
            if (substring != null) {
                result = substring;
                break;
            }
        }
        return result;
    }

    private String checkSubstringWithLength(String input, Map<Character, Integer> requiredCounts, int length) {
        Map<Character, Integer> currentCounts = new HashMap<>();
        int matchedChars = 0;

        // Инициализируем окно с последней подстрокой заданной длины
        for (int i = input.length() - length; i < input.length(); i++) {
            char c = input.charAt(i);
            currentCounts.put(c, currentCounts.getOrDefault(c, 0) + 1);
            if (requiredCounts.containsKey(c) && currentCounts.get(c).equals(requiredCounts.get(c))) {
                matchedChars++;
            }
        }

        // Сдвиг окна для проверки остальных подстрок заданной длины
        for (int start = input.length() - length; start >= 0; start--) {
            if (matchedChars == requiredCounts.size()) {
                return input.substring(start, start + length);
            }

            if (start > 0) {
                // Удаляем символ справа
                updateWindowCount(input, requiredCounts, currentCounts, start + length - 1, -1, matchedChars);
                // Добавляем символ слева
                matchedChars = updateWindowCount(input, requiredCounts, currentCounts, start - 1, 1, matchedChars);
            }
        }
        return null;
    }

    // Обновление счетчика символов в окне, удаляя или добавляя символы.
    private int updateWindowCount(String input, Map<Character, Integer> requiredCounts, Map<Character, Integer> currentCounts, int index, int delta, int matchedChars) {
        char c = input.charAt(index);
        int previousCount = currentCounts.getOrDefault(c, 0);

        if (requiredCounts.containsKey(c) && previousCount == requiredCounts.get(c)) {
            matchedChars--;
        }

        currentCounts.put(c, previousCount + delta);
        int newCount = currentCounts.get(c);

        if (requiredCounts.containsKey(c) && newCount == requiredCounts.get(c)) {
            matchedChars++;
        }

        if (newCount == 0) {
            currentCounts.remove(c);
        }

        return matchedChars;
    }
}
