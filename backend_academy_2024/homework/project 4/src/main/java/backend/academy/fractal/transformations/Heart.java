package backend.academy.fractal.transformations;

import backend.academy.fractal.records.Point;
import backend.academy.fractal.transformations.interfaces.ITransformation;

public class Heart implements ITransformation {
    @Override
    public Point apply(Point point) {
        double radius = Math.sqrt(point.x() * point.x() + point.y() * point.y());
        double angle = Math.atan2(point.y(), point.x());

        return new Point(radius * Math.sin(radius * angle), -radius * Math.cos(radius * angle));
    }
}
