package backend.academy.fractal.transformations;

import backend.academy.fractal.records.Point;
import backend.academy.fractal.transformations.interfaces.ITransformation;

public class Handkerchief implements ITransformation {
    @Override
    public Point apply(Point point) {
        double r = Math.sqrt(point.x() * point.x() + point.y() * point.y());
        double theta = Math.atan2(point.y(), point.x());
        return new Point(r * Math.sin(theta + r), r * Math.cos(theta - r));
    }
}
