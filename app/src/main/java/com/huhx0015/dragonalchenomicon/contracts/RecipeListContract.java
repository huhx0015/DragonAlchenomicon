package com.huhx0015.dragonalchenomicon.contracts;

import com.huhx0015.dragonalchenomicon.interfaces.BasePresenter;
import com.huhx0015.dragonalchenomicon.interfaces.BaseView;
import java.util.List;

/**
 * Created by Michael Yoon Huh on 5/11/2017.
 */

public interface RecipeListContract {

    // this defines the methods that the concrete View aka Fragment will implement. This way you can
    // proceed to create and test the Presenter without worrying about Android-specific components
    // such as Context.
    interface View extends BaseView<RecipeListContract.Presenter> {
        void showRecipeList();
    }

    // this defines the methods that the concrete Presenter class will implement. Also known as user
    // actions, this is where the business logic for the app is defined.
    interface Presenter extends BasePresenter {
        void onRecipeListLoad();
    }

    // this defines the methods that the concrete persistence class will implement. This way the
    // Presenter does not need to be concerned about how data is persisted.
    interface Repository {

        void getIngredientResourceId(int id);

        void updateIngredients(List<String> recipeList);

        void updateRecipe(String recipe);

        void removeRecipe();
    }
}
