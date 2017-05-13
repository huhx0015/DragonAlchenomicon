package com.huhx0015.dragonalchenomicon.presenters;

import android.support.annotation.NonNull;
import android.util.Log;
import com.huhx0015.dragonalchenomicon.application.AlchenomiconApplication;
import com.huhx0015.dragonalchenomicon.contracts.RecipeListContract;
import com.huhx0015.dragonalchenomicon.data.repositories.RecipeListRepository;
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

    // LOGGING VARIABLES:
    private static final String LOG_TAG = RecipeListPresenter.class.getSimpleName();

    // REPOSITORY VARIABLES:
    @Inject RecipeListRepository mRepository;

    // RX VARIABLES:
    @NonNull private CompositeDisposable mDisposables;

    // VIEW VARIABLES:
    private RecipeListContract.View mView;

    /** CONSTRUCTOR METHODS ____________________________________________________________________ **/

    public RecipeListPresenter(RecipeListContract.View view) {
        this.mView = view;
        this.mView.setPresenter(this);
        this.mDisposables = new CompositeDisposable();
        AlchenomiconApplication.getInstance().getAppComponent().inject(this);
    }

    /** PRESENTER METHODS ______________________________________________________________________ **/

    @Override
    public void subscribe() {
        Log.d(LOG_TAG, "subscribe(): Presenter subscribed.");
        mDisposables.clear();
    }

    @Override
    public void unsubscribe() {
        Log.d(LOG_TAG, "unsubscribe(): Presenter unsubscribed.");
        mDisposables.clear();
    }

    @Override
    public void onLoadRecipeList() {
        Log.d(LOG_TAG, "onLoadRecipeList(): Loading recipe list.");

        mView.showProgressBar(true); // Displays the progress bar.

        // Queries the database to retrieve all available recipes.
        mRepository.getAllRecipes(new AlchenomiconDatabaseListener.RecipeQueryListener() {
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

        mView.showProgressBar(false); // Hides the progress bar.
        mView.showRecipeList(recipeList); // Signals the view to make the recipe RecyclerView visible.
    }
}
