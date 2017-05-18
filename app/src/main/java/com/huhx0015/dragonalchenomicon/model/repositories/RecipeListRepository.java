package com.huhx0015.dragonalchenomicon.model.repositories;

import com.huhx0015.dragonalchenomicon.application.AlchenomiconApplication;
import com.huhx0015.dragonalchenomicon.model.contracts.RecipeListContract;
import com.huhx0015.dragonalchenomicon.database.AlchenomiconDatabaseHelper;
import com.huhx0015.dragonalchenomicon.model.objects.AlchenomiconRecipe;
import java.util.List;
import javax.inject.Inject;
import io.reactivex.Observable;

/**
 * Created by Michael Yoon Huh on 5/13/2017.
 */

public class RecipeListRepository implements RecipeListContract.Repository {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // DATA VARIABLES:
    private List<AlchenomiconRecipe> mRecipeList;

    // DATABASE VARIABLES:
    @Inject AlchenomiconDatabaseHelper mDatabase;

    // LOGGING VARIABLES:
    private static final String LOG_TAG = RecipeListRepository.class.getSimpleName();

    /** CONSTRUCTOR METHODS ____________________________________________________________________ **/

    @Inject
    public RecipeListRepository() {
        AlchenomiconApplication.getInstance().getAppComponent().inject(this);
    }

    /** REPOSITORY METHODS _____________________________________________________________________ **/

    @Override
    public Observable<List<AlchenomiconRecipe>> loadAllRecipes() {
        return Observable.create(emitter -> {
            try {
                mRecipeList = mDatabase.getAllRecipes();

                // If the recipe list is not null, no issues have occurred with retrieving all the
                // recipes from the database.
                if (mRecipeList != null) {
                    emitter.onComplete(); // Signals that the operation has completed.
                }
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }

    @Override
    public List<AlchenomiconRecipe> getRecipeList() {
        return mRecipeList;
    }

    @Override
    public void setRecipeList(List<AlchenomiconRecipe> recipeList) {
        this.mRecipeList = recipeList;
    }
}
