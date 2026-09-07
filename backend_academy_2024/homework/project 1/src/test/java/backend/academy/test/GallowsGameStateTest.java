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

class GallowsGameStateTest {

    private GallowsModel gallowsModel;
    private GallowsView gallowsView;
    private PrintStream output;

    @BeforeEach
    void setUp() {
        // Создание mock-объекта PrintStream для вывода
        output = Mockito.mock(PrintStream.class);

        gallowsView = new GallowsView(output);

        // Создание модели с известным словом
        gallowsModel = new GallowsModel(CategoriesWords.ANIMAL, DifficultyLevels.EASY, output);
        gallowsModel.updateWordToGuess("кот");
    }

    @Test
    void testGameStateAfterCorrectGuess() {
        // Проверяем начальное состояние
        assertEquals("***", gallowsModel.getCurrentGuess());
        assertEquals(10, gallowsModel.getAttemptsLeft());

        gallowsView.displayGameState(gallowsModel.getCurrentGuess(), gallowsModel.getAttemptsLeft(),
            gallowsModel.getHangmanStage());

        // Проверяем ввод правильной буквы
        boolean isFound = gallowsModel.checkLetter("к");
        assertTrue(isFound);
        assertEquals("к**", gallowsModel.getCurrentGuess());
        assertEquals(10, gallowsModel.getAttemptsLeft());

        gallowsView.displayGameState(gallowsModel.getCurrentGuess(), gallowsModel.getAttemptsLeft(),
            gallowsModel.getHangmanStage());

        // Проверяем ввод правильной буквы
        isFound = gallowsModel.checkLetter("о");
        assertTrue(isFound);
        assertEquals("ко*", gallowsModel.getCurrentGuess());
        assertEquals(10, gallowsModel.getAttemptsLeft());

        gallowsView.displayGameState(gallowsModel.getCurrentGuess(), gallowsModel.getAttemptsLeft(),
            gallowsModel.getHangmanStage());

        // Проверяем ввод правильной буквы
        isFound = gallowsModel.checkLetter("т");
        assertTrue(isFound);
        assertEquals("кот", gallowsModel.getCurrentGuess());
        assertEquals(10, gallowsModel.getAttemptsLeft());

        gallowsView.displayGameState(gallowsModel.getCurrentGuess(), gallowsModel.getAttemptsLeft(),
            gallowsModel.getHangmanStage());

        // Проверяем, что игра закончилась
        assertTrue(gallowsModel.isWin());
        assertTrue(gallowsModel.isGameOver());

        verify(output, atLeastOnce()).println(contains("\nТекущее слово: кот"));
        verify(output, atLeastOnce()).println(contains("Осталось попыток: 10"));
        verify(output, atLeastOnce()).println(contains("Состояние виселицы:"));
        verify(output, atLeastOnce()).println(contains(gallowsModel.getHangmanStage()));
    }

    @Test
    void testGameStateAfterIncorrectGuess() {
        // Проверяем начальное состояние
        assertEquals("***", gallowsModel.getCurrentGuess());
        assertEquals(10, gallowsModel.getAttemptsLeft());

        gallowsView.displayGameState(gallowsModel.getCurrentGuess(), gallowsModel.getAttemptsLeft(),
            gallowsModel.getHangmanStage());

        // Проверяем ввод неправильной буквы
        boolean isFound = gallowsModel.checkLetter("а");
        assertFalse(isFound);
        assertEquals(9, gallowsModel.getAttemptsLeft()); // Уменьшаем количество попыток

        // Проверяем состояние после неправильного ввода
        assertEquals("***", gallowsModel.getCurrentGuess());
        assertEquals(9, gallowsModel.getAttemptsLeft());

        gallowsView.displayGameState(gallowsModel.getCurrentGuess(), gallowsModel.getAttemptsLeft(),
            gallowsModel.getHangmanStage());

        // Проверяем ввод неправильной буквы
        isFound = gallowsModel.checkLetter("у");
        assertFalse(isFound);
        assertEquals(8, gallowsModel.getAttemptsLeft()); // Уменьшаем количество попыток

        // Проверяем состояние после неправильного ввода
        assertEquals("***", gallowsModel.getCurrentGuess());
        assertEquals(8, gallowsModel.getAttemptsLeft());

        gallowsView.displayGameState(gallowsModel.getCurrentGuess(), gallowsModel.getAttemptsLeft(),
            gallowsModel.getHangmanStage());

        // Проверяем ввод правильной буквы
        isFound = gallowsModel.checkLetter("т");
        assertTrue(isFound);
        assertEquals("**т", gallowsModel.getCurrentGuess());
        assertEquals(8, gallowsModel.getAttemptsLeft());

        gallowsView.displayGameState(gallowsModel.getCurrentGuess(), gallowsModel.getAttemptsLeft(),
            gallowsModel.getHangmanStage());

        // Проверяем, что игра еще не закончилась
        assertFalse(gallowsModel.isWin());
        assertFalse(gallowsModel.isGameOver());
        verify(output, atLeastOnce()).println(contains("\nТекущее слово: **т"));
        verify(output, atLeastOnce()).println(contains("Осталось попыток: 8"));
        verify(output, atLeastOnce()).println(contains("Состояние виселицы:"));
        verify(output, atLeastOnce()).println(contains(gallowsModel.getHangmanStage()));
    }

    @Test
    void testRepeatedInputForInvalidGuess() {
        // Проверяем начальное состояние
        assertEquals("***", gallowsModel.getCurrentGuess());
        assertEquals(10, gallowsModel.getAttemptsLeft());

        // Проверяем состояние перед неправильным вводом
        gallowsView.displayGameState(gallowsModel.getCurrentGuess(), gallowsModel.getAttemptsLeft(),
            gallowsModel.getHangmanStage());

        // Попытка ввода строки длиной больше 1 символа
        String invalidInput = "кот";
        boolean isFound = gallowsModel.checkLetter(invalidInput); // Проверяем только первый символ

        // Убеждаемся, что состояние не изменилось
        assertFalse(isFound);
        assertEquals("***", gallowsModel.getCurrentGuess()); // Состояние слова не должно измениться
        assertEquals(10, gallowsModel.getAttemptsLeft()); // Количество попыток должно остаться прежним

        // Вызов метода отображения состояния игры
        gallowsView.displayGameState(gallowsModel.getCurrentGuess(), gallowsModel.getAttemptsLeft(),
            gallowsModel.getHangmanStage());

        // Проверка вывода
        verify(output, atLeastOnce()).println(contains("\nТекущее слово: ***"));
        verify(output, atLeastOnce()).println(contains("Осталось попыток: 10"));
        verify(output, atLeastOnce()).println(contains("Состояние виселицы:"));
        verify(output, atLeastOnce()).println(contains(gallowsModel.getHangmanStage()));
    }
}
