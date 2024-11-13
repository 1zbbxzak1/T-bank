package backend.academy.view;

import java.io.PrintStream;

public class HangmanStageManager {
    private final String[] hangmanStages;

    public HangmanStageManager(int maxAttempts, PrintStream output) {
        GallowsView view = new GallowsView(output);
        this.hangmanStages = view.createHangmanStages(maxAttempts);
    }

    public String getHangmanStage(int attemptsLeft) {
        if (attemptsLeft < hangmanStages.length) {
            return hangmanStages[hangmanStages.length - attemptsLeft - 1];
        }
        return hangmanStages[0];
    }
}
