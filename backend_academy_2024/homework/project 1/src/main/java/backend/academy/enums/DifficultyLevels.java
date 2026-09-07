package backend.academy.enums;

import backend.academy.interfaces.TranslatableEnum;

public enum DifficultyLevels implements TranslatableEnum {
    EASY("Легкий"),
    MEDIUM("Средний"),
    HARD("Сложный");

    private final String translationDifficultyLevels;

    DifficultyLevels(String translationDifficultyLevels) {
        this.translationDifficultyLevels = translationDifficultyLevels;
    }

    @Override
    public String getTranslation() {
        return translationDifficultyLevels;
    }
}
