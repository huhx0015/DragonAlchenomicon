package com.huhx0015.dragonalchenomicon.presenters;

import android.util.Log;
import com.huhx0015.dragonalchenomicon.application.AlchenomiconApplication;
import com.huhx0015.dragonalchenomicon.contracts.AlchenomiconContract;
import com.huhx0015.dragonalchenomicon.data.AlchenomiconDatabaseHelper;
import java.util.HashSet;
import javax.inject.Inject;

/**
 * Created by Michael Yoon Huh on 5/7/2017.
 */

public class AlchenomiconPresenter implements AlchenomiconContract.Presenter {

    private AlchenomiconContract.View mAlchenomiconView;

    private static final String LOG_TAG = AlchenomiconPresenter.class.getSimpleName();

    @Inject AlchenomiconDatabaseHelper mDatabase;


    public AlchenomiconPresenter(AlchenomiconContract.View view) {
        this.mAlchenomiconView = view;
        this.mAlchenomiconView.setPresenter(this);
        AlchenomiconApplication.getInstance().getAppComponent().inject(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void onIngredientButtonClicked(int buttonId) {

        HashSet<String> ingredientList = mDatabase.getAllIngredients();

        for (String ingredient : ingredientList) {
            Log.d(LOG_TAG, "onIngredientButtonClicked(): Ingredient: " + ingredient);
        }

        mAlchenomiconView.showIngredientDialog();

        // SHOW DIALOG WITH ITEM LIST.
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
