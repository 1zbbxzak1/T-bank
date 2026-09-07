package backend.academy.fractal.transformations;

import backend.academy.fractal.records.Point;
import backend.academy.fractal.transformations.interfaces.ITransformation;

public class Spherical implements ITransformation {
    @Override
    public Point apply(Point point) {
        double r = point.x() * point.x() + point.y() * point.y();
        return new Point(point.x() / r, point.y() / r);
    }
}
