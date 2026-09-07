package backend.academy.fractal.records.utils;

import backend.academy.fractal.records.Coefficient;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CoefficientGeneratorTest {
    @Test
    public void testGenerateValidCoefficient() {
        Coefficient coefficient = CoefficientGenerator.generateValidCoefficient();

        double a = coefficient.a();
        double b = coefficient.b();
        double d = coefficient.d();
        double e = coefficient.e();

        assertTrue(isValid(a, b, d, e));
    }

    @Test
    public void testGenerateValidCoefficient_multipleTimes() {
        for (int i = 0; i < 100; i++) {
            Coefficient coefficient = CoefficientGenerator.generateValidCoefficient();
            double a = coefficient.a();
            double b = coefficient.b();
            double d = coefficient.d();
            double e = coefficient.e();

            assertTrue(isValid(a, b, d, e));
        }
    }

    @Test
    public void testRandomnessOfGeneratedCoefficients() {
        Coefficient coefficient1 = CoefficientGenerator.generateValidCoefficient();
        Coefficient coefficient2 = CoefficientGenerator.generateValidCoefficient();

        assertNotEquals(coefficient1, coefficient2);
    }

    @Test
    public void testCoefficientInRange() {
        Coefficient coefficient = CoefficientGenerator.generateValidCoefficient();

        assertTrue(isWithinRange(coefficient));
    }

    private boolean isValid(double a, double b, double d, double e) {
        double determinant = a * e - b * d;
        return (a * a + b * b < 1)
            && (d * d + e * e < 1)
            && (a * a + b * b + d * d + e * e < 1 + determinant * determinant);
    }

    private boolean isWithinRange(Coefficient coefficient) {
        double a = coefficient.a();
        double b = coefficient.b();
        double c = coefficient.c();
        double d = coefficient.d();
        double e = coefficient.e();
        double f = coefficient.f();

        return (Math.abs(a) <= 1.5 && Math.abs(b) <= 1.5 && Math.abs(d) <= 1.5 && Math.abs(e) <= 1.5)
            && (Math.abs(c) <= 3.5 && Math.abs(f) <= 3.5);
    }
}
