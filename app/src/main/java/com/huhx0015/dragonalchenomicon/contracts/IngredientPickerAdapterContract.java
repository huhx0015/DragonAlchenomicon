package com.huhx0015.dragonalchenomicon.contracts;

import android.widget.ImageView;
import android.widget.TextView;
import com.huhx0015.dragonalchenomicon.view.adapters.IngredientPickerDialogAdapter;
import com.huhx0015.dragonalchenomicon.interfaces.BasePresenter;
import com.huhx0015.dragonalchenomicon.interfaces.BaseView;
import java.util.HashSet;
import java.util.List;

/** -----------------------------------------------------------------------------------------------
 *  [IngredientPickerAdapterContract] CLASS
 *  DESCRIPTION: IngredientPickerAdapterContract is an interface that is the contract between the
 *  view and presenter for IngredientPickerDialogAdapter. It defines the responsibility of the Model,
 *  View, and the Presenter.
 *
 *  REFERENCE: https://www.codeproject.com/Articles/1098822/Learn-Android-MVP-Pattern-By-Example
 *  -----------------------------------------------------------------------------------------------
 */

public interface IngredientPickerAdapterContract {

    // View: Defines the methods that the concrete View aka Adapter will implement. This way you can
    // proceed to create and test the Presenter without worrying about Android-specific components
    // such as Context.
    interface View extends BaseView<Presenter> {

        void showIngredientRow(IngredientPickerDialogAdapter.IngredientPickerViewHolder holder, String ingredient);

        void showIngredientName(String ingredient, TextView view);

        void showIngredientImage(int resource, ImageView view);

        void dismissIngredientPicker(String ingredient);

        void clearIngredientImage(ImageView view);
    }

    // Presenter: Defines the methods that the concrete Presenter class will implement. Also known
    // as user actions, this is where the business logic for the app is defined.
    interface Presenter extends BasePresenter {

        void onIngredientClicked(int position);

        int getIngredientCount();

        void setIngredientList(HashSet<String> ingredientList);

        void setIngredientRow(IngredientPickerDialogAdapter.IngredientPickerViewHolder holder, int position);

        void setIngredientName(String ingredient, TextView view);

        void setIngredientImage(int resource, ImageView view);

        void clearIngredientImage(ImageView view);

    }

    // Repository: Defines the methods that the concrete persistence class will implement. This way
    // the Presenter does not need to be concerned about how data is persisted.
    interface Repository {

        int getIngredientCount();

        List<String> getIngredientList();

        void setIngredientList(HashSet<String> ingredientList);

    }
}
