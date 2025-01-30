package backend.academy.fractal.transformations;

import backend.academy.fractal.records.Point;
import backend.academy.fractal.transformations.interfaces.ITransformation;

public class Spiral implements ITransformation {
    @Override
    public Point apply(Point point) {
        double radius = Math.sqrt(point.x() * point.x() + point.y() * point.y());
        double angle = Math.atan2(point.y(), point.x());
        return new Point((Math.cos(angle) + Math.sin(radius)) / radius, (Math.sin(angle) - Math.cos(radius)) / radius);
    }
}
