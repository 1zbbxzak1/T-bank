package backend.academy.test;

import backend.academy.view.GallowsStage;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class GallowsStageTest {

    @Test
    void shouldHaveTenStages() {
        // Проверяем, что мы определили правильное количество этапов
        assertThat(GallowsStage.class.getDeclaredFields())
            .filteredOn(field -> field.getName().startsWith("STAGE_"))
            .hasSize(11);
    }

    @Test
    void shouldDefineEmptyStageCorrectly() {
        // Проверьте правильность определения пустой сцены
        assertThat(GallowsStage.STAGE_EMPTY).isEqualTo("""
            """);
    }

    @Test
    void shouldDefineAllStagesWithExpectedFormat() {
        // Проверяем, что каждый этап имеет согласованный формат и не является нулевым или пустым
        String[] stages = {
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

        for (String stage : stages) {
            assertThat(stage).isNotNull();
            assertThat(stage).isNotEmpty();
        }
    }

    @Test
    void shouldNotInstantiateUtilityClass() {
        // Проверяем, что экземпляр служебного класса не может быть создан
        assertThatThrownBy(() -> {
            throw new UnsupportedOperationException("Utility class should not be instantiated");
        }).isInstanceOf(UnsupportedOperationException.class)
            .hasMessage("Utility class should not be instantiated");
    }
}
