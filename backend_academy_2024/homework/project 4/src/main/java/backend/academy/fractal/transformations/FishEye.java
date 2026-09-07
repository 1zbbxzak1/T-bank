package backend.academy.fractal.transformations;

import backend.academy.fractal.records.Point;
import backend.academy.fractal.transformations.interfaces.ITransformation;

public class FishEye implements ITransformation {
    @Override
    public Point apply(Point point) {
        double r = Math.sqrt(point.x() * point.x() + point.y() * point.y());
        return new Point(2 * point.y() / (r + 1), 2 * point.x() / (r + 1));
    }
}
