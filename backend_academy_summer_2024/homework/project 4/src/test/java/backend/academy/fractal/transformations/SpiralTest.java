package backend.academy.fractal.transformations;

import backend.academy.fractal.records.Point;
import backend.academy.fractal.transformations.interfaces.ITransformation;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpiralTest {
    @Test
    public void testSpiralTransformation() {
        ITransformation transformation = new Spiral();

        Point point = new Point(1.0, 1.0);
        Point transformed = transformation.apply(point);

        double radius = Math.sqrt(1.0 + 1.0);
        double angle = Math.atan2(1.0, 1.0);

        double expectedX = (Math.cos(angle) + Math.sin(radius)) / radius;
        double expectedY = (Math.sin(angle) - Math.cos(radius)) / radius;

        assertEquals(expectedX, transformed.x(), 0.0001);
        assertEquals(expectedY, transformed.y(), 0.0001);
    }
}
