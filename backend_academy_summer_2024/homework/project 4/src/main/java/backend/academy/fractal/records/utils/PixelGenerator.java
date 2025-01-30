package backend.academy.fractal.records.utils;

import backend.academy.fractal.records.Pixel;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PixelGenerator {
    public static Pixel blendPixel(Pixel pixel, int red, int green, int blue) {
        if (pixel.alpha() == 0) {
            return new Pixel(red, green, blue, 1);
        }

        int newRed = (pixel.red() + red) / 2;
        int newGreen = (pixel.green() + green) / 2;
        int newBlue = (pixel.blue() + blue) / 2;
        int newAlpha = pixel.alpha() + 1;

        return new Pixel(newRed, newGreen, newBlue, newAlpha);
    }
}
