package backend.academy.fractal.records.utils;

import backend.academy.fractal.records.Coefficient;
import java.util.Random;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CoefficientGenerator {
    private static final double RANGE_LINEAR = 1.5;
    private static final double RANGE_SHIFT = 3.5;
    private static final Random RANDOM = new Random();

    public static Coefficient generateValidCoefficient() {
        double a;
        double b;
        double c;
        double d;
        double e;
        double f;

        do {
            a = randomValue(RANGE_LINEAR);
            b = randomValue(RANGE_LINEAR);
            c = randomValue(RANGE_SHIFT);
            d = randomValue(RANGE_LINEAR);
            e = randomValue(RANGE_LINEAR);
            f = randomValue(RANGE_SHIFT);
        } while (!isValid(a, b, d, e));

        return new Coefficient(a, b, c, d, e, f);
    }

    private static double randomValue(double range) {
        return (RANDOM.nextDouble() * 2 * range) - range;
    }

    private static boolean isValid(double a, double b, double d, double e) {
        double determinant = a * e - b * d;
        return (a * a + b * b < 1)
            && (d * d + e * e < 1)
            && (a * a + b * b + d * d + e * e < 1 + determinant * determinant);
    }
}
