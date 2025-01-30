package backend.academy.fractal.transformations;

import backend.academy.fractal.records.Point;
import backend.academy.fractal.transformations.interfaces.ITransformation;

public class Disk implements ITransformation {
    @Override
    public Point apply(Point point) {
        double piRad = Math.PI * Math.sqrt(point.x() * point.x() + point.y() * point.y());
        double angle = Math.atan2(point.y(), point.x());

        return new Point(angle * Math.sin(piRad) / Math.PI, angle * Math.cos(piRad) / Math.PI);
    }
}
