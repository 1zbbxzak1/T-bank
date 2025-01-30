package backend.academy.fractal.records.utils;

import backend.academy.fractal.records.Color;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ColorGeneratorTest {
    @Test
    public void testGenerateRandomColor() {
        Color color = ColorGenerator.generateRandomColor();

        assertTrue(isValidRGB(color));
    }

    @Test
    public void testGenerateRandomColor_multipleTimes() {
        for (int i = 0; i < 100; i++) {
            Color color = ColorGenerator.generateRandomColor();
            assertTrue(isValidRGB(color));
        }
    }

    @Test
    public void testRandomnessOfGeneratedColors() {
        Color color1 = ColorGenerator.generateRandomColor();
        Color color2 = ColorGenerator.generateRandomColor();

        assertNotEquals(color1, color2);
    }

    private boolean isValidRGB(Color color) {
        int r = color.red();
        int g = color.green();
        int b = color.blue();

        return r >= 0 && r <= 255 && g >= 0 && g <= 255 && b >= 0 && b <= 255;
    }
}
