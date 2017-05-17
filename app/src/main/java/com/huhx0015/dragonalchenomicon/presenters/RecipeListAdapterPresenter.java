package com.huhx0015.dragonalchenomicon.presenters;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import com.huhx0015.dragonalchenomicon.view.adapters.RecipeListAdapter;
import com.huhx0015.dragonalchenomicon.application.AlchenomiconApplication;
import com.huhx0015.dragonalchenomicon.contracts.RecipeListAdapterContract;
import com.huhx0015.dragonalchenomicon.model.repositories.RecipeListAdapterRepository;
import com.huhx0015.dragonalchenomicon.model.objects.AlchenomiconRecipe;
import com.huhx0015.dragonalchenomicon.utils.AlchenomiconImageUtils;
import java.util.List;
import javax.inject.Inject;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Michael Yoon Huh on 5/14/2017.
 */

public class RecipeListAdapterPresenter implements RecipeListAdapterContract.Presenter {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // LOGGING VARIABLES:
    private static final String LOG_TAG = RecipeListAdapterPresenter.class.getSimpleName();

    // REPOSITORY VARIABLES:
    @Inject RecipeListAdapterRepository mRepository;

    // RX VARIABLES:
    @NonNull private CompositeDisposable mDisposables;

    // VIEW VARIABLES:
    private RecipeListAdapterContract.View mView;

    /** CONSTRUCTOR METHODS ____________________________________________________________________ **/

    public RecipeListAdapterPresenter(RecipeListAdapterContract.View view) {
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
    public int getRecipeCount() {
        return mRepository.getRecipeCount();
    }

    @Override
    public void setRecipeList(List<AlchenomiconRecipe> recipeList) {
        mRepository.setRecipeList(recipeList);
    }

    @Override
    public void setRecipeRow(RecipeListAdapter.RecipeListViewHolder holder, int position) {
        mView.showRecipeRow(holder, mRepository.getRecipeList().get(position));
    }

    @Override
    public void setRecipeName(String recipeName, TextView view) {
        mView.showRecipeName(recipeName, view);
    }

    @Override
    public void setRecipeImage(int resource, ImageView view) {
        mView.showRecipeImage(resource, view);
    }

    @Override
    public void setRecipeIngredient(String ingredient, TextView view) {
        int ingredientResource = AlchenomiconImageUtils.getItemImage(ingredient);
        mView.showRecipeIngredient(ingredient, ingredientResource, view);
    }

    @Override
    public void clearRecipeImage(ImageView view) {
        mView.clearRecipeImage(view);
    }
}
