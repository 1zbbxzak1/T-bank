package backend.academy.fractal.render;

import backend.academy.fractal.records.AffineCoefficients;
import backend.academy.fractal.records.Image;
import backend.academy.fractal.settings.FractalSettings;
import backend.academy.fractal.transformations.interfaces.ITransformation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FractalSingleGeneratorTest {

    private FractalSingleGenerator fractalSingleGenerator;

    @BeforeEach
    void setUp() {
        fractalSingleGenerator = new FractalSingleGenerator();
    }

    @Test
    void testGetFractalImage() {
        FractalSettings fractalSettings = mock(FractalSettings.class);
        when(fractalSettings.width()).thenReturn(800);
        when(fractalSettings.height()).thenReturn(600);
        when(fractalSettings.points()).thenReturn(30);
        when(fractalSettings.iterations()).thenReturn(100);

        AffineCoefficients[] coefficients = {mock(AffineCoefficients.class), mock(AffineCoefficients.class)};
        when(fractalSettings.coefficients()).thenReturn(coefficients);

        ITransformation[] transformations = {mock(ITransformation.class), mock(ITransformation.class)};
        when(fractalSettings.transformations()).thenReturn(transformations);

        Image image = FractalSingleGenerator.getFractalImage(fractalSettings);

        assertNotNull(image);
        assertEquals(800, image.width());
        assertEquals(600, image.height());
    }

    @Test
    void testGetImage() {
        FractalSettings fractalSettings = mock(FractalSettings.class);
        when(fractalSettings.width()).thenReturn(800);
        when(fractalSettings.height()).thenReturn(600);
        when(fractalSettings.points()).thenReturn(10);
        when(fractalSettings.iterations()).thenReturn(100);

        AffineCoefficients[] coefficients = {mock(AffineCoefficients.class), mock(AffineCoefficients.class)};
        when(fractalSettings.coefficients()).thenReturn(coefficients);

        ITransformation[] transformations = {mock(ITransformation.class), mock(ITransformation.class)};
        when(fractalSettings.transformations()).thenReturn(transformations);

        Image image = fractalSingleGenerator.getImage(fractalSettings);

        assertNotNull(image);
        assertEquals(800, image.width());
        assertEquals(600, image.height());
    }
}
