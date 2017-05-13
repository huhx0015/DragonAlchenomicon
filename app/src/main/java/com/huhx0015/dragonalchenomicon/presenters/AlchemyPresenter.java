package com.huhx0015.dragonalchenomicon.presenters;

import android.util.Log;
import com.huhx0015.dragonalchenomicon.application.AlchenomiconApplication;
import com.huhx0015.dragonalchenomicon.contracts.AlchemyContract;
import com.huhx0015.dragonalchenomicon.data.AlchenomiconDatabaseHelper;
import com.huhx0015.dragonalchenomicon.interfaces.AlchenomiconDatabaseListener;

import java.util.HashSet;
import javax.inject.Inject;

/**
 * Created by Michael Yoon Huh on 5/11/2017.
 */

public class AlchemyPresenter implements AlchemyContract.Presenter {

    private AlchemyContract.View mAlchemyView;

    private static final String LOG_TAG = AlchemyPresenter.class.getSimpleName();

    @Inject AlchenomiconDatabaseHelper mDatabase;

    public AlchemyPresenter(AlchemyContract.View view) {
        this.mAlchemyView = view;
        this.mAlchemyView.setPresenter(this);
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

        mDatabase.getAllIngredients(new AlchenomiconDatabaseListener.IngredientQueryListener() {
            @Override
            public void onQueryFinished(HashSet<String> ingredientList) {
                mAlchemyView.showIngredientDialog(ingredientList);

                for (String ingredient : ingredientList) {
                    Log.d(LOG_TAG, "onIngredientButtonClicked(): Ingredient: " + ingredient);
                }

            }
        });
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
