package backend.academy.benchmarks.graph.settings.custom;

import java.awt.Color;
import java.awt.Paint;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.jfree.chart.renderer.category.BarRenderer;

@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CustomBarRenderer extends BarRenderer {
    private final Color[] colors;

    @Override
    public Paint getItemPaint(int row, int column) {
        return colors[column % colors.length];
    }
}
