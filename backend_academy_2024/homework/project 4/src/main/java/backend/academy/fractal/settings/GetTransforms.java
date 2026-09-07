package backend.academy.fractal.settings;

import backend.academy.fractal.transformations.Disk;
import backend.academy.fractal.transformations.Exponential;
import backend.academy.fractal.transformations.FishEye;
import backend.academy.fractal.transformations.Handkerchief;
import backend.academy.fractal.transformations.Heart;
import backend.academy.fractal.transformations.Julia;
import backend.academy.fractal.transformations.Linear;
import backend.academy.fractal.transformations.Polar;
import backend.academy.fractal.transformations.Sin;
import backend.academy.fractal.transformations.Spherical;
import backend.academy.fractal.transformations.Spiral;
import backend.academy.fractal.transformations.Swirl;
import backend.academy.fractal.transformations.interfaces.ITransformation;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import lombok.experimental.UtilityClass;

@UtilityClass
public class GetTransforms {
    private static final List<ITransformation> TRANSFORMATIONS = List.of(
        new Linear(),
        new Polar(),
        new Spiral(),
        new Heart(),
        new Disk(),
        new Julia(),
        new Exponential(),
        new FishEye(),
        new Handkerchief(),
        new Sin(),
        new Spherical(),
        new Swirl()
    );

    private static final PrintStream OUT = System.out;
    private static final Scanner IN = new Scanner(System.in);

    public static Optional<Integer> checkForInt(String str, int max) {
        try {
            int input = Integer.parseInt(str.trim());
            return (input < 0 || input > max) ? Optional.empty() : Optional.of(input);
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public static ITransformation[] getTransforms() {
        OUT.println("Выберите вариации трансформаций:");
        for (int i = 0; i < TRANSFORMATIONS.size(); i++) {
            OUT.println((i + 1) + ". " + TRANSFORMATIONS.get(i).getClass().getSimpleName());
        }

        OUT.println("""
            Введите номера выбираемых вариаций. Номера вводите по одному. Для окончания введите 0.
            Одну вариацию можно выбрать несколько раз, увеличив этим её вес при генерации изображения""");

        List<ITransformation> variations = new ArrayList<>();
        Optional<Integer> input = checkForInt(IN.nextLine(), TRANSFORMATIONS.size());
        while (input.isEmpty() || input.get() != 0) {
            if (input.isEmpty()) {
                OUT.println("Введите число от 1 до " + TRANSFORMATIONS.size() + " или 0 для завершения выбора");
            } else {
                variations.add(TRANSFORMATIONS.get(input.get() - 1));
            }
            input = checkForInt(IN.nextLine(), TRANSFORMATIONS.size());
        }

        if (!variations.isEmpty()) {
            return variations.toArray(new ITransformation[0]);
        }

        OUT.println("Ни одна трансформация не выбрана. Завершение работы.");
        return new ITransformation[0];
    }
}
