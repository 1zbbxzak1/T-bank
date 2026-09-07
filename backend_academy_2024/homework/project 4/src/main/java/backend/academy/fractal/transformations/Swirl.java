package backend.academy.fractal.transformations;

import backend.academy.fractal.records.Point;
import backend.academy.fractal.transformations.interfaces.ITransformation;

public class Swirl implements ITransformation {
    @Override
    public Point apply(Point point) {
        double r = Math.sqrt(point.x() * point.x() + point.y() * point.y());
        double newX = point.x() * Math.sin(r * r) - point.y() * Math.cos(r * r);
        double newY = point.x() * Math.cos(r * r) + point.y() * Math.sin(r * r);
        return new Point(newX, newY);
    }
}
