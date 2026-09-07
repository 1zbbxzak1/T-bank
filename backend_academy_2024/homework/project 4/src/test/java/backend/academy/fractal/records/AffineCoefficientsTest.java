package backend.academy.fractal.records;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AffineCoefficientsTest {
    @Test
    public void testCreate() {
        AffineCoefficients affineCoefficients = AffineCoefficients.create();

        assertNotNull(affineCoefficients.coefficient());

        assertTrue(affineCoefficients.red() >= 0 && affineCoefficients.red() <= 255);
        assertTrue(affineCoefficients.green() >= 0 && affineCoefficients.green() <= 255);
        assertTrue(affineCoefficients.blue() >= 0 && affineCoefficients.blue() <= 255);
    }

    @Test
    public void testAffineTransform() {
        AffineCoefficients affineCoefficients = AffineCoefficients.create();

        Point point = new Point(2, 3);
        Point transformedPoint = affineCoefficients.affineTransform(point);

        double expectedX =
            affineCoefficients.coefficient().a() * point.x() + affineCoefficients.coefficient().b() * point.y() +
                affineCoefficients.coefficient().c();
        double expectedY =
            affineCoefficients.coefficient().d() * point.x() + affineCoefficients.coefficient().e() * point.y() +
                affineCoefficients.coefficient().f();

        assertEquals(expectedX, transformedPoint.x(), 0.0001);
        assertEquals(expectedY, transformedPoint.y(), 0.0001);
    }

    @Test
    public void testHitPixel() {
        AffineCoefficients affineCoefficients = AffineCoefficients.create();

        Pixel pixel = new Pixel(100, 150, 200, 255);

        Pixel blendedPixel = affineCoefficients.hitPixel(pixel);

        int expectedRed = (pixel.red() + affineCoefficients.red()) / 2;
        int expectedGreen = (pixel.green() + affineCoefficients.green()) / 2;
        int expectedBlue = (pixel.blue() + affineCoefficients.blue()) / 2;

        assertEquals(expectedRed, blendedPixel.red());
        assertEquals(expectedGreen, blendedPixel.green());
        assertEquals(expectedBlue, blendedPixel.blue());
        assertEquals(256, blendedPixel.alpha());
    }

    @Test
    public void testAffineTransformWithDifferentPoints() {
        AffineCoefficients affineCoefficients = AffineCoefficients.create();

        Point point1 = new Point(0, 0);
        Point point2 = new Point(1, 1);
        Point point3 = new Point(-1, -1);

        Point transformedPoint1 = affineCoefficients.affineTransform(point1);
        Point transformedPoint2 = affineCoefficients.affineTransform(point2);
        Point transformedPoint3 = affineCoefficients.affineTransform(point3);

        assertNotNull(transformedPoint1);
        assertNotNull(transformedPoint2);
        assertNotNull(transformedPoint3);
    }

    @Test
    public void testPixelBlendingWhenAlphaIsZero() {
        AffineCoefficients affineCoefficients = AffineCoefficients.create();

        Pixel pixel = new Pixel(100, 150, 200, 0);

        Pixel blendedPixel = affineCoefficients.hitPixel(pixel);

        assertEquals(affineCoefficients.red(), blendedPixel.red());
        assertEquals(affineCoefficients.green(), blendedPixel.green());
        assertEquals(affineCoefficients.blue(), blendedPixel.blue());
        assertEquals(1, blendedPixel.alpha());
    }
}
