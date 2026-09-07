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

        model = new GallowsModel(CategoriesWords.ANIMAL, DifficultyLevels.EASY, output);
        controller = new GallowsController(view, output);

        // Примеры выбора категорий
        CategoriesWords selectedCategory = controller.selectCategory();

        // Проверка, что категория выбрана
        assertNotNull(selectedCategory);
        assertEquals(CategoriesWords.ANIMAL, selectedCategory); // Проверка на конкретную категорию
    }

    @Test
    void shouldSelectRandomCategoryWhenInputIsEmpty() {
        InputStream input = new ByteArrayInputStream("\n".getBytes(StandardCharsets.UTF_8));
        System.setIn(input);

        model = new GallowsModel(CategoriesWords.ANIMAL, DifficultyLevels.EASY, output);
        controller = new GallowsController(view, output);

        CategoriesWords selectedCategory = controller.selectCategory();

        assertNotNull(selectedCategory);
        assertTrue(Arrays.asList(CategoriesWords.values()).contains(selectedCategory));
    }

    @Test
    void shouldPromptForNewInputOnInvalidNumber() {
        InputStream input = new ByteArrayInputStream("999\n1\n".getBytes(StandardCharsets.UTF_8));
        System.setIn(input);

        model = new GallowsModel(CategoriesWords.ANIMAL, DifficultyLevels.EASY, output);
        controller = new GallowsController(view, output);

        CategoriesWords selectedCategory = controller.selectCategory();

        // Проверяем, что категория выбрана после второго корректного ввода
        assertNotNull(selectedCategory);
        assertEquals(CategoriesWords.ANIMAL, selectedCategory);
    }

    @Test
    void shouldDisplayErrorAndPromptAgainForInvalidInput() {
        InputStream input = new ByteArrayInputStream("invalid\n1\n".getBytes(StandardCharsets.UTF_8));
        System.setIn(input);

        model = new GallowsModel(CategoriesWords.ANIMAL, DifficultyLevels.EASY, output);
        controller = new GallowsController(view, output);

        CategoriesWords selectedCategory = controller.selectCategory();

        // Проверяем, что после некорректного ввода корректный был принят
        assertNotNull(selectedCategory);
        assertEquals(CategoriesWords.ANIMAL, selectedCategory);
    }

    @Test
    void shouldSelectDifficultyLevelCorrectly() {
        InputStream input = new ByteArrayInputStream("2\n".getBytes(StandardCharsets.UTF_8));
        System.setIn(input);

        model = new GallowsModel(CategoriesWords.ANIMAL, DifficultyLevels.MEDIUM, output);
        controller = new GallowsController(view, output);

        // Примеры выбора уровня сложности
        DifficultyLevels selectedLevel = controller.selectDifficultyLevel();

        // Проверка, что уровень сложности выбран
        assertNotNull(selectedLevel);
        assertEquals(DifficultyLevels.MEDIUM, selectedLevel);
    }

    @Test
    void shouldSelectRandomLevelWhenInputIsEmpty() {
        InputStream input = new ByteArrayInputStream("\n".getBytes(StandardCharsets.UTF_8));
        System.setIn(input);

        model = new GallowsModel(CategoriesWords.ANIMAL, DifficultyLevels.EASY, output);
        controller = new GallowsController(view, output);

        DifficultyLevels selectedDifficultyLevel = controller.selectDifficultyLevel();

        assertNotNull(selectedDifficultyLevel);
        assertTrue(Arrays.asList(DifficultyLevels.values()).contains(selectedDifficultyLevel));
    }

    @Test
    void shouldPromptForNewInputOnInvalidNumberLevel() {
        InputStream input = new ByteArrayInputStream("9\n3\n".getBytes(StandardCharsets.UTF_8));
        System.setIn(input);

        model = new GallowsModel(CategoriesWords.ANIMAL, DifficultyLevels.HARD, output);
        controller = new GallowsController(view, output);

        DifficultyLevels selectedDifficultyLevel = controller.selectDifficultyLevel();

        // Проверяем, что категория выбрана после второго корректного ввода
        assertNotNull(selectedDifficultyLevel);
        assertEquals(DifficultyLevels.HARD, selectedDifficultyLevel);
    }

    @Test
    void shouldDisplayErrorAndPromptAgainForInvalidInputLevel() {
        InputStream input = new ByteArrayInputStream("invalid\n1\n".getBytes(StandardCharsets.UTF_8));
        System.setIn(input);

        model = new GallowsModel(CategoriesWords.ANIMAL, DifficultyLevels.EASY, output);
        controller = new GallowsController(view, output);

        DifficultyLevels selectedDifficultyLevel = controller.selectDifficultyLevel();

        // Проверяем, что после некорректного ввода корректный был принят
        assertNotNull(selectedDifficultyLevel);
        assertEquals(DifficultyLevels.EASY, selectedDifficultyLevel);
    }
}
