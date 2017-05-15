package com.huhx0015.dragonalchenomicon.presenters;

import android.support.annotation.NonNull;
import android.util.Log;
import com.huhx0015.dragonalchenomicon.application.AlchenomiconApplication;
import com.huhx0015.dragonalchenomicon.contracts.RecipeListContract;
import com.huhx0015.dragonalchenomicon.data.repositories.RecipeListRepository;
import com.huhx0015.dragonalchenomicon.interfaces.RecipeListPresenterListener;
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
    public List<AlchenomiconRecipe> getRecipeList() {
        return mRepository.getRecipeList();
    }

    @Override
    public void setRecipeList(List<AlchenomiconRecipe> recipeList) {
        mRepository.setRecipeList(recipeList);
    }

    @Override
    public void onLoadRecipeList() {
        Log.d(LOG_TAG, "onLoadRecipeList(): Loading recipe list.");

        // Queries the database to retrieve all available recipes.
        if (mRepository.getRecipeList() == null) {
            mView.showProgressBar(true); // Displays the progress bar.

            mRepository.getAllRecipes(new RecipeListPresenterListener() {
                @Override
                public void onRecipeListLoaded() {
                    Log.d(LOG_TAG, "onRecipeListLoaded(): Recipe list load complete.");

                    mView.showProgressBar(false); // Hides the progress bar.
                    mView.showRecipeList(); // Signals the view to make the recipe RecyclerView visible.
                }
            });
        } else {
            mView.showRecipeList(); // Signals the view to make the recipe RecyclerView visible.
        }
    }
}
