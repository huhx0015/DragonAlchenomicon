package com.huhx0015.dragonalchenomicon.contracts;

import com.huhx0015.dragonalchenomicon.interfaces.AlchemyPresenterListener;
import com.huhx0015.dragonalchenomicon.interfaces.BasePresenter;
import com.huhx0015.dragonalchenomicon.interfaces.BaseView;
import java.util.HashSet;

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

    // View: Defines the methods that the concrete View aka Fragment will implement. This way you can
    // proceed to create and test the Presenter without worrying about Android-specific components
    // such as Context.
    interface View extends BaseView<Presenter> {

        void showIngredientDialog(int buttonId);

        void showSelectedIngredient(int resource, int position);

        void updateSelectedIngredientText(int position);
    }

    // Presenter: Defines the methods that the concrete Presenter class will implement. Also known
    // as user actions, this is where the business logic for the app is defined.
    interface Presenter extends BasePresenter {

        HashSet<String> getIngredientList();

        void setIngredientList(HashSet<String> ingredientList);

        String getSelectedIngredient(int buttonId);

        String[] getSelectedIngredientList();

        void setSelectedIngredient(String ingredient, int buttonId);

        void setSelectedIngredientList(String[] ingredientList);

        void onIngredientButtonClicked(int buttonId);

    }

    // Repository: Defines the methods that the concrete persistence class will implement. This way
    // the Presenter does not need to be concerned about how data is persisted.
    interface Repository {

        void getAllIngredients(AlchemyPresenterListener listener);

        HashSet<String> getIngredientList();

        String getSelectedIngredient(int buttonId);

        String[] getSelectedIngredientList();

        void setIngredientList(HashSet<String> ingredientList);

        void setSelectedIngredient(String ingredient, int buttonId);

        void setSelectedIngredientList(String[] ingredientList);
    }
}
