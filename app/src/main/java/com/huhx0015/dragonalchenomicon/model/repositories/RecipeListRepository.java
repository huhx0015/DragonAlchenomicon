package com.huhx0015.dragonalchenomicon.model.repositories;

import android.util.Log;
import com.huhx0015.dragonalchenomicon.application.AlchenomiconApplication;
import com.huhx0015.dragonalchenomicon.contracts.RecipeListContract;
import com.huhx0015.dragonalchenomicon.database.AlchenomiconDatabaseHelper;
import com.huhx0015.dragonalchenomicon.listeners.AlchenomiconDatabaseListener;
import com.huhx0015.dragonalchenomicon.listeners.RecipeListPresenterListener;
import com.huhx0015.dragonalchenomicon.model.objects.AlchenomiconRecipe;
import java.util.List;
import javax.inject.Inject;

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
    public void getAllRecipes(final RecipeListPresenterListener listener) {
        Thread databaseThread = new Thread(new Runnable() {
            @Override
            public void run() {
                mDatabase.getAllRecipes(new AlchenomiconDatabaseListener.RecipeQueryListener() {
                    @Override
                    public void onQueryFinished(List<AlchenomiconRecipe> recipeList) {
                        Log.d(LOG_TAG, "onQueryFinished(): Query for recipe list has finished.");
                        mRecipeList = recipeList;
                        listener.onRecipeListLoaded();
                    }
                });
            }
        });
        databaseThread.start();
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
