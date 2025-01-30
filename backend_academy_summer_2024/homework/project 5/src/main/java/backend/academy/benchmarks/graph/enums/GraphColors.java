package backend.academy.benchmarks.graph.enums;

import java.awt.Color;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GraphColors {
    BLUE(Color.BLUE),
    GREEN(Color.GREEN),
    ORANGE(Color.ORANGE),
    MAGENTA(Color.MAGENTA);

    private final Color color;

    public static Color[] getColors() {
        GraphColors[] values = GraphColors.values();
        Color[] colors = new Color[values.length];
        for (int i = 0; i < values.length; i++) {
            colors[i] = values[i].color();
        }
        return colors;
    }

}
