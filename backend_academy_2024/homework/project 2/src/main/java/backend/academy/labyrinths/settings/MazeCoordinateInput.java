package backend.academy.labyrinths.settings;

import backend.academy.labyrinths.records.Coordinate;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class MazeCoordinateInput {
    private final Scanner scanner;
    private final PrintStream output;

    public MazeCoordinateInput(PrintStream output, InputStream input) {
        this.scanner = new Scanner(input, StandardCharsets.UTF_8);
        this.output = output;
    }

    // Метод для получения координат начальной и конечной точки
    public Coordinate getCoordinate(String name, int maxRow, int maxCol) {
        output.printf("Введите %s точку (строка и столбец через пробел): ", name);
        int row = -1;
        int col = -1;

        while (row < 0 || row >= maxRow || col < 0 || col >= maxCol) {
            String input = scanner.nextLine(); // Читаем строку целиком
            String[] parts = input.split("\\s+"); // Разделяем по пробелам

            // Проверка, что введено ровно 2 числа
            if (parts.length != 2) {
                output.println("Пожалуйста, введите ровно два целых числа.");
                continue; // Возвращаемся в начало цикла
            }

            // Попытка преобразовать введённые значения в целые числа
            try {
                row = Integer.parseInt(parts[0]);
                col = Integer.parseInt(parts[1]);
            } catch (NumberFormatException e) {
                output.println("Пожалуйста, введите корректные целые числа.");
                row = -1; // Сбрасываем значения
                col = -1; // Сбрасываем значения
                continue; // Возвращаемся в начало цикла
            }

            // Проверка границ
            if (row < 0 || row >= maxRow || col < 0 || col >= maxCol) {
                output.printf("Координаты должны быть в пределах [0, %d) x [0, %d). Попробуйте ещё раз: ", maxRow,
                    maxCol);
            }
        }

        return new Coordinate(row, col);
    }
}
