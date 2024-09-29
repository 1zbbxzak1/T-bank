package backend.academy.test;

import backend.academy.view.GallowsStage;
import backend.academy.view.GallowsView;
import java.io.PrintStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class GallowsDisplayStageTest {
    private GallowsView view;
    private PrintStream output;

    @BeforeEach
    void setUp() {
        output = System.out;
        view = new GallowsView(output);
    }

    @Test
    void shouldCreateStagesForEasyLevel() {
        int maxAttempts = 10;
        String[] expectedStages = {
            GallowsStage.STAGE_EMPTY,
            GallowsStage.STAGE_1,
            GallowsStage.STAGE_2,
            GallowsStage.STAGE_3,
            GallowsStage.STAGE_4,
            GallowsStage.STAGE_5,
            GallowsStage.STAGE_6,
            GallowsStage.STAGE_7,
            GallowsStage.STAGE_8,
            GallowsStage.STAGE_9,
            GallowsStage.STAGE_10
        };

        String[] actualStages = view.createHangmanStages(maxAttempts);
        assertArrayEquals(expectedStages, actualStages);
    }

    @Test
    void shouldCreateStagesForMediumLevel() {
        int maxAttempts = 7;
        String[] expectedStages = {
            GallowsStage.STAGE_EMPTY,
            GallowsStage.STAGE_4,
            GallowsStage.STAGE_5,
            GallowsStage.STAGE_6,
            GallowsStage.STAGE_7,
            GallowsStage.STAGE_8,
            GallowsStage.STAGE_9,
            GallowsStage.STAGE_10
        };

        String[] actualStages = view.createHangmanStages(maxAttempts);
        assertArrayEquals(expectedStages, actualStages);
    }

    @Test
    void shouldCreateStagesForHardLevel() {
        int maxAttempts = 5;
        String[] expectedStages = {
            GallowsStage.STAGE_EMPTY,
            GallowsStage.STAGE_4,
            GallowsStage.STAGE_5,
            GallowsStage.STAGE_7,
            GallowsStage.STAGE_9,
            GallowsStage.STAGE_10
        };

        String[] actualStages = view.createHangmanStages(maxAttempts);
        assertArrayEquals(expectedStages, actualStages);
    }
}

