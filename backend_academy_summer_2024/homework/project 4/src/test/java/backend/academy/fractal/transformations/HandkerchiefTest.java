package backend.academy.fractal.transformations;

import backend.academy.fractal.records.Point;
import backend.academy.fractal.transformations.interfaces.ITransformation;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HandkerchiefTest {
    @Test
    public void testHandkerchiefTransformation() {
        ITransformation transformation = new Handkerchief();

        Point point = new Point(1.0, 2.0);
        Point transformed = transformation.apply(point);

        double r = Math.sqrt(1.0 + 2.0 * 2.0);
        double theta = Math.atan2(2.0, 1.0);

        double expectedX = r * Math.sin(theta + r);
        double expectedY = r * Math.cos(theta - r);

        assertEquals(expectedX, transformed.x(), 0.0001);
        assertEquals(expectedY, transformed.y(), 0.0001);
    }
}
