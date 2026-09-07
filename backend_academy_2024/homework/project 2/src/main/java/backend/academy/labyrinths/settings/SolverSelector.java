package backend.academy.labyrinths.settings;

import backend.academy.labyrinths.interfaces.Solver;
import backend.academy.labyrinths.solvers.AStarSolver;
import backend.academy.labyrinths.solvers.BFSSolver;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class SolverSelector {
    private final Scanner scanner;
    private final PrintStream output;

    public SolverSelector(PrintStream output, InputStream input) {
        this.scanner = new Scanner(input, StandardCharsets.UTF_8);
        this.output = output;
    }

    // Метод для выбора алгоритма поиска пути
    public Solver selectPathSolver() {
        output.println("Выберите алгоритм поиска пути:");
        output.println("1. A* (A-star)");
        output.println("2. Поиск в ширину (BFS)");

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
                    return new AStarSolver();
                }
                case 2 -> {
                    return new BFSSolver();
                }
                default -> output.println("Пожалуйста, выберите 1 или 2.");
            }
        }
        return null;
    }
}
