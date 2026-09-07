package backend.academy.fractal.settings;

import backend.academy.fractal.enums.ImageFormat;
import backend.academy.fractal.enums.Screen;
import backend.academy.fractal.records.Image;
import backend.academy.fractal.render.FractalMultiGenerator;
import backend.academy.fractal.render.FractalSingleGenerator;
import backend.academy.fractal.transformations.interfaces.ITransformation;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Scanner;
import lombok.experimental.UtilityClass;

@UtilityClass
public class StartSettings {
    private static final Path DEFAULT_PATH_FOR_IMAGE = Paths.get("FRACTAL-FLAME");
    private static final int MAX_SYMMETRY_INDEX = 5;
    private static final int POINTS = 300;
    private static final int EQ_COUNT = 30;

    private final static PrintStream OUTPUT = System.out;
    static Scanner in = new Scanner(System.in);

    public static void start() {
        Screen screenResolution = getScreenResolution();
        OUTPUT.println("Вы выбрали разрешение: " + screenResolution);

        int iterationsValue = getIterations();
        int symmetryIndex = getSymmetryIndex();
        Path saveDirectory = getSaveDirectory();

        ITransformation[] variations = GetTransforms.getTransforms();

        FractalSettings fractal = FractalSettings.create(
            POINTS, iterationsValue, EQ_COUNT, screenResolution.width(),
            screenResolution.height(), symmetryIndex, variations);

        generateAndSaveImages(fractal, saveDirectory);
    }

    private static Screen getScreenResolution() {
        GetScreen screenGetter = new GetScreen(OUTPUT, in);
        return screenGetter.getScreen();
    }

    private static int getIterations() {
        GetIterations iterations = new GetIterations(OUTPUT, in);
        return iterations.getIterations();
    }

    private static int getSymmetryIndex() {
        OUTPUT.println(
            "Введите показатель симметрии (степень двойки, где 0 - без симметрии): " + segment(0, MAX_SYMMETRY_INDEX));
        return correctIntInput();
    }

    private static Path getSaveDirectory() {
        OUTPUT.println(
            "Укажите директорию для сохранения изображений "
                + "(при вводе \"default\" или нажатии \"Enter\" они будут сохранены в папке \""
                + DEFAULT_PATH_FOR_IMAGE + "\"):");
        return correctPath();
    }

    private static void generateAndSaveImages(FractalSettings fractal, Path saveDirectory) {
        Image imageSingle = FractalSingleGenerator.getFractalImage(fractal);
        ImageUtils.save(imageSingle, ImageFormat.JPEG, saveDirectory);

        Image imageMulti = FractalMultiGenerator.getFractalImage(fractal);
        ImageUtils.save(imageMulti, ImageFormat.PNG, saveDirectory);

        Runnable single = () -> FractalSingleGenerator.getFractalImage(fractal);
        Runnable multi = () -> FractalMultiGenerator.getFractalImage(fractal);

        OUTPUT.println("Время при однопоточной реализации: " + getTime(single)
            + "\nВремя при многопоточной реализации (" + Runtime.getRuntime().availableProcessors() + "): "
            + getTime(multi));
    }

    private static long getTime(Runnable method) {
        long start = System.nanoTime();
        method.run();
        long end = System.nanoTime();
        return end - start;
    }

    private static int correctIntInput() {
        Optional<Integer> optSize = checkInput(in.nextLine());
        while (optSize.isEmpty() || optSize.get() < 0 || optSize.get() > StartSettings.MAX_SYMMETRY_INDEX) {
            OUTPUT.println("Введите число " + segment(0, StartSettings.MAX_SYMMETRY_INDEX));
            optSize = checkInput(in.nextLine());
        }
        return optSize.get();
    }

    private static Optional<Integer> checkInput(String str) {
        try {
            return Optional.of(Integer.parseInt(str.trim()));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    private static Path correctPath() {
        String inputLine = in.nextLine();
        while (inputLine != null && !inputLine.equals("default") && !inputLine.isEmpty()) {
            try {
                Path path = Paths.get(inputLine);
                if (Files.isDirectory(path)) {
                    return path;
                } else {
                    OUTPUT.println("Введённый путь не является директорией. Введите директорию или \"default\"");
                }
            } catch (Exception e) {
                OUTPUT.println("Путь введён неверно. Введите директорию или \"default\"");
            }
            inputLine = in.nextLine();
        }

        return DEFAULT_PATH_FOR_IMAGE;
    }

    private static String segment(int min, int max) {
        return "от " + min + " до " + max;
    }
}
