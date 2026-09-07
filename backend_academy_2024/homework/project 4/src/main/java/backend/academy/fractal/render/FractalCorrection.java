package backend.academy.fractal.render;

import backend.academy.fractal.records.Image;
import backend.academy.fractal.records.Pixel;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FractalCorrection {
    public static final int MAX_RGB_VALUE = 255;
    public static final double GAMMA = 2.0;

    public static void correct(Image image) {
        double maxHits = 0.0;
        for (Pixel[] row : image.pixels()) {
            for (Pixel pixel : row) {
                if (pixel.alpha() > 0) {
                    maxHits = Math.max(maxHits, pixel.alpha());
                }
            }
        }
        double maxLog = Math.log10(maxHits);
        for (int y = 0; y < image.height(); y++) {
            for (int x = 0; x < image.width(); x++) {
                Pixel pixel = image.pixels()[y][x];
                if (pixel.alpha() > 0) {
                    double normalized = Math.log10(pixel.alpha()) / maxLog;
                    double gammaFactor = Math.pow(normalized, 1.0 / GAMMA);
                    image.pixels()[y][x] = enhanceColor(pixel, gammaFactor);
                }
            }
        }
    }

    static Pixel enhanceColor(Pixel pixel, double gammaFactor) {
        int red = Math.min((int) (pixel.red() * gammaFactor), MAX_RGB_VALUE);
        int green = Math.min((int) (pixel.green() * gammaFactor), MAX_RGB_VALUE);
        int blue = Math.min((int) (pixel.blue() * gammaFactor), MAX_RGB_VALUE);
        return new Pixel(red, green, blue, pixel.alpha());
    }
}
