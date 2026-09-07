package backend.academy.fractal.render;

import backend.academy.fractal.records.Image;
import backend.academy.fractal.records.Pixel;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FractalCorrectionTest {
    @Test
    void testCorrectImage() {
        Pixel[][] pixels = new Pixel[2][2];
        pixels[0][0] = new Pixel(100, 100, 100, 10);
        pixels[0][1] = new Pixel(150, 150, 150, 20);
        pixels[1][0] = new Pixel(200, 200, 200, 30);
        pixels[1][1] = new Pixel(255, 255, 255, 40);

        Image image = new Image(pixels, 2, 2);

        FractalCorrection.correct(image);

        assertTrue(image.pixels()[0][0].alpha() > 0);
        assertTrue(image.pixels()[0][1].alpha() > 0);
        assertTrue(image.pixels()[1][0].alpha() > 0);
        assertTrue(image.pixels()[1][1].alpha() > 0);

        assertEquals(79, image.pixels()[0][0].red());
        assertEquals(79, image.pixels()[0][0].green());
        assertEquals(79, image.pixels()[0][0].blue());

        assertEquals(135, image.pixels()[0][1].red());
        assertEquals(135, image.pixels()[0][1].green());
        assertEquals(135, image.pixels()[0][1].blue());

        assertEquals(192, image.pixels()[1][0].red());
        assertEquals(192, image.pixels()[1][0].green());
        assertEquals(192, image.pixels()[1][0].blue());

        assertEquals(255, image.pixels()[1][1].red());
        assertEquals(255, image.pixels()[1][1].green());
        assertEquals(255, image.pixels()[1][1].blue());
    }

    @Test
    void testEnhanceColor() {
        Pixel pixel = new Pixel(100, 100, 100, 10);
        Pixel enhancedPixel = FractalCorrection.enhanceColor(pixel, 1.2);

        assertEquals(120, enhancedPixel.red());
        assertEquals(120, enhancedPixel.green());
        assertEquals(120, enhancedPixel.blue());
        assertEquals(10, enhancedPixel.alpha());
    }

    @Test
    void testMaxAlphaHandling() {
        Pixel[][] pixels = new Pixel[2][2];
        pixels[0][0] = new Pixel(255, 255, 255, 0);
        pixels[0][1] = new Pixel(255, 255, 255, 0);
        pixels[1][0] = new Pixel(255, 255, 255, 0);
        pixels[1][1] = new Pixel(255, 255, 255, 0);

        Image image = new Image(pixels, 2, 2);

        FractalCorrection.correct(image);

        assertEquals(0, image.pixels()[0][0].alpha());
        assertEquals(0, image.pixels()[0][1].alpha());
        assertEquals(0, image.pixels()[1][0].alpha());
        assertEquals(0, image.pixels()[1][1].alpha());
    }
}
