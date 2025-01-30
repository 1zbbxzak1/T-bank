package backend.academy.fractal.render;

import backend.academy.fractal.records.Image;
import backend.academy.fractal.settings.FractalSettings;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FractalMultiGenerator extends FractalRenderer {
    public static Image getFractalImage(FractalSettings fractal) {
        FractalMultiGenerator multiThreaded = new FractalMultiGenerator();
        return multiThreaded.getImage(fractal);
    }

    protected Image getImage(FractalSettings fractal) {
        Image image = Image.create(fractal.width(), fractal.height());
        try (ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())) {
            for (int p = 0; p < fractal.points(); ++p) {
                Runnable task = () -> render(fractal, image);
                executor.execute(task);
            }
        }
        FractalCorrection.correct(image);
        return image;
    }
}
