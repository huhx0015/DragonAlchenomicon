package com.huhx0015.dragonalchenomicon.contracts;

import com.huhx0015.dragonalchenomicon.interfaces.BasePresenter;
import com.huhx0015.dragonalchenomicon.interfaces.BaseView;
import java.util.HashSet;
import java.util.List;

/** -----------------------------------------------------------------------------------------------
 *  [AlchemyContract] CLASS
 *  DESCRIPTION: AlchemyContract is an interface that is the contract between the view and
 *  presenter for AlchemyFragment. It defines the responsibility of the Model, View, and the
 *  Presenter.
 *
 *  REFERENCE: https://www.codeproject.com/Articles/1098822/Learn-Android-MVP-Pattern-By-Example
 *  -----------------------------------------------------------------------------------------------
 */

public interface AlchemyContract {

    // this defines the methods that the concrete View aka Fragment will implement. This way you can
    // proceed to create and test the Presenter without worrying about Android-specific components
    // such as Context.
    interface View extends BaseView<Presenter> {

        void showIngredientDialog(HashSet<String> ingredientList);

        void showRecipeResults();

        void setLoadingIndicator(boolean isActive);

        void showNoRecipeResults();
    }

    // this defines the methods that the concrete Presenter class will implement. Also known as user
    // actions, this is where the business logic for the app is defined.
    interface Presenter extends BasePresenter {

        void onIngredientButtonClicked(int buttonId);

        void onAlchemizeButtonClicked();

        void onMusicButtonClicked();

        void onClearButtonClicked();
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
