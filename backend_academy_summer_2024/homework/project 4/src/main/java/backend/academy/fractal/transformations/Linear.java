package backend.academy.fractal.transformations;

import backend.academy.fractal.records.Point;
import backend.academy.fractal.transformations.interfaces.ITransformation;

public class Linear implements ITransformation {
    @Override
    public Point apply(Point point) {
        return new Point(point.x(), point.y());
    }
}
