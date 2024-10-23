package backend.academy.test;

import backend.academy.enums.CategoriesWords;
import backend.academy.enums.DifficultyLevels;
import backend.academy.model.GallowsModel;
import backend.academy.model.WordSelector;
import java.io.PrintStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GallowsCaseInsensitiveTest {

    private GallowsModel gallowsModel;
    private PrintStream output;
    private WordSelector wordSelector;

    @BeforeEach
    void setUp() {
        // Создание mock-объекта PrintStream для вывода
        output = Mockito.mock(PrintStream.class);

        // Создание mock-объекта WordSelector
        wordSelector = Mockito.mock(WordSelector.class);

        // Создание модели с известным словом
        gallowsModel = new GallowsModel(CategoriesWords.ANIMAL, DifficultyLevels.EASY, output);
        gallowsModel.updateWordToGuess("кот");
    }

    @Test
    void testCheckLetterCaseInsensitive() {
        // Проверка начального состояния
        assertEquals("***", gallowsModel.getCurrentGuess());

        // Ввод строчной буквы "к"
        boolean isFoundLowerCase = gallowsModel.checkLetter("к");
        assertTrue(isFoundLowerCase);
        assertEquals("к**", gallowsModel.getCurrentGuess());

        // Сброс состояния
        gallowsModel.updateWordToGuess("кот");

        // Ввод заглавной буквы "К"
        boolean isFoundUpperCase = gallowsModel.checkLetter("К");
        assertTrue(isFoundUpperCase);
        assertEquals("к**", gallowsModel.getCurrentGuess());

        // Убедимся, что попытки не уменьшаются при правильном вводе
        assertEquals(10, gallowsModel.getAttemptsLeft());

        // Ввод неправильной заглавной буквы "О"
        boolean isNotFoundUpperCase = gallowsModel.checkLetter("О");
        assertTrue(isNotFoundUpperCase); // Поскольку "О" присутствует в слове
        assertEquals("ко*", gallowsModel.getCurrentGuess());

        // Ввод строчной буквы "т"
        boolean isFoundLowerCaseT = gallowsModel.checkLetter("т");
        assertTrue(isFoundLowerCaseT);
        assertEquals("кот", gallowsModel.getCurrentGuess());

        // Убедимся, что игра завершена победой
        assertTrue(gallowsModel.isWin());
        assertTrue(gallowsModel.isGameOver());  // Нет попыток на нуле
    }
}
