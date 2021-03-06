package com.huhx0015.dragonalchenomicon.presenters;

import android.support.annotation.NonNull;
import android.util.Log;
import com.huhx0015.dragonalchenomicon.application.AlchenomiconApplication;
import com.huhx0015.dragonalchenomicon.model.contracts.IngredientPickerContract;
import com.huhx0015.dragonalchenomicon.model.repositories.IngredientPickerRepository;
import javax.inject.Inject;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Michael Yoon Huh on 5/14/2017.
 */

public class IngredientPickerPresenter implements IngredientPickerContract.Presenter {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // LOGGING VARIABLES:
    private static final String LOG_TAG = IngredientPickerPresenter.class.getSimpleName();

    // REPOSITORY VARIABLES:
    @Inject IngredientPickerRepository mRepository;

    // RX VARIABLES:
    @NonNull private CompositeDisposable mDisposables;

    // VIEW VARIABLES:
    private IngredientPickerContract.View mView;

    /** CONSTRUCTOR METHODS ____________________________________________________________________ **/

    public IngredientPickerPresenter(IngredientPickerContract.View view) {
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
    public IngredientPickerRepository getRepository() {
        return mRepository;
    }

    @Override
    public void loadIngredientList() {
        mView.showIngredientList();
    }

    @Override
    public void onIngredientSelected(String ingredient) {
        mView.dismissPickerDialog(ingredient);
    }
}
