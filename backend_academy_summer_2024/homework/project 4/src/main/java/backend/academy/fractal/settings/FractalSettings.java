package backend.academy.fractal.settings;

import backend.academy.fractal.records.AffineCoefficients;
import backend.academy.fractal.transformations.interfaces.ITransformation;

public record FractalSettings(
    int points,
    int iterations,
    int width,
    int height,
    int symmetryParts,
    AffineCoefficients[] coefficients,
    ITransformation[] transformations
) {
    public static FractalSettings create(
        int points, int iterations, int eqCount, int width, int height,
        int symmetryIndex, ITransformation[] variations
    ) {
        AffineCoefficients[] coefficients = getAffineArray(eqCount);
        return new FractalSettings(points, iterations, width, height,
            (int) Math.pow(2, symmetryIndex), coefficients, variations);
    }

    public static AffineCoefficients[] getAffineArray(int eqCounts) {
        AffineCoefficients[] coefficients = new AffineCoefficients[eqCounts];
        for (int i = 0; i < eqCounts; ++i) {
            coefficients[i] = AffineCoefficients.create();
        }
        return coefficients;
    }
}
