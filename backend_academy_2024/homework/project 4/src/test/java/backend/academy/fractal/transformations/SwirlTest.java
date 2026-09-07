package backend.academy.fractal.transformations;

import backend.academy.fractal.records.Point;
import backend.academy.fractal.transformations.interfaces.ITransformation;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SwirlTest {
    @Test
    public void testSwirlTransformation() {
        ITransformation transformation = new Swirl();

        Point point = new Point(1.0, 1.0);
        Point transformed = transformation.apply(point);

        double r = Math.sqrt(1.0 + 1.0);
        double expectedX = Math.sin(r * r) - Math.cos(r * r);
        double expectedY = Math.cos(r * r) + Math.sin(r * r);

        assertEquals(expectedX, transformed.x(), 0.0001);
        assertEquals(expectedY, transformed.y(), 0.0001);
    }
}
