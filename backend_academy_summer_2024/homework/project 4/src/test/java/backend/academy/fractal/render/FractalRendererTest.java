package backend.academy.fractal.render;

import backend.academy.fractal.records.Image;
import backend.academy.fractal.records.Point;
import backend.academy.fractal.settings.FractalSettings;
import backend.academy.fractal.transformations.interfaces.ITransformation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FractalRendererTest {
    private FractalRenderer renderer;
    private FractalSettings fractalSettings;
    private Image image;

    @BeforeEach
    void setUp() {
        renderer = new FractalRenderer();
        fractalSettings = createMockFractalSettings();
        image = createMockImage(100, 100);
    }

    private FractalSettings createMockFractalSettings() {
        return new FractalSettings(
            1000,
            1000,
            5,
            5,
            4,
            new backend.academy.fractal.records.AffineCoefficients[3],
            new ITransformation[2]
        );
    }

    private Image createMockImage(int width, int height) {
        backend.academy.fractal.records.Pixel[][] pixels = new backend.academy.fractal.records.Pixel[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                pixels[y][x] = new backend.academy.fractal.records.Pixel(0, 0, 0, 0);
            }
        }
        return new Image(pixels, width, height);
    }

    @Test
    void testRandomPointGeneration() {
        Point point = FractalRenderer.randomPoint();
        assertTrue(point.x() >= FractalRenderer.X_MIN && point.x() <= FractalRenderer.X_MAX);
        assertTrue(point.y() >= FractalRenderer.Y_MIN && point.y() <= FractalRenderer.Y_MAX);
    }

    @Test
    void testRotate() {
        Point point = new Point(1.0, 0.0);
        double theta = Math.PI / 2;
        Point rotatedPoint = FractalRenderer.rotate(point, theta);

        assertEquals(0.0, rotatedPoint.x(), 1e-6);
        assertEquals(1.0, rotatedPoint.y(), 1e-6);
    }
}
