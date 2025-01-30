package backend.academy.fractal.records;

import backend.academy.fractal.records.utils.CoefficientGenerator;
import backend.academy.fractal.records.utils.ColorGenerator;
import backend.academy.fractal.records.utils.PixelGenerator;

public record AffineCoefficients(
    Coefficient coefficient,
    int red,
    int green,
    int blue
) {
    public static AffineCoefficients create() {
        Coefficient coeff = CoefficientGenerator.generateValidCoefficient();
        Color color = ColorGenerator.generateRandomColor();

        return new AffineCoefficients(
            coeff,
            color.red(),
            color.green(),
            color.blue()
        );
    }

    public Point affineTransform(Point point) {
        double x = coefficient.a() * point.x() + coefficient.b() * point.y() + coefficient.c();
        double y = coefficient.d() * point.x() + coefficient.e() * point.y() + coefficient.f();
        return new Point(x, y);
    }

    public Pixel hitPixel(Pixel pixel) {
        return PixelGenerator.blendPixel(pixel, red, green, blue);
    }
}
