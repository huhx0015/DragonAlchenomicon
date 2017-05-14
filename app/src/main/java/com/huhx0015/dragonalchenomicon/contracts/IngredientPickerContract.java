package com.huhx0015.dragonalchenomicon.contracts;

import com.huhx0015.dragonalchenomicon.interfaces.BasePresenter;
import com.huhx0015.dragonalchenomicon.interfaces.BaseView;

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

        void loadIngredientList();

        void onIngredientClicked(String ingredient);

    }
}