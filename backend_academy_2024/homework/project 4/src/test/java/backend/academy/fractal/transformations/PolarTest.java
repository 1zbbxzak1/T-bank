package backend.academy.fractal.transformations;

import backend.academy.fractal.records.Point;
import backend.academy.fractal.transformations.interfaces.ITransformation;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PolarTest {
    @Test
    public void testPolarTransformation() {
        ITransformation transformation = new Polar();

        Point point = new Point(1.0, 2.0);
        Point transformed = transformation.apply(point);

        double radius = Math.sqrt(1.0 + 2.0 * 2.0);
        double angle = Math.atan2(2.0, 1.0);

        double expectedX = angle / Math.PI;
        double expectedY = radius - 1;

        assertEquals(expectedX, transformed.x(), 0.0001);
        assertEquals(expectedY, transformed.y(), 0.0001);
    }
}
