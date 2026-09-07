package backend.academy.constants;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class ConfigConstants {
    public static final int MAX_ATTEMPTS_EASY = 10;
    public static final int MAX_ATTEMPTS_MEDIUM = 7;
    public static final int MAX_ATTEMPTS_HARD = 5;

    public static final int MIN_WORD_LENGTH_EASY = 1;
    public static final int MAX_WORD_LENGTH_EASY = 5;

    public static final int MIN_WORD_LENGTH_MEDIUM = 6;
    public static final int MAX_WORD_LENGTH_MEDIUM = 8;

    public static final int MIN_WORD_LENGTH_HARD = 9;

    public static final String RANDOM_CHOICE_MESSAGE = "Выбор сделан случайным образом:";
}
