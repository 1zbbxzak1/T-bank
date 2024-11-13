package backend.academy.interfaces;

import backend.academy.enums.CategoriesWords;
import backend.academy.enums.DifficultyLevels;
import backend.academy.model.GallowsModel;

public interface IGallowsController {
    CategoriesWords selectCategory();

    DifficultyLevels selectDifficultyLevel();

    void startGame(GallowsModel model);
}
