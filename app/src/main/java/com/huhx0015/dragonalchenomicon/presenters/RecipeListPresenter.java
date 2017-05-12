package com.huhx0015.dragonalchenomicon.presenters;

import com.huhx0015.dragonalchenomicon.application.AlchenomiconApplication;
import com.huhx0015.dragonalchenomicon.contracts.RecipeListContract;
import com.huhx0015.dragonalchenomicon.data.AlchenomiconDatabaseHelper;
import javax.inject.Inject;

/**
 * Created by Michael Yoon Huh on 5/11/2017.
 */

public class RecipeListPresenter implements RecipeListContract.Presenter {

    private RecipeListContract.View mRecipeListView;

    private static final String LOG_TAG = RecipeListPresenter.class.getSimpleName();

    @Inject AlchenomiconDatabaseHelper mDatabase;

    public RecipeListPresenter(RecipeListContract.View view) {
        this.mRecipeListView = view;
        this.mRecipeListView.setPresenter(this);
        AlchenomiconApplication.getInstance().getAppComponent().inject(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void onRecipeListLoad() {
        mRecipeListView.showRecipeList();
    }
}
