package com.huhx0015.dragonalchenomicon.presenters;

import android.util.Log;
import com.huhx0015.dragonalchenomicon.application.AlchenomiconApplication;
import com.huhx0015.dragonalchenomicon.constants.AlchenomiconConstants;
import com.huhx0015.dragonalchenomicon.model.contracts.AlchemyContract;
import com.huhx0015.dragonalchenomicon.model.repositories.AlchemyRepository;
import com.huhx0015.dragonalchenomicon.model.objects.AlchenomiconRecipe;
import com.huhx0015.dragonalchenomicon.utils.AlchenomiconImageUtils;
import com.huhx0015.dragonalchenomicon.view.listeners.ObserverCompletedListener;
import java.util.HashSet;
import java.util.List;
import javax.inject.Inject;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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

        // Loads the ingredient list if it has not been loaded.
        if (mRepository.getIngredientList() == null) {
            loadAllIngredients(null);
        }
    }

    @Override
    public void unsubscribe() {
        Log.d(LOG_TAG, "unsubscribe(): Presenter unsubscribed.");
        mDisposables.clear();
    }

    @Override
    public AlchemyRepository getRepository() {
        return mRepository;
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

            loadRecipes(() -> {
                mView.showProgressBar(false);

                if (mRepository.getRecipeResults().size() > 0) {
                    mView.showRecipeList(true);
                } else {
                    mView.showNoResults(true);
                }
            });
        }
    }

    @Override
    public void setSelectedIngredientList(String[] ingredientList) {
        mRepository.setSelectedIngredientList(ingredientList);

        int buttonId = 0;
        for (String ingredient : ingredientList) {
            if (!ingredient.equals(AlchenomiconConstants.NULL_IDENTIFIER)) {
                int ingredientResource = AlchenomiconImageUtils.getItemImage(ingredient);
                mView.showSelectedIngredient(ingredientResource, buttonId);
                mView.updateSelectedIngredientText(buttonId);
            }
            buttonId++;
        }
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
        if (mRepository.getIngredientList() == null) {
            loadAllIngredients(() -> {
                mView.showIngredientDialog(buttonId);
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

    /** OBSERVABLE METHODS _____________________________________________________________________ **/

    // loadAllIngredients(): Queries the database to retrieve all available ingredients.
    private void loadAllIngredients(ObserverCompletedListener listener) {
        Observable<HashSet<String>> ingredientListObservable = mRepository.loadAllIngredients();
        ingredientListObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HashSet<String>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mDisposables.add(d); // Adds the disposable to the CompositeDisposables.
                    }

                    @Override
                    public void onNext(@NonNull HashSet<String> strings) {}

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(LOG_TAG, "loadAllIngredients(): An error occurred while loading ingredient list: " + e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {
                        if (listener != null) {
                            listener.onObserverCompleted();
                        }
                    }
                });
    }

    // loadRecipes(): Queries the database for all the recipes that contain the selected ingredients.
    private void loadRecipes(ObserverCompletedListener listener) {
        Observable<List<AlchenomiconRecipe>> recipeResultsObservable = mRepository.loadRecipes();
        recipeResultsObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<AlchenomiconRecipe>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        mDisposables.add(d); // Adds the disposable to the CompositeDisposables.
                    }

                    @Override
                    public void onNext(@NonNull List<AlchenomiconRecipe> alchenomiconRecipes) {}

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(LOG_TAG, "loadRecipes(): An error occurred while loading the recipe results: " + e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {
                        if (listener != null) {
                            listener.onObserverCompleted();
                        }
                    }
                });
    }
}
