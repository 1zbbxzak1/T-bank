package backend.academy.fractal.transformations;

import backend.academy.fractal.records.Point;
import backend.academy.fractal.transformations.interfaces.ITransformation;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JuliaTest {
    @Test
    public void testJuliaTransformation() {
        ITransformation transformation = new Julia();

        Point point = new Point(1.0, 2.0);
        Point transformed = transformation.apply(point);

        double radius = Math.sqrt(1.0 + 2.0 * 2.0);
        double angle = Math.atan2(2.0, 1.0);

        double expectedX = radius * Math.cos(angle);
        double expectedY = radius * Math.sin(angle);

        assertEquals(expectedX, transformed.x(), 0.0001);
        assertEquals(expectedY, transformed.y(), 0.0001);
    }
}
