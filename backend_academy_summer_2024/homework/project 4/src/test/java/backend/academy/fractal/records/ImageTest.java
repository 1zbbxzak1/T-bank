package backend.academy.fractal.records;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ImageTest {
    @Test
    public void testCreateImage() {
        int width = 3;
        int height = 2;
        Image image = Image.create(width, height);

        assertEquals(width, image.width());
        assertEquals(height, image.height());

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Pixel pixel = image.pixels()[y][x];
                assertEquals(0, pixel.red());
                assertEquals(0, pixel.green());
                assertEquals(0, pixel.blue());
                assertEquals(0, pixel.alpha());
            }
        }
    }

    @Test
    public void testContains_withValidCoordinates() {
        Image image = Image.create(3, 3);

        assertTrue(image.contains(0, 0));
        assertTrue(image.contains(1, 1));
        assertTrue(image.contains(2, 2));
    }

    @Test
    public void testContains_withInvalidCoordinates() {
        Image image = Image.create(3, 3);

        assertFalse(image.contains(-1, 0));
        assertFalse(image.contains(3, 0));
        assertFalse(image.contains(0, -1));
        assertFalse(image.contains(0, 3));
    }

    @Test
    public void testContains_withEdgeCoordinates() {
        Image image = Image.create(3, 3);

        assertTrue(image.contains(0, 0));
        assertTrue(image.contains(2, 0));
        assertTrue(image.contains(0, 2));
        assertTrue(image.contains(2, 2));
    }

    @Test
    public void testPixelInitialization() {
        int width = 5;
        int height = 5;
        Image image = Image.create(width, height);

        Pixel pixel = image.pixels()[2][2];
        assertEquals(0, pixel.red());
        assertEquals(0, pixel.green());
        assertEquals(0, pixel.blue());
        assertEquals(0, pixel.alpha());
    }
}
