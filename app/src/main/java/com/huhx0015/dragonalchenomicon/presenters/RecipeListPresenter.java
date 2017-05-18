package com.huhx0015.dragonalchenomicon.presenters;

import android.util.Log;
import com.huhx0015.dragonalchenomicon.application.AlchenomiconApplication;
import com.huhx0015.dragonalchenomicon.model.contracts.RecipeListContract;
import com.huhx0015.dragonalchenomicon.model.repositories.RecipeListRepository;
import com.huhx0015.dragonalchenomicon.model.objects.AlchenomiconRecipe;
import com.huhx0015.dragonalchenomicon.view.listeners.ObserverCompletedListener;
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

        // Loads the recipe list if it has not been loaded.
        onLoadRecipeList();
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
        if (mRepository.getRecipeList() == null) {
            mView.showProgressBar(true); // Displays the progress bar.

            loadAllRecipes(() -> {
                mView.showProgressBar(false); // Hides the progress bar.
                mView.showRecipeList(); // Signals the view to make the recipe RecyclerView visible.
            });
        } else {
            mView.showRecipeList(); // Signals the view to make the recipe RecyclerView visible.
        }
    }

    /** OBSERVABLE METHODS _____________________________________________________________________ **/

    // loadAllRecipes(): Queries the database to retrieve all available recipes.
    private void loadAllRecipes(ObserverCompletedListener listener) {
        Observable<List<AlchenomiconRecipe>> recipeListObservable = mRepository.loadAllRecipes();
        recipeListObservable.subscribeOn(Schedulers.io())
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
                        Log.e(LOG_TAG, "loadAllRecipes(): An error occurred while loading ingredient list: " + e.getLocalizedMessage());
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
