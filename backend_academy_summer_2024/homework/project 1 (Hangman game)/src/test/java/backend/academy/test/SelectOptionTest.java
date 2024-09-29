package backend.academy.test;

import backend.academy.controller.GallowsController;
import backend.academy.enums.CategoriesWords;
import backend.academy.enums.DifficultyLevels;
import backend.academy.model.GallowsModel;
import backend.academy.view.GallowsView;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SelectOptionTest {
    private GallowsModel model;
    private GallowsView view;
    private PrintStream output;
    private GallowsController controller;

    @BeforeEach
    void setUp() {
        output = System.out;
        view = new GallowsView(output);
    }

    @Test
    void shouldSelectCategoryCorrectly() {
        // Настройка моков
        InputStream input = new ByteArrayInputStream("1\n".getBytes(StandardCharsets.UTF_8));
        System.setIn(input);

        model = new GallowsModel(CategoriesWords.Animal, DifficultyLevels.easy, output);
        controller = new GallowsController(model, view, output);

        // Примеры выбора категорий
        CategoriesWords selectedCategory = controller.selectCategory();

        // Проверка, что категория выбрана
        assertNotNull(selectedCategory);
        assertEquals(CategoriesWords.Animal, selectedCategory); // Проверка на конкретную категорию
    }

    @Test
    void shouldSelectDifficultyLevelCorrectly() {
        InputStream input = new ByteArrayInputStream("2\n".getBytes(StandardCharsets.UTF_8));
        System.setIn(input);

        model = new GallowsModel(CategoriesWords.Animal, DifficultyLevels.easy, output);
        controller = new GallowsController(model, view, output);

        // Примеры выбора уровня сложности
        DifficultyLevels selectedLevel = controller.selectDifficultyLevel();

        // Проверка, что уровень сложности выбран
        assertNotNull(selectedLevel);
        assertEquals(DifficultyLevels.medium, selectedLevel);
    }

    @Test
    void shouldSelectRandomCategoryWhenInputIsEmpty() {
        InputStream input = new ByteArrayInputStream("\n".getBytes(StandardCharsets.UTF_8));
        System.setIn(input);

        model = new GallowsModel(CategoriesWords.Animal, DifficultyLevels.easy, output);
        controller = new GallowsController(model, view, output);

        CategoriesWords selectedCategory = controller.selectCategory();

        assertNotNull(selectedCategory);
        assertTrue(Arrays.asList(CategoriesWords.values()).contains(selectedCategory));
    }

    @Test
    void shouldSelectRandomCategoryWhenInputIsInvalid() {
        InputStream input = new ByteArrayInputStream("999\n".getBytes(StandardCharsets.UTF_8));
        System.setIn(input);

        model = new GallowsModel(CategoriesWords.Animal, DifficultyLevels.easy, output);
        controller = new GallowsController(model, view, output);

        CategoriesWords selectedCategory = controller.selectCategory();

        assertNotNull(selectedCategory);
        assertTrue(Arrays.asList(CategoriesWords.values()).contains(selectedCategory));
    }

    @Test
    void shouldSelectRandomLevelWhenInputIsInvalid() {
        InputStream input = new ByteArrayInputStream("9\n".getBytes(StandardCharsets.UTF_8));
        System.setIn(input);

        model = new GallowsModel(CategoriesWords.Animal, DifficultyLevels.easy, output);
        controller = new GallowsController(model, view, output);

        DifficultyLevels selectedDifficultyLevel = controller.selectDifficultyLevel();

        assertNotNull(selectedDifficultyLevel);
        assertTrue(Arrays.asList(DifficultyLevels.values()).contains(selectedDifficultyLevel));
    }
}
