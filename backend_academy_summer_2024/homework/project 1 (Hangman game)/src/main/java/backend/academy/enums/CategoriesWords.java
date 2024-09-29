package backend.academy.enums;

import backend.academy.interfaces.TranslatableEnum;
import backend.academy.model.DictionaryReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public enum CategoriesWords implements TranslatableEnum {
    Animal("Животные"),
    Nature("Природа"),
    Food("Еда"),
    Cities("Города"),
    Countries("Страны"),
    Hobby("Хобби"),
    Popular("1000 популярных");

    private static final Map<CategoriesWords, String[]> WORDS_MAP = new HashMap<>();

    private final String translationCategories;

    CategoriesWords(String translationCategories) {
        this.translationCategories = translationCategories;
    }

    public String[] getWords() {
        return WORDS_MAP.computeIfAbsent(this, key -> {
            try {
                DictionaryReader dict = new DictionaryReader();
                dict.loadWordsByCategory(key);
                return dict.getWords().toArray(new String[0]);
            } catch (IOException e) {
                throw new RuntimeException("Ошибка загрузки словаря для категории: " + key.getTranslation(), e);
            }
        });
    }

    @Override
    public String getTranslation() {
        return translationCategories;
    }

    @Override
    public String toString() {
        return this.translationCategories;
    }
}
