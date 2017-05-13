package com.huhx0015.dragonalchenomicon.data.repositories;

import com.huhx0015.dragonalchenomicon.application.AlchenomiconApplication;
import com.huhx0015.dragonalchenomicon.contracts.AlchemyContract;
import com.huhx0015.dragonalchenomicon.data.database.AlchenomiconDatabaseHelper;
import com.huhx0015.dragonalchenomicon.interfaces.AlchenomiconDatabaseListener;
import javax.inject.Inject;

/**
 * Created by Michael Yoon Huh on 5/13/2017.
 */

public class AlchemyRepository implements AlchemyContract.Repository {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // DATABASE VARIABLES:
    @Inject AlchenomiconDatabaseHelper mDatabase;

    /** CONSTRUCTOR METHODS ____________________________________________________________________ **/

    @Inject
    public AlchemyRepository() {
        AlchenomiconApplication.getInstance().getAppComponent().inject(this);
    }

    /** REPOSITORY METHODS _____________________________________________________________________ **/

    @Override
    public void getAllIngredients(AlchenomiconDatabaseListener.IngredientQueryListener listener) {
        mDatabase.getAllIngredients(listener);
    }
}
