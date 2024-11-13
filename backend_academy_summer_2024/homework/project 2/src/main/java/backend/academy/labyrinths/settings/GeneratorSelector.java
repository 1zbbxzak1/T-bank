package backend.academy.labyrinths.settings;

import backend.academy.labyrinths.generators.KruskalMazeGenerator;
import backend.academy.labyrinths.generators.PrimMazeGenerator;
import backend.academy.labyrinths.interfaces.Generator;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class GeneratorSelector {
    private final Scanner scanner;
    private final PrintStream output;

    public GeneratorSelector(PrintStream output, InputStream input) {
        this.scanner = new Scanner(input, StandardCharsets.UTF_8);
        this.output = output;
    }

    // Метод для выбора алгоритма генерации лабиринта
    public Generator selectMazeGenerator() {
        output.println("Выберите алгоритм генерации лабиринта:");
        output.println("1. Алгоритм Прима");
        output.println("2. Алгоритм Краскала");

        int choice = -1;
        while (choice < 1 || choice > 2) {
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
            } else {
                output.println("Введите число 1 или 2.");
                scanner.next(); // пропустить неверный ввод
            }

            switch (choice) {
                case 1 -> {
                    return new PrimMazeGenerator();
                }
                case 2 -> {
                    return new KruskalMazeGenerator();
                }
                default -> output.println("Пожалуйста, выберите 1 или 2.");
            }
        }
        return null;
    }
}
