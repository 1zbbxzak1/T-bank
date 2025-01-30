package backend.academy.fractal.settings;

import java.io.PrintStream;
import java.util.Scanner;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GetIterations {
    private static final int MAX_ITERATIONS = 100;
    private final PrintStream out;
    private final Scanner in;

    public int getIterations() {
        int iterations = -1;
        while (iterations <= 0) {
            try {
                out.print("Введите количество итераций от 10000: ");
                iterations = Integer.parseInt(in.nextLine());
                if (iterations <= MAX_ITERATIONS) {
                    out.println("Число должно быть больше 100.");
                }
            } catch (NumberFormatException e) {
                out.println("Пожалуйста, введите корректное число.");
            }
        }
        return iterations;
    }
}
