package backend.academy.test;

import backend.academy.enums.CategoriesWords;
import backend.academy.enums.DifficultyLevels;
import backend.academy.model.GallowsModel;
import java.io.PrintStream;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GallowsInvalidWordLengthTest {

    @Test
    void testInvalidWordLength() {
        // Создаем mock-объект PrintStream для вывода
        PrintStream output = Mockito.mock(PrintStream.class);

        // Используем обычный конструктор GallowsModel
        GallowsModel gallowsModel = new GallowsModel(CategoriesWords.Animal, DifficultyLevels.easy, output);

        // Проверяем, что выбрасывается IllegalArgumentException при установке пустого слова
        assertThrows(IllegalArgumentException.class, () -> {
            gallowsModel.updateWordToGuess(""); // Попытка установить пустое слово
        });
    }
}
