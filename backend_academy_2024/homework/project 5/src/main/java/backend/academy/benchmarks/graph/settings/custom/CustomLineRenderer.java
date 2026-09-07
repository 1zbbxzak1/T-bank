package backend.academy.benchmarks.graph.settings.custom;

import java.awt.Color;
import java.awt.Paint;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;

@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CustomLineRenderer extends XYLineAndShapeRenderer {
    private final Color[] colors;

    @Override
    public Paint getItemPaint(int row, int col) {
        return colors[row % colors.length];
    }
}
