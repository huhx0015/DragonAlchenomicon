package com.huhx0015.dragonalchenomicon.presenters;

import android.support.annotation.NonNull;
import android.util.Log;
import com.huhx0015.dragonalchenomicon.application.AlchenomiconApplication;
import com.huhx0015.dragonalchenomicon.contracts.AlchemyContract;
import com.huhx0015.dragonalchenomicon.data.repositories.AlchemyRepository;
import com.huhx0015.dragonalchenomicon.interfaces.AlchenomiconDatabaseListener;
import java.util.HashSet;
import javax.inject.Inject;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Michael Yoon Huh on 5/11/2017.
 */

public class AlchemyPresenter implements AlchemyContract.Presenter {

    /** CLASS VARIABLES ________________________________________________________________________ **/

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
    public void onIngredientButtonClicked(int buttonId) {

        // Queries the database to retrieve all available ingredients.
        mRepository.getAllIngredients(new AlchenomiconDatabaseListener.IngredientQueryListener() {
            @Override
            public void onQueryFinished(HashSet<String> ingredientList) {
                Log.d(LOG_TAG, "onQueryFinished(): Query for ingredient list has finished.");
                mView.showIngredientDialog(ingredientList);
            }
        });
    }
}
