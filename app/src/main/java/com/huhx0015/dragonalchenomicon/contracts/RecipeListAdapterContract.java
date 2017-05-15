package com.huhx0015.dragonalchenomicon.contracts;

import com.huhx0015.dragonalchenomicon.adapters.RecipeListAdapter;
import com.huhx0015.dragonalchenomicon.interfaces.BasePresenter;
import com.huhx0015.dragonalchenomicon.interfaces.BaseView;
import com.huhx0015.dragonalchenomicon.model.AlchenomiconRecipe;
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

    }

    // Presenter: Defines the methods that the concrete Presenter class will implement. Also known
    // as user actions, this is where the business logic for the app is defined.
    interface Presenter extends BasePresenter {

        int getRecipeCount();

        void setRecipeList(List<AlchenomiconRecipe> recipeList);

        void setRecipeRow(RecipeListAdapter.RecipeListViewHolder holder, int position);

    }

    // Repository: Defines the methods that the concrete persistence class will implement. This way
    // the Presenter does not need to be concerned about how data is persisted.
    interface Repository {

        int getRecipeCount();

        List<AlchenomiconRecipe> getRecipeList();

        void setRecipeList(List<AlchenomiconRecipe> recipeList);

    }
}
