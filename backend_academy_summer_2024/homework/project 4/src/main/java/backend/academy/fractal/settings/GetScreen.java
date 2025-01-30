package backend.academy.fractal.settings;

import backend.academy.fractal.enums.Screen;
import java.io.PrintStream;
import java.util.Scanner;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GetScreen {
    private final PrintStream out;
    private final Scanner in;

    public Screen getScreen() {
        out.println("Выберите разрешение экрана:");
        for (Screen screen : Screen.values()) {
            out.println(screen.ordinal() + 1 + ". " + screen);
        }

        int choice = -1;
        while (choice < 1 || choice > Screen.values().length) {
            try {
                out.print("Введите номер разрешения: ");
                choice = Integer.parseInt(in.nextLine());
            } catch (NumberFormatException e) {
                out.println("Пожалуйста, введите корректное число.");
            }
        }

        return Screen.values()[choice - 1];
    }
}
