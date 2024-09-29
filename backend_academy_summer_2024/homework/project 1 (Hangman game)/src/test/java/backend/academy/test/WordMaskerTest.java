package backend.academy.test;

import backend.academy.model.WordMasker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class WordMaskerTest {
    private WordMasker wordMasker;

    @BeforeEach
    void setUp() {
        wordMasker = new WordMasker();
    }

    @Test
    void shouldMaskWordWithAsterisks() {
        String word = "программа";

        // Маскируем слово
        String maskedWord = wordMasker.maskWord(word);

        // Проверяем, что слово замаскировано корректно (все буквы заменены на "*")
        assertThat(maskedWord).isEqualTo("*********");
    }

    @Test
    void shouldPreserveSpacesWhileMasking() {
        String word = "слово с пробелами";

        // Маскируем слово с пробелами
        String maskedWord = wordMasker.maskWord(word);

        // Проверяем, что пробелы сохранены, а остальные буквы замаскированы
        assertThat(maskedWord).isEqualTo("***** * *********");
    }

    @Test
    void shouldPreserveHyphensWhileMasking() {
        String word = "слово-с дефисом";

        // Маскируем слово с дефисом
        String maskedWord = wordMasker.maskWord(word);

        // Проверяем, что дефисы сохранены, а остальные буквы замаскированы
        assertThat(maskedWord).isEqualTo("*****-* *******");
    }

    @Test
    void shouldHandleEmptyString() {
        String word = "";

        // Маскируем пустую строку
        String maskedWord = wordMasker.maskWord(word);

        // Проверяем, что пустая строка возвращается корректно
        assertThat(maskedWord).isEmpty();
    }

    @Test
    void shouldHandleSingleLetterWord() {
        String word = "а";

        // Маскируем слово из одной буквы
        String maskedWord = wordMasker.maskWord(word);

        // Проверяем, что одна буква замаскирована как "*"
        assertThat(maskedWord).isEqualTo("*");
    }
}
