package com.huhx0015.dragonalchenomicon.presenters;

import android.support.annotation.NonNull;
import android.util.Log;
import com.huhx0015.dragonalchenomicon.application.AlchenomiconApplication;
import com.huhx0015.dragonalchenomicon.constants.AlchenomiconConstants;
import com.huhx0015.dragonalchenomicon.contracts.AlchemyContract;
import com.huhx0015.dragonalchenomicon.data.repositories.AlchemyRepository;
import com.huhx0015.dragonalchenomicon.listeners.AlchemyPresenterListener;
import com.huhx0015.dragonalchenomicon.model.AlchenomiconRecipe;
import com.huhx0015.dragonalchenomicon.utils.AlchenomiconImageUtils;
import java.util.HashSet;
import java.util.List;
import javax.inject.Inject;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Michael Yoon Huh on 5/11/2017.
 */

public class AlchemyPresenter implements AlchemyContract.Presenter {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // CONSTANT VARIABLES:
    private static final int INGREDIENT_BUTTON_1_ID = 0;
    private static final int INGREDIENT_BUTTON_2_ID = 1;
    private static final int INGREDIENT_BUTTON_3_ID = 2;

    // LOGGING VARIABLES:
    private static final String LOG_TAG = AlchemyPresenter.class.getSimpleName();

    // REPOSITORY VARIABLES:
    @Inject AlchemyRepository mRepository;

    // RX VARIABLES:
    @NonNull private CompositeDisposable mDisposables;

    // VIEW VARIABLES:
    private AlchemyContract.View mView;

    /** CONSTRUCTOR METHODS ____________________________________________________________________ **/

    public AlchemyPresenter(AlchemyContract.View view) {
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
    public HashSet<String> getIngredientList() {
        return mRepository.getIngredientList();
    }

    @Override
    public void setIngredientList(HashSet<String> ingredientList) {
        mRepository.setIngredientList(ingredientList);
    }

    @Override
    public String getSelectedIngredient(int buttonId) {
        return mRepository.getSelectedIngredient(buttonId);
    }

    @Override
    public String[] getSelectedIngredientList() {
        return mRepository.getSelectedIngredientList();
    }

    @Override
    public List<AlchenomiconRecipe> getRecipeResults() {
        return mRepository.getRecipeResults();
    }

    @Override
    public void setSelectedIngredient(String ingredient, int buttonId) {
        mRepository.setSelectedIngredient(ingredient, buttonId);

        if (!ingredient.equals(AlchenomiconConstants.NULL_IDENTIFIER)) {
            int ingredientResource = AlchenomiconImageUtils.getItemImage(ingredient);

            mView.showSelectedIngredient(ingredientResource, buttonId);
            mView.updateSelectedIngredientText(buttonId);
            mView.showNoResults(false);
            mView.showRecipeList(false);
            mView.showProgressBar(true);

            // Queries the database for all the recipes that contain the selected ingredients.
            mRepository.loadRecipes(new AlchemyPresenterListener() {
                @Override
                public void onAlchemyListLoaded() {
                    mView.showProgressBar(false);

                    if (mRepository.getRecipeResults().size() > 0) {
                        mView.showRecipeList(true);
                    } else {
                        mView.showNoResults(true);
                    }
                }
            });
        }
    }

    @Override
    public void setSelectedIngredientList(String[] ingredientList) {
        mRepository.setSelectedIngredientList(ingredientList);
    }

    @Override
    public void setRecipeResults(List<AlchenomiconRecipe> recipeResults) {
        mRepository.setRecipeResults(recipeResults);

        if (mRepository.getRecipeResults().size() > 0) {
            mView.showNoResults(false);
            mView.showRecipeList(true);
        } else {
            mView.showRecipeList(false);
            mView.showNoResults(true);
        }
    }

    @Override
    public void onIngredientButtonClicked(final int buttonId) {

        // Queries the database to retrieve all available ingredients.
        if (mRepository.getIngredientList() == null) {
            mRepository.loadAllIngredients(new AlchemyPresenterListener() {
                @Override
                public void onAlchemyListLoaded() {
                    mView.showIngredientDialog(buttonId);
                }
            });
        } else {
            mView.showIngredientDialog(buttonId);
        }
    }

    @Override
    public void onClearButtonClicked() {
        mRepository.setSelectedIngredientList(null);
        mRepository.setRecipeResults(null);

        mView.clearSelectedIngredients();
        mView.updateSelectedIngredientText(INGREDIENT_BUTTON_1_ID);
        mView.updateSelectedIngredientText(INGREDIENT_BUTTON_2_ID);
        mView.updateSelectedIngredientText(INGREDIENT_BUTTON_3_ID);
        mView.showRecipeList(false);
        mView.showNoResults(false);
    }
}
