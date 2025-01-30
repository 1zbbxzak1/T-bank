package backend.academy.fractal.render;

import backend.academy.fractal.records.Image;
import backend.academy.fractal.settings.FractalSettings;

public class FractalSingleGenerator extends FractalRenderer {
    public static Image getFractalImage(FractalSettings fractal) {
        FractalSingleGenerator singleThreaded = new FractalSingleGenerator();
        return singleThreaded.getImage(fractal);
    }

    protected Image getImage(FractalSettings fractal) {
        Image image = Image.create(fractal.width(), fractal.height());
        for (int p = 0; p < fractal.points(); ++p) {
            render(fractal, image);
        }
        FractalCorrection.correct(image);
        return image;
    }
}
