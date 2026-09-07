package backend.academy.fractal.transformations;

import backend.academy.fractal.records.Point;
import backend.academy.fractal.transformations.interfaces.ITransformation;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SinTest {
    @Test
    public void testSinTransformation() {
        ITransformation transformation = new Sin();

        Point point = new Point(Math.PI, Math.PI);
        Point transformed = transformation.apply(point);

        assertEquals(Math.sin(Math.PI), transformed.x());
        assertEquals(Math.sin(Math.PI), transformed.y());
    }
}
