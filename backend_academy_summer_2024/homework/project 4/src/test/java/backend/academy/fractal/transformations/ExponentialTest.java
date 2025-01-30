package backend.academy.fractal.transformations;

import backend.academy.fractal.records.Point;
import backend.academy.fractal.transformations.interfaces.ITransformation;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExponentialTest {
    @Test
    public void testExponentialTransformation() {
        ITransformation transformation = new Exponential();

        Point point = new Point(1.0, 0.5);
        Point transformed = transformation.apply(point);

        double expectedX = Math.exp(1.0 - 1) * Math.cos(Math.PI * 0.5);
        double expectedY = Math.exp(1.0 - 1) * Math.sin(Math.PI * 0.5);

        assertEquals(expectedX, transformed.x(), 0.0001);
        assertEquals(expectedY, transformed.y(), 0.0001);
    }
}
