package backend.academy.fractal.transformations;

import backend.academy.fractal.records.Point;
import backend.academy.fractal.transformations.interfaces.ITransformation;

public class Julia implements ITransformation {
    @Override
    public Point apply(Point point) {
        double x = point.x();
        double y = point.y();

        double radius = Math.sqrt(x * x + y * y);
        double angle = Math.atan2(y, x);

        double newX = radius * Math.cos(angle);
        double newY = radius * Math.sin(angle);

        return new Point(newX, newY);
    }
}
