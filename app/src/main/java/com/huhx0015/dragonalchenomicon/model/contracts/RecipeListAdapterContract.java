package com.huhx0015.dragonalchenomicon.model.contracts;

import android.widget.ImageView;
import android.widget.TextView;
import com.huhx0015.dragonalchenomicon.model.repositories.RecipeListAdapterRepository;
import com.huhx0015.dragonalchenomicon.view.adapters.RecipeListAdapter;
import com.huhx0015.dragonalchenomicon.presenters.BasePresenter;
import com.huhx0015.dragonalchenomicon.view.base.BaseView;
import com.huhx0015.dragonalchenomicon.model.objects.AlchenomiconRecipe;
import java.util.List;

/** -----------------------------------------------------------------------------------------------
 *  [RecipeListAdapterContract] CLASS
 *  DESCRIPTION: RecipeListAdapterContract is an interface that is the contract between the view and
 *  presenter for RecipeListAdapter. It defines the responsibility of the Model, View, and the
 *  Presenter.
 *
 *  REFERENCE: https://www.codeproject.com/Articles/1098822/Learn-Android-MVP-Pattern-By-Example
 *  -----------------------------------------------------------------------------------------------
 */
public interface RecipeListAdapterContract {

    // View: Defines the methods that the concrete View aka Adapter will implement. This way you can
    // proceed to create and test the Presenter without worrying about Android-specific components
    // such as Context.
    interface View extends BaseView<Presenter> {

        void showRecipeRow(RecipeListAdapter.RecipeListViewHolder holder, AlchenomiconRecipe recipe);

        void showRecipeName(String recipe, TextView view);

        void showRecipeImage(int resource, ImageView view);

        void showRecipeIngredient(String ingredient, int resource, TextView view);

        void clearRecipeImage(ImageView view);
    }

    // Presenter: Defines the methods that the concrete Presenter class will implement. Also known
    // as user actions, this is where the business logic for the app is defined.
    interface Presenter extends BasePresenter {

        RecipeListAdapterRepository getRepository();

        void setRecipeRow(RecipeListAdapter.RecipeListViewHolder holder, int position);

        void setRecipeName(String name, TextView view);

        void setRecipeImage(int resource, ImageView view);

        void setRecipeIngredient(String ingredient, TextView view);

        void clearRecipeImage(ImageView view);

    }

    // Repository: Defines the methods that the concrete persistence class will implement. This way
    // the Presenter does not need to be concerned about how data is persisted.
    interface Repository {

        int getRecipeCount();

        List<AlchenomiconRecipe> getRecipeList();

        void setRecipeList(List<AlchenomiconRecipe> recipeList);

    }
}
