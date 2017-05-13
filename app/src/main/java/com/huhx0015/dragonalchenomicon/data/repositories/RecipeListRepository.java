package com.huhx0015.dragonalchenomicon.data.repositories;

import com.huhx0015.dragonalchenomicon.application.AlchenomiconApplication;
import com.huhx0015.dragonalchenomicon.contracts.RecipeListContract;
import com.huhx0015.dragonalchenomicon.data.database.AlchenomiconDatabaseHelper;
import com.huhx0015.dragonalchenomicon.interfaces.AlchenomiconDatabaseListener;
import javax.inject.Inject;

/**
 * Created by Michael Yoon Huh on 5/13/2017.
 */

public class RecipeListRepository implements RecipeListContract.Repository {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // DATABASE VARIABLES:
    @Inject AlchenomiconDatabaseHelper mDatabase;

    /** CONSTRUCTOR METHODS ____________________________________________________________________ **/

    @Inject
    public RecipeListRepository() {
        AlchenomiconApplication.getInstance().getAppComponent().inject(this);
    }

    /** REPOSITORY METHODS _____________________________________________________________________ **/

    @Override
    public void getAllRecipes(AlchenomiconDatabaseListener.RecipeQueryListener listener) {
        mDatabase.getAllRecipes(listener);
    }
}
