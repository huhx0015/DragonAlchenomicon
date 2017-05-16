package com.huhx0015.dragonalchenomicon.contracts;

import com.huhx0015.dragonalchenomicon.interfaces.BasePresenter;
import com.huhx0015.dragonalchenomicon.interfaces.BaseView;
import java.util.HashSet;

/** -----------------------------------------------------------------------------------------------
 *  [IngredientPickerContract] CLASS
 *  DESCRIPTION: IngredientPickerContract is an interface that is the contract between the view and
 *  presenter for IngredientPickerDialog. It defines the responsibility of the Model, View, and the
 *  Presenter.
 *
 *  REFERENCE: https://www.codeproject.com/Articles/1098822/Learn-Android-MVP-Pattern-By-Example
 *  -----------------------------------------------------------------------------------------------
 */

public interface IngredientPickerContract {

    // View: Defines the methods that the concrete View aka Fragment will implement. This way you can
    // proceed to create and test the Presenter without worrying about Android-specific components
    // such as Context.
    interface View extends BaseView<Presenter> {

        void showIngredientList();

        void dismissPickerDialog(String ingredient);
    }

    // Presenter: Defines the methods that the concrete Presenter class will implement. Also known
    // as user actions, this is where the business logic for the app is defined.
    interface Presenter extends BasePresenter {

        int getButtonId();

        void setButtonId(int buttonId);

        HashSet<String> getIngredientList();

        void loadIngredientList();

        void setIngredientList(HashSet<String> ingredientList);

        void onIngredientSelected(String ingredient);
    }

    // Repository: Defines the methods that the concrete persistence class will implement. This way
    // the Presenter does not need to be concerned about how data is persisted.
    interface Repository {

        int getButtonId();

        void setButtonId(int buttonId);

        HashSet<String> getIngredientList();

        void setIngredientList(HashSet<String> ingredientList);
    }
}