package backend.academy.fractal.transformations;

import backend.academy.fractal.records.Point;
import backend.academy.fractal.transformations.interfaces.ITransformation;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LinearTest {
    @Test
    public void testLinearTransformation() {
        ITransformation transformation = new Linear();

        Point point = new Point(1.0, 2.0);
        Point transformed = transformation.apply(point);

        assertEquals(1.0, transformed.x());
        assertEquals(2.0, transformed.y());
    }
}
