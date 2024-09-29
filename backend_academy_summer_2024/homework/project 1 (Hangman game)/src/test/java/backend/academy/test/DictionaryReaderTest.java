package backend.academy.test;

import backend.academy.enums.CategoriesWords;
import backend.academy.model.DictionaryReader;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DictionaryReaderTest {

    private DictionaryReader dictionaryReader;

    @BeforeEach
    void setUp() throws IOException {
        dictionaryReader = new DictionaryReader();
    }

    @Test
    void testLoadWordsByCategory() throws IOException {
        // Предполагаем, что файл resources/Animal.txt содержит слова "кот", "собака"
        dictionaryReader.loadWordsByCategory(CategoriesWords.Animal);
        List<String> words = dictionaryReader.getWords();

        assertFalse(words.isEmpty(), "Список слов не должен быть пустым");
        assertTrue(words.contains("кот"), "Список слов должен содержать 'кот'");
        assertTrue(words.contains("собака"), "Список слов должен содержать 'собака'");
    }

    @Test
    void testGetFilenameByCategory() {
        assertEquals("resources/Animal.txt", dictionaryReader.getFilenameByCategory(CategoriesWords.Animal));
        assertEquals("resources/Nature.txt", dictionaryReader.getFilenameByCategory(CategoriesWords.Nature));
        assertEquals("resources/Food.txt", dictionaryReader.getFilenameByCategory(CategoriesWords.Food));
        assertEquals("resources/Cities.txt", dictionaryReader.getFilenameByCategory(CategoriesWords.Cities));
        assertEquals("resources/Countries.txt", dictionaryReader.getFilenameByCategory(CategoriesWords.Countries));
        assertEquals("resources/Hobby.txt", dictionaryReader.getFilenameByCategory(CategoriesWords.Hobby));
        assertEquals("resources/Popular.txt", dictionaryReader.getFilenameByCategory(CategoriesWords.Popular));
    }
}

