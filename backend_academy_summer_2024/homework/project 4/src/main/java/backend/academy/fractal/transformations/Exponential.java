package backend.academy.fractal.transformations;

import backend.academy.fractal.records.Point;
import backend.academy.fractal.transformations.interfaces.ITransformation;

public class Exponential implements ITransformation {
    @Override
    public Point apply(Point point) {
        double newX = Math.exp(point.x() - 1) * Math.cos(Math.PI * point.y());
        double newY = Math.exp(point.x() - 1) * Math.sin(Math.PI * point.y());
        return new Point(newX, newY);
    }
}
