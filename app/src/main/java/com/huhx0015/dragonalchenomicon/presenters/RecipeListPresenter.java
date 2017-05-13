package com.huhx0015.dragonalchenomicon.presenters;

import android.support.annotation.NonNull;
import android.util.Log;
import com.huhx0015.dragonalchenomicon.application.AlchenomiconApplication;
import com.huhx0015.dragonalchenomicon.contracts.RecipeListContract;
import com.huhx0015.dragonalchenomicon.data.AlchenomiconDatabaseHelper;
import com.huhx0015.dragonalchenomicon.interfaces.AlchenomiconDatabaseListener;
import com.huhx0015.dragonalchenomicon.model.AlchenomiconRecipe;
import java.util.List;
import javax.inject.Inject;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Michael Yoon Huh on 5/11/2017.
 */

public class RecipeListPresenter implements RecipeListContract.Presenter {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // DATABASE VARIABLES:
    @Inject AlchenomiconDatabaseHelper mDatabase;

    // LOGGING VARIABLES:
    private static final String LOG_TAG = RecipeListPresenter.class.getSimpleName();

    // RX VARIABLES:
    @NonNull
    private CompositeDisposable mDisposables;

    // VIEW VARIABLES:
    private RecipeListContract.View mRecipeListView;

    /** CONSTRUCTOR METHODS ____________________________________________________________________ **/

    public RecipeListPresenter(RecipeListContract.View view) {
        this.mRecipeListView = view;
        this.mRecipeListView.setPresenter(this);
        this.mDisposables = new CompositeDisposable();
        AlchenomiconApplication.getInstance().getAppComponent().inject(this);
    }

    @Override
    public void subscribe() {
        mDisposables.clear();
    }

    @Override
    public void unsubscribe() {
        mDisposables.clear();
    }

    @Override
    public void onLoadRecipeList() {
        Log.d(LOG_TAG, "onLoadRecipeList(): Loading recipe list.");

        mRecipeListView.showProgressBar(true); // Displays the progress bar.

        // Queries the database to retrieve all available recipes.
        mDatabase.getAllRecipes(new AlchenomiconDatabaseListener.RecipeQueryListener() {
            @Override
            public void onQueryFinished(List<AlchenomiconRecipe> recipeList) {
                Log.d(LOG_TAG, "onQueryFinished(): Query for recipe list has finished.");
                onRecipeListLoaded(recipeList);
            }
        });
    }

    @Override
    public void onRecipeListLoaded(List<AlchenomiconRecipe> recipeList) {
        Log.d(LOG_TAG, "onRecipeListLoaded(): Recipe list load complete.");

        mRecipeListView.showProgressBar(false); // Hides the progress bar.
        mRecipeListView.showRecipeList(recipeList); // Signals the view to make the recipe RecyclerView visible.
    }
}
