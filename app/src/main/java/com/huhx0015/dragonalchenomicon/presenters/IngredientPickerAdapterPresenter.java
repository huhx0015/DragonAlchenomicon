package com.huhx0015.dragonalchenomicon.presenters;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import com.huhx0015.dragonalchenomicon.view.adapters.IngredientPickerDialogAdapter;
import com.huhx0015.dragonalchenomicon.application.AlchenomiconApplication;
import com.huhx0015.dragonalchenomicon.model.contracts.IngredientPickerAdapterContract;
import com.huhx0015.dragonalchenomicon.model.repositories.IngredientPickerAdapterRepository;
import javax.inject.Inject;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Michael Yoon Huh on 5/15/2017.
 */

public class IngredientPickerAdapterPresenter implements IngredientPickerAdapterContract.Presenter {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // LOGGING VARIABLES:
    private static final String LOG_TAG = IngredientPickerAdapterPresenter.class.getSimpleName();

    // REPOSITORY VARIABLES:
    @Inject IngredientPickerAdapterRepository mRepository;

    // RX VARIABLES:
    @NonNull private CompositeDisposable mDisposables;

    // VIEW VARIABLES:
    private IngredientPickerAdapterContract.View mView;

    /** CONSTRUCTOR METHODS ____________________________________________________________________ **/

    public IngredientPickerAdapterPresenter(IngredientPickerAdapterContract.View view) {
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
    public IngredientPickerAdapterRepository getRepository() {
        return mRepository;
    }

    @Override
    public void onIngredientClicked(int position) {
        String ingredient = mRepository.getIngredientList().get(position);
        mView.dismissIngredientPicker(ingredient);
    }

    @Override
    public void setIngredientRow(IngredientPickerDialogAdapter.IngredientPickerViewHolder holder, int position) {
        String ingredient = mRepository.getIngredientList().get(position);
        mView.showIngredientRow(holder, ingredient);
    }

    @Override
    public void setIngredientName(String ingredient, TextView view) {
        mView.showIngredientName(ingredient, view);
    }

    @Override
    public void setIngredientImage(int resource, ImageView view) {
        mView.showIngredientImage(resource, view);
    }

    @Override
    public void clearIngredientImage(ImageView view) {
        mView.clearIngredientImage(view);
    }
}
