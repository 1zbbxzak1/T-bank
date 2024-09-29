package backend.academy.enums;

import backend.academy.interfaces.TranslatableEnum;

public enum DifficultyLevels implements TranslatableEnum {
    easy("Легкий"),
    medium("Средний"),
    hard("Сложный");

    private final String translationDifficultyLevels;

    DifficultyLevels(String translationDifficultyLevels) {
        this.translationDifficultyLevels = translationDifficultyLevels;
    }

    @Override
    public String getTranslation() {
        return translationDifficultyLevels;
    }
}
