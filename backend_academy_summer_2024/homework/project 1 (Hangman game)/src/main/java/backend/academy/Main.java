package backend.academy;

import backend.academy.controller.GallowsController;
import backend.academy.enums.CategoriesWords;
import backend.academy.enums.DifficultyLevels;
import backend.academy.model.GallowsModel;
import backend.academy.view.GallowsView;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {
    public static void main(String[] args) {
        GallowsView view = new GallowsView(System.out);
        GallowsController controller = new GallowsController(null, view, System.out);

        CategoriesWords selectedCategory = controller.selectCategory();
        DifficultyLevels selectedLevel = controller.selectDifficultyLevel();

        GallowsModel model = new GallowsModel(selectedCategory, selectedLevel, System.out);
        controller = new GallowsController(model, view, System.out);

        controller.playGame();
    }
}
