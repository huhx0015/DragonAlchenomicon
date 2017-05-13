package com.huhx0015.dragonalchenomicon.presenters;

import android.support.annotation.NonNull;
import android.util.Log;
import com.huhx0015.dragonalchenomicon.contracts.AlchenomiconContract;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Michael Yoon Huh on 5/7/2017.
 */

public class AlchenomiconPresenter implements AlchenomiconContract.Presenter {

    private AlchenomiconContract.View mAlchenomiconView;

    private static final String LOG_TAG = AlchenomiconPresenter.class.getSimpleName();

    // RX VARIABLES:
    @NonNull
    private CompositeDisposable mDisposables;

    public AlchenomiconPresenter(AlchenomiconContract.View view) {
        this.mAlchenomiconView = view;
        this.mAlchenomiconView.setPresenter(this);
    }

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

    }

    @Override
    public void onAlchemizeButtonClicked() {

    }

    @Override
    public void onMusicButtonClicked() {

    }

    @Override
    public void onClearButtonClicked() {

    }
}
