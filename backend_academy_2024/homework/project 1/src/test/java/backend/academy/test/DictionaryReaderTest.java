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
        dictionaryReader.loadWordsByCategory(CategoriesWords.ANIMAL);
        List<String> words = dictionaryReader.getWords();

        assertFalse(words.isEmpty(), "Список слов не должен быть пустым");
        assertTrue(words.contains("кот"), "Список слов должен содержать 'кот'");
        assertTrue(words.contains("собака"), "Список слов должен содержать 'собака'");
    }

    @Test
    void testGetFilenameByCategory() {
        assertEquals("resources/Animal.txt", dictionaryReader.getFilenameByCategory(CategoriesWords.ANIMAL));
        assertEquals("resources/Nature.txt", dictionaryReader.getFilenameByCategory(CategoriesWords.NATURE));
        assertEquals("resources/Food.txt", dictionaryReader.getFilenameByCategory(CategoriesWords.FOOD));
        assertEquals("resources/Cities.txt", dictionaryReader.getFilenameByCategory(CategoriesWords.CITIES));
        assertEquals("resources/Countries.txt", dictionaryReader.getFilenameByCategory(CategoriesWords.COUNTRIES));
        assertEquals("resources/Hobby.txt", dictionaryReader.getFilenameByCategory(CategoriesWords.HOBBY));
        assertEquals("resources/Popular.txt", dictionaryReader.getFilenameByCategory(CategoriesWords.POPULAR));
    }
}

