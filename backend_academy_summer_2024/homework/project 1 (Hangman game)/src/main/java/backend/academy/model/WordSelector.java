package backend.academy.model;

import backend.academy.enums.CategoriesWords;
import backend.academy.enums.DifficultyLevels;
import backend.academy.properties.ConfigLoader;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class WordSelector {
    private final SecureRandom secureRand = new SecureRandom();
    private final String[] words;

    public WordSelector(CategoriesWords category) {
        if (category == null) {
            throw new IllegalArgumentException("Категория не может быть пустой.");
        }

        String[] tempWords = category.getWords();
        if (tempWords == null || tempWords.length == 0) {
            throw new IllegalArgumentException("В выбранной категории нет доступных слов.");
        }

        this.words = tempWords.clone();
    }

    public String getRandomWord(DifficultyLevels difficulty) {
        int minLength;
        int maxLength;
        switch (difficulty) {
            case easy -> {
                minLength = Integer.parseInt(
                    ConfigLoader.getProperty("MIN_WORD_LENGTH_EASY")); // Легкий уровень: слова короткие
                maxLength = Integer.parseInt(ConfigLoader.getProperty("MAX_WORD_LENGTH_EASY"));
            }
            case medium -> {
                minLength = Integer.parseInt(
                    ConfigLoader.getProperty("MIN_WORD_LENGTH_MEDIUM")); // Средний уровень: слова средней длины
                maxLength = Integer.parseInt(ConfigLoader.getProperty("MAX_WORD_LENGTH_MEDIUM"));
            }
            case hard -> {
                minLength = Integer.parseInt(
                    ConfigLoader.getProperty("MIN_WORD_LENGTH_HARD")); // Сложный уровень: слова длинные
                maxLength = Integer.MAX_VALUE;
            }
            default -> throw new IllegalArgumentException("Неизвестный уровень сложности: " + difficulty);
        }

        List<String> filteredWords = new ArrayList<>();
        for (String word : words) {
            if (word.length() >= minLength && word.length() <= maxLength) {
                filteredWords.add(word);
            }
        }

        if (filteredWords.isEmpty()) {
            throw new IllegalArgumentException("Нет доступных слов для указанного уровня сложности.");
        }

        return filteredWords.get(secureRand.nextInt(filteredWords.size()));
    }
}
