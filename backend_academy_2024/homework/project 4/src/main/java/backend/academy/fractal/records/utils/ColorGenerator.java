package backend.academy.fractal.records.utils;

import backend.academy.fractal.records.Color;
import java.util.Random;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ColorGenerator {
    private static final int MAX_RGB_VALUE = 255;
    private static final Random RANDOM = new Random();

    public static Color generateRandomColor() {
        return new Color(
            RANDOM.nextInt(MAX_RGB_VALUE + 1),
            RANDOM.nextInt(MAX_RGB_VALUE + 1),
            RANDOM.nextInt(MAX_RGB_VALUE + 1)
        );
    }
}
