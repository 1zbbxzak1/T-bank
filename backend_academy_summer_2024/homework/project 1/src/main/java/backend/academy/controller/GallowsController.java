package backend.academy.controller;

import backend.academy.constants.ConfigConstants;
import backend.academy.enums.CategoriesWords;
import backend.academy.enums.DifficultyLevels;
import backend.academy.interfaces.IGallowsController;
import backend.academy.interfaces.TranslatableEnum;
import backend.academy.model.GallowsModel;
import backend.academy.view.GallowsView;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Scanner;

public class GallowsController implements IGallowsController {
    private final GallowsView view;
    private final Scanner scanner;
    private final PrintStream output;
    private final SecureRandom secureRand = new SecureRandom();

    public GallowsController(GallowsView view, PrintStream output) {
        this.view = view;
        this.scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        this.output = output;
    }

    @Override
    public CategoriesWords selectCategory() {
        return selectOption(CategoriesWords.values(), "Список категорий:");
    }

    @Override
    public DifficultyLevels selectDifficultyLevel() {
        return selectOption(DifficultyLevels.values(), "Выберите уровень сложности:");
    }

    @Override
    public void startGame(GallowsModel model) {
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

    private <T extends Enum<T> & TranslatableEnum> T selectOption(T[] options, String message) {
        output.println();
        output.println(message);
        String messageOut = ConfigConstants.RANDOM_CHOICE_MESSAGE + " ";

        for (int i = 0; i < options.length; i++) {
            output.println((i + 1) + ". " + options[i].getTranslation());
        }

        T selectedOption = null;
        boolean validInput = false;

        while (!validInput) {
            output.print("Введите номер или нажмите 'Enter' для случайного выбора: ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                selectedOption = options[secureRand.nextInt(options.length)];
                output.println(messageOut + selectedOption.getTranslation());
                validInput = true;
            } else if (isNumeric(input)) {
                int choice = Integer.parseInt(input);

                if (choice >= 1 && choice <= options.length) {
                    selectedOption = options[choice - 1];
                    validInput = true;
                } else {
                    output.println("Ошибка: Введите число от 1 до " + options.length + ".");
                    output.println();
                }
            } else {
                output.println("Ошибка: Некорректный ввод. Пожалуйста, введите число или нажмите 'Enter'.");
                output.println();
            }
        }

        return selectedOption;
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
