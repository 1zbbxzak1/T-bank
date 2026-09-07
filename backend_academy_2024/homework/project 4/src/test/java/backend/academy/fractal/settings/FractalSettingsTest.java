package backend.academy.fractal.settings;

import backend.academy.fractal.records.AffineCoefficients;
import backend.academy.fractal.transformations.interfaces.ITransformation;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FractalSettingsTest {
    @Test
    void testCreateFractalSettings() {
        ITransformation[] transformations = new ITransformation[0];
        FractalSettings settings = FractalSettings.create(100, 500, 3, 800, 600, 2, transformations);

        assertEquals(100, settings.points());
        assertEquals(500, settings.iterations());
        assertEquals(800, settings.width());
        assertEquals(600, settings.height());
        assertEquals(4, settings.symmetryParts());
        assertEquals(3, settings.coefficients().length);
        assertEquals(0, settings.transformations().length);
    }

    @Test
    void testGetAffineArray() {
        AffineCoefficients[] coefficients = FractalSettings.getAffineArray(5);

        assertEquals(5, coefficients.length);
        for (AffineCoefficients coefficient : coefficients) {
            assertNotNull(coefficient);
        }
    }

    @Test
    void testCreateAffineCoefficients() {
        AffineCoefficients coefficients = AffineCoefficients.create();

        assertNotNull(coefficients);
    }
}
