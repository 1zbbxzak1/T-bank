package backend.academy.fractal.records.utils;

import backend.academy.fractal.records.Pixel;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PixelGeneratorTest {
    @Test
    public void testBlendPixel_withAlphaZero() {
        Pixel pixel = new Pixel(100, 150, 200, 0);

        Pixel blendedPixel = PixelGenerator.blendPixel(pixel, 255, 0, 0);

        assertEquals(255, blendedPixel.red());
        assertEquals(0, blendedPixel.green());
        assertEquals(0, blendedPixel.blue());
        assertEquals(1, blendedPixel.alpha());
    }

    @Test
    public void testBlendPixel_withAlphaGreaterThanZero() {
        Pixel pixel = new Pixel(100, 150, 200, 1);

        Pixel blendedPixel = PixelGenerator.blendPixel(pixel, 255, 0, 0);

        assertEquals(177, blendedPixel.red());
        assertEquals(75, blendedPixel.green());
        assertEquals(100, blendedPixel.blue());

        assertEquals(2, blendedPixel.alpha());
    }

    @Test
    public void testBlendPixel_edgeValues() {
        Pixel pixel = new Pixel(0, 0, 0, 1);
        Pixel blendedPixel = PixelGenerator.blendPixel(pixel, 255, 255, 255);

        assertEquals(127, blendedPixel.red());
        assertEquals(127, blendedPixel.green());
        assertEquals(127, blendedPixel.blue());

        assertEquals(2, blendedPixel.alpha());
    }

    @Test
    public void testBlendPixel_noChange() {
        Pixel pixel = new Pixel(100, 150, 200, 1);
        Pixel blendedPixel = PixelGenerator.blendPixel(pixel, 100, 150, 200);

        assertEquals(100, blendedPixel.red());
        assertEquals(150, blendedPixel.green());
        assertEquals(200, blendedPixel.blue());
        assertEquals(2, blendedPixel.alpha());
    }

    @Test
    public void testBlendPixel_edgeAlphaValues() {
        Pixel pixel = new Pixel(100, 150, 200, 255);
        Pixel blendedPixel = PixelGenerator.blendPixel(pixel, 50, 100, 150);

        assertEquals(75, blendedPixel.red());
        assertEquals(125, blendedPixel.green());
        assertEquals(175, blendedPixel.blue());

        assertEquals(256, blendedPixel.alpha());
    }
}
