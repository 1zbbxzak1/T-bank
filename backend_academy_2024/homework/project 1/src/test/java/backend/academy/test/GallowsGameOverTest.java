package backend.academy.test;

import backend.academy.enums.CategoriesWords;
import backend.academy.enums.DifficultyLevels;
import backend.academy.model.GallowsModel;
import backend.academy.view.GallowsView;
import java.io.PrintStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

public class GallowsGameOverTest {

    private PrintStream output;
    private GallowsModel model;
    private GallowsView view;

    @BeforeEach
    public void setUp() {
        output = Mockito.mock(PrintStream.class);

        // Выберите категорию и уровень сложности для теста
        model = new GallowsModel(CategoriesWords.FOOD, DifficultyLevels.EASY, output);
        view = new GallowsView(output);
    }

    @Test
    public void testGameOverAfterMaxAttempts() {
        // Проверяем, что количество оставшихся попыток изначально соответствует максимальному количеству
        assertEquals(10, model.getAttemptsLeft());

        // Пытаемся угадать неправильные буквы 10 раз
        for (int i = 0; i < 10; i++) {
            model.checkLetter("ъ"); // Пытаемся угадать букву, которая, вероятно, не в слове
        }

        // Проверяем, что игра окончена и игрок проиграл
        assertTrue(model.isGameOver());
        assertFalse(model.isWin());
    }

    @Test
    public void testCorrectGameOverMessage() {
        // Пытаемся угадать неправильные буквы 10 раз
        for (int i = 0; i < 10; i++) {
            model.checkLetter("ъ"); // Пытаемся угадать букву, которая, вероятно, не в слове
        }

        // Проверяем, что выводится правильное сообщение о конце игры
        model.isGameOver();

        model.getHangmanStage(); // Обновим состояние виселицы
        String correctWord = model.getWordToGuess();
        view.displayGameOver(model.isWin(), correctWord);

        verify(output, atLeastOnce()).println(
            contains("Игра окончена! Вы проиграли. Загаданное слово было: " + model.getWordToGuess()));
    }
}
