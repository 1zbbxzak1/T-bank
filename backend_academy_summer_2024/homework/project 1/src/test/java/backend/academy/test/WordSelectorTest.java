package backend.academy.test;

import backend.academy.constants.ConfigConstants;
import backend.academy.enums.CategoriesWords;
import backend.academy.enums.DifficultyLevels;
import backend.academy.model.WordSelector;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class WordSelectorTest {
    private WordSelector wordSelector;

    @Test
    void shouldSelectWordFromCategory() {
        wordSelector = new WordSelector(CategoriesWords.ANIMAL);

        // Выбираем случайное слово
        String randomWord = wordSelector.getRandomWord(DifficultyLevels.EASY);

        // Проверяем, что выбранное слово не пустое и содержится в списке слов категории "Животные"
        assertThat(randomWord).isNotEmpty();
        assertThat(CategoriesWords.ANIMAL.getWords()).contains(randomWord);
    }

    @Test
    void shouldSelectDifferentWordsOnMultipleAttempts() {
        wordSelector = new WordSelector(CategoriesWords.ANIMAL);

        // Проверяем, что на нескольких вызовах метода могут выбираться разные слова
        Set<String> selectedWords = new HashSet<>();

        for (int i = 0; i < 10; i++) {
            selectedWords.add(wordSelector.getRandomWord(DifficultyLevels.EASY));
        }

        // Убедимся, что среди выбранных слов есть несколько уникальных
        assertThat(selectedWords.size()).isGreaterThan(1);
    }

    @Test
    void shouldThrowExceptionForNullCategory() {
        // Проверяем, что при указании пустой категории генерируется исключение
        assertThatThrownBy(() -> new WordSelector(null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Категория не может быть пустой.");
    }

    @Test
    void shouldSelectWordOfCorrectLengthForEasyDifficulty() {
        wordSelector = new WordSelector(CategoriesWords.ANIMAL);

        String randomWord = wordSelector.getRandomWord(DifficultyLevels.EASY);

        // Предположим, что ограничения длины правильно заданы в свойствах
        int minLength = ConfigConstants.MIN_WORD_LENGTH_EASY;
        int maxLength = ConfigConstants.MAX_WORD_LENGTH_EASY;

        assertThat(randomWord.length()).isGreaterThanOrEqualTo(minLength);
        assertThat(randomWord.length()).isLessThanOrEqualTo(maxLength);
    }

    @Test
    void shouldSelectWordOfCorrectLengthForMediumDifficulty() {
        wordSelector = new WordSelector(CategoriesWords.ANIMAL);

        String randomWord = wordSelector.getRandomWord(DifficultyLevels.MEDIUM);

        int minLength = ConfigConstants.MIN_WORD_LENGTH_MEDIUM;
        int maxLength = ConfigConstants.MAX_WORD_LENGTH_MEDIUM;

        assertThat(randomWord.length()).isGreaterThanOrEqualTo(minLength);
        assertThat(randomWord.length()).isLessThanOrEqualTo(maxLength);
    }

    @Test
    void shouldSelectWordOfCorrectLengthForHardDifficulty() {
        wordSelector = new WordSelector(CategoriesWords.ANIMAL);

        String randomWord = wordSelector.getRandomWord(DifficultyLevels.HARD);

        int minLength = ConfigConstants.MIN_WORD_LENGTH_HARD;

        assertThat(randomWord.length()).isGreaterThanOrEqualTo(minLength);
    }
}
