package backend.academy.labyrinths.settings;

import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class MazeDimensionInput {
    private final Scanner scanner;
    private final PrintStream output;

    public MazeDimensionInput(PrintStream output, InputStream input) {
        this.scanner = new Scanner(input, StandardCharsets.UTF_8);
        this.output = output;
    }

    // Метод для получения высоты и ширины лабиринта
    public int getMazeDimension(String prompt) {
        output.println(prompt);
        while (true) {
            String input = scanner.nextLine(); // Читаем строку целиком
            String[] parts = input.split("\\s+"); // Разделяем по пробелам

            // Проверка, что введено ровно одно число
            if (parts.length != 1) {
                output.println("Пожалуйста, введите ровно одно целое число.");
                continue; // Возвращаемся в начало цикла
            }

            // Попытка преобразовать введённое значение в целое число
            try {
                int dimension = Integer.parseInt(parts[0]);
                if (dimension <= 0) {
                    output.println("Пожалуйста, введите положительное целое число.");
                    continue; // Запросите ввод снова, если число не положительное
                }
                return dimension; // Верните корректное значение
            } catch (NumberFormatException e) {
                output.println("Пожалуйста, введите корректное целое число.");
            }
        }
    }
}
