package backend.academy.controller;

import backend.academy.enums.CategoriesWords;
import backend.academy.enums.DifficultyLevels;
import backend.academy.interfaces.TranslatableEnum;
import backend.academy.model.GallowsModel;
import backend.academy.properties.ConfigLoader;
import backend.academy.view.GallowsView;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Scanner;

public class GallowsController {
    private final GallowsView view;
    private final Scanner scanner;
    private final GallowsModel model;
    private final PrintStream output;
    private final SecureRandom secureRand = new SecureRandom();

    public GallowsController(GallowsModel model, GallowsView view, PrintStream output) {
        this.model = model;
        this.view = view;
        this.scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        this.output = output;
    }

    public CategoriesWords selectCategory() {
        return selectOption(CategoriesWords.values(), "\nСписок категорий:");
    }

    public DifficultyLevels selectDifficultyLevel() {
        return selectOption(DifficultyLevels.values(), "\nВыберите уровень сложности:");
    }

    private <T extends Enum<T> & TranslatableEnum> T selectOption(T[] options, String message) {
        output.println(message);
        String messageOut = ConfigLoader.getProperty("RANDOM_CHOICE_MESSAGE") + " ";

        for (int i = 0; i < options.length; i++) {
            output.println((i + 1) + ". " + options[i].getTranslation());
        }

        T selectedOption;

        output.print("Введите номер или нажмите Enter для случайного выбора: ");
        String input = scanner.nextLine().trim();

        if (input.isEmpty() || !isNumeric(input)) {
            selectedOption = options[secureRand.nextInt(options.length)];
            output.println(messageOut + selectedOption.getTranslation());
        } else {
            int choice = Integer.parseInt(input);

            if (choice < 1 || choice > options.length) {
                selectedOption = options[secureRand.nextInt(options.length)];
                output.println(messageOut + selectedOption.getTranslation());
            } else {
                selectedOption = options[choice - 1];
            }
        }

        return selectedOption;
    }

    public void playGame() {
        while (!model.isGameOver()) {
            view.displayGameState(model.getCurrentGuess(), model.getAttemptsLeft(), model.getHangmanStage());

            output.println("Введите букву: ");
            String guess = String.valueOf(scanner.next().toLowerCase().charAt(0));

            if (guess.length() != 1) {
                output.println("Введите только одну букву!");
                continue;
            }

            if (!model.checkLetter(guess)) {
                output.println("Неправильная буква!");
            }
        }

        view.displayGameState(model.getCurrentGuess(), model.getAttemptsLeft(), model.getHangmanStage());
        view.displayGameOver(model.isWin(), model.getWordToGuess());

    }

    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
