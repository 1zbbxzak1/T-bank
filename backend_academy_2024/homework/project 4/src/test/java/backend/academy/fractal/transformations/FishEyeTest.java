package backend.academy.fractal.transformations;

import backend.academy.fractal.records.Point;
import backend.academy.fractal.transformations.interfaces.ITransformation;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FishEyeTest {
    @Test
    public void testFishEyeTransformation() {
        ITransformation transformation = new FishEye();

        Point point = new Point(1.0, 2.0);
        Point transformed = transformation.apply(point);

        double r = Math.sqrt(1.0 + 2.0 * 2.0);
        double expectedX = 2.0 * 2.0 / (r + 1);
        double expectedY = 2.0 / (r + 1);

        assertEquals(expectedX, transformed.x(), 0.0001);
        assertEquals(expectedY, transformed.y(), 0.0001);
    }
}
