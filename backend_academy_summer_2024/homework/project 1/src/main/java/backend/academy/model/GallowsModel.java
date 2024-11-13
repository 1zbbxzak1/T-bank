package backend.academy.model;

import backend.academy.constants.ConfigConstants;
import backend.academy.enums.CategoriesWords;
import backend.academy.enums.DifficultyLevels;
import backend.academy.view.HangmanStageManager;
import java.io.PrintStream;

public class GallowsModel {
    private final PrintStream output;
    private final HangmanStageManager hangmanStageManager;
    private String wordToGuess;
    private StringBuilder currentGuess;
    private int attemptsLeft;

    public GallowsModel(CategoriesWords category, DifficultyLevels difficulty, PrintStream output) {
        if (output == null) {
            throw new IllegalArgumentException("Выходные данные не могут быть пустыми.");
        }
        this.output = output;

        if (category == null) {
            throw new IllegalArgumentException("Категория не может быть пустой.");
        }

        try {
            int maxAttempts = getMaxAttempts(difficulty);
            this.attemptsLeft = maxAttempts;

            WordSelector wordSelector = new WordSelector(category);
            this.wordToGuess = wordSelector.getRandomWord(difficulty);

            if (this.wordToGuess == null || this.wordToGuess.isEmpty()) {
                throw new IllegalArgumentException("Word cannot be null or empty");
            }

            WordMasker wordMasker = new WordMasker();
            this.currentGuess = new StringBuilder(wordMasker.maskWord(wordToGuess));

            hangmanStageManager = new HangmanStageManager(maxAttempts, output);
        } catch (Exception e) {
            throw new RuntimeException("Не удалось выполнить инициализацию: " + e.getMessage(), e);
        }
    }

    public boolean checkLetter(String input) {
        if (input == null || input.length() != 1) {
            output.println("Введите только одну букву.");
            return false;
        }

        char letter = input.charAt(0);
        char lowerCaseLetter = Character.toLowerCase(letter);
        boolean found = false;

        // Проверка, является ли введённый символ кириллицей
        if (!isCyrillic(lowerCaseLetter)) {
            output.println("Введите букву на кириллице.");
            return false;
        }

        // Проверка буквы в загаданном слове
        for (int i = 0; i < wordToGuess.length(); i++) {
            if (wordToGuess.charAt(i) != ' ' && Character.toLowerCase(wordToGuess.charAt(i)) == lowerCaseLetter) {
                currentGuess.setCharAt(i, wordToGuess.charAt(i));
                found = true;
            }
        }
        if (!found) {
            attemptsLeft--;
        }
        return found;
    }

    public boolean isGameOver() {
        return attemptsLeft == 0 || currentGuess.toString().equals(wordToGuess);
    }

    public boolean isWin() {
        return currentGuess.toString().equals(wordToGuess);
    }

    public String getCurrentGuess() {
        return currentGuess.toString();
    }

    public String getWordToGuess() {
        return wordToGuess;
    }

    public void updateWordToGuess(String newWord) {
        if (newWord == null || newWord.isEmpty()) {
            throw new IllegalArgumentException("New word cannot be null or empty");
        }
        this.wordToGuess = newWord;
        this.currentGuess = new StringBuilder("*".repeat(newWord.length()));
    }

    public int getAttemptsLeft() {
        return attemptsLeft;
    }

    public String getHangmanStage() {
        return hangmanStageManager.getHangmanStage(attemptsLeft);
    }

    private int getMaxAttempts(DifficultyLevels difficulty) {
        return switch (difficulty) {
            case EASY -> ConfigConstants.MAX_ATTEMPTS_EASY;
            case MEDIUM -> ConfigConstants.MAX_ATTEMPTS_MEDIUM;
            case HARD -> ConfigConstants.MAX_ATTEMPTS_HARD;
        };
    }

    private boolean isCyrillic(char letter) {
        return String.valueOf(letter).matches("[а-яА-Я]");
    }
}
