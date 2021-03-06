package com.huhx0015.dragonalchenomicon.model.contracts;

import com.huhx0015.dragonalchenomicon.model.repositories.RecipeListRepository;
import com.huhx0015.dragonalchenomicon.presenters.BasePresenter;
import com.huhx0015.dragonalchenomicon.view.base.BaseView;
import com.huhx0015.dragonalchenomicon.model.objects.AlchenomiconRecipe;
import java.util.List;
import io.reactivex.Observable;

/** -----------------------------------------------------------------------------------------------
 *  [RecipeListContract] CLASS
 *  DESCRIPTION: RecipeListContract is an interface that is the contract between the view and
 *  presenter for RecipeListFragment. It defines the responsibility of the Model, View, and the
 *  Presenter.
 *
 *  REFERENCE: https://www.codeproject.com/Articles/1098822/Learn-Android-MVP-Pattern-By-Example
 *  -----------------------------------------------------------------------------------------------
 */

public interface RecipeListContract {

    // View: Defines the methods that the concrete View aka Fragment will implement. This way you can
    // proceed to create and test the Presenter without worrying about Android-specific components
    // such as Context.
    interface View extends BaseView<Presenter> {

        void showProgressBar(boolean isDisplay);

        void showRecipeList();
    }

    // Presenter: Defines the methods that the concrete Presenter class will implement. Also known
    // as user actions, this is where the business logic for the app is defined.
    interface Presenter extends BasePresenter {

        RecipeListRepository getRepository();

        void onLoadRecipeList();

    }

    // Repository: Defines the methods that the concrete persistence class will implement. This way
    // the Presenter does not need to be concerned about how data is persisted.
    interface Repository {

        Observable<List<AlchenomiconRecipe>> loadAllRecipes();

        List<AlchenomiconRecipe> getRecipeList();

        void setRecipeList(List<AlchenomiconRecipe> recipeList);
    }
}
