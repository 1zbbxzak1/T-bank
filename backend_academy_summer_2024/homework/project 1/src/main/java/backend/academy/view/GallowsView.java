package backend.academy.view;

import backend.academy.constants.ConfigConstants;
import java.io.PrintStream;

public class GallowsView {

    private final PrintStream output;

    public GallowsView(PrintStream output) {
        this.output = output;
    }

    public void displayGameState(String wordState, int attemptsLeft, String hangmanStage) {
        output.println("\nТекущее слово: " + wordState);
        output.println("Осталось попыток: " + attemptsLeft);
        output.println("Состояние виселицы:");
        output.println(hangmanStage + "\n");
    }

    public void displayGameOver(boolean win, String correctWord) {
        if (win) {
            output.println("Поздравляем! Вы угадали слово: " + correctWord);
        } else {
            output.println("Игра окончена! Вы проиграли. Загаданное слово было: " + correctWord);
        }
    }

    // Массив с визуализацией всех стадий виселицы
    public String[] createHangmanStages(int maxAttempts) {
        int mediumMaxAttempts = ConfigConstants.MAX_ATTEMPTS_MEDIUM;
        int hardMaxAttempts = ConfigConstants.MAX_ATTEMPTS_HARD;

        if (maxAttempts == hardMaxAttempts) {
            return new String[] {
                GallowsStage.STAGE_EMPTY,
                GallowsStage.STAGE_4,
                GallowsStage.STAGE_5,
                GallowsStage.STAGE_7,
                GallowsStage.STAGE_9,
                GallowsStage.STAGE_10
            };
        } else if (maxAttempts == mediumMaxAttempts) {
            return new String[] {
                GallowsStage.STAGE_EMPTY,
                GallowsStage.STAGE_4,
                GallowsStage.STAGE_5,
                GallowsStage.STAGE_6,
                GallowsStage.STAGE_7,
                GallowsStage.STAGE_8,
                GallowsStage.STAGE_9,
                GallowsStage.STAGE_10
            };
        } else {
            return new String[] {
                GallowsStage.STAGE_EMPTY,
                GallowsStage.STAGE_1,
                GallowsStage.STAGE_2,
                GallowsStage.STAGE_3,
                GallowsStage.STAGE_4,
                GallowsStage.STAGE_5,
                GallowsStage.STAGE_6,
                GallowsStage.STAGE_7,
                GallowsStage.STAGE_8,
                GallowsStage.STAGE_9,
                GallowsStage.STAGE_10
            };
        }
    }
}
