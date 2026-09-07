package backend.academy.fractal.records;

public record Point(double x, double y) {
    public boolean isBordered(Border border) {
        return border.xMin() <= x && border.xMax() >= x && border.yMin() <= y && border.yMax() >= y;
    }
}
