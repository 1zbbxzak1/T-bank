package backend.academy.model;

import backend.academy.enums.CategoriesWords;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class DictionaryReader {
    private static final String ANIMAL_FILENAME = "resources/Animal.txt";
    private static final String NATURE_FILENAME = "resources/Nature.txt";
    private static final String FOOD_FILENAME = "resources/Food.txt";
    private static final String CITIES_FILENAME = "resources/Cities.txt";
    private static final String COUNTRIES_FILENAME = "resources/Countries.txt";
    private static final String HOBBY_FILENAME = "resources/Hobby.txt";
    private static final String POPULAR_FILENAME = "resources/Popular.txt";

    private List<String> words = new ArrayList<>();

    public List<String> getWords() {
        return words;
    }

    public void loadWordsByCategory(CategoriesWords category) throws IOException {
        words.clear();

        String filename = getFilenameByCategory(category);
        words = loadDictionary(filename);
    }

    public String getFilenameByCategory(CategoriesWords category) {
        return switch (category) {
            case ANIMAL -> ANIMAL_FILENAME;
            case NATURE -> NATURE_FILENAME;
            case FOOD -> FOOD_FILENAME;
            case CITIES -> CITIES_FILENAME;
            case COUNTRIES -> COUNTRIES_FILENAME;
            case HOBBY -> HOBBY_FILENAME;
            case POPULAR -> POPULAR_FILENAME;
        };
    }

    private List<String> loadDictionary(String filename) throws IOException {
        List<String> wordList = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                wordList.add(line.trim());
            }
        }
        return wordList;
    }
}
