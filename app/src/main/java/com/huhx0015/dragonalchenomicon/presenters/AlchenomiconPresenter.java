package com.huhx0015.dragonalchenomicon.presenters;

import android.support.annotation.NonNull;
import android.util.Log;
import com.huhx0015.dragonalchenomicon.application.AlchenomiconApplication;
import com.huhx0015.dragonalchenomicon.model.contracts.AlchenomiconContract;
import com.huhx0015.dragonalchenomicon.model.repositories.AlchenomiconRepository;
import javax.inject.Inject;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Michael Yoon Huh on 5/7/2017.
 */

public class AlchenomiconPresenter implements AlchenomiconContract.Presenter {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // LOGGING VARIABLES:
    private static final String LOG_TAG = AlchenomiconPresenter.class.getSimpleName();

    // REPOSITORY VARIABLES:
    @Inject AlchenomiconRepository mRepository;

    // RX VARIABLES:
    @NonNull
    private CompositeDisposable mDisposables;

    // VIEW VARIABLES:
    private AlchenomiconContract.View mView;

    /** CONSTRUCTOR METHODS ____________________________________________________________________ **/

    public AlchenomiconPresenter(AlchenomiconContract.View view) {
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
    public int getCurrentPage() {
        return mRepository.getCurrentPage();
    }

    @Override
    public void setCurrentPage(int page) {
        mRepository.setCurrentPage(page);
        mView.updateViewPager(page);
        mView.updateBottomNavigationSelected(page);
    }

    @Override
    public void onPageSelected(int page) {
        mRepository.setCurrentPage(page);
        mView.updateBottomNavigationSelected(page);
    }

    @Override
    public void onBottomNavigationClicked(int position) {
        mView.updateViewPager(position);
    }

    @Override
    public void onInitListeners() {
        mView.initBottomNavigationView();
        mView.initViewPager();
    }
}
