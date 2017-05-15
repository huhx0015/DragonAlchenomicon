package com.huhx0015.dragonalchenomicon.data.repositories;

import android.util.Log;
import com.huhx0015.dragonalchenomicon.application.AlchenomiconApplication;
import com.huhx0015.dragonalchenomicon.contracts.AlchemyContract;
import com.huhx0015.dragonalchenomicon.data.database.AlchenomiconDatabaseHelper;
import com.huhx0015.dragonalchenomicon.interfaces.AlchemyPresenterListener;
import com.huhx0015.dragonalchenomicon.interfaces.AlchenomiconDatabaseListener;
import java.util.HashSet;
import javax.inject.Inject;

/**
 * Created by Michael Yoon Huh on 5/13/2017.
 */

public class AlchemyRepository implements AlchemyContract.Repository {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // DATA VARIABLES
    private HashSet<String> mIngredientList;

    // DATABASE VARIABLES:
    @Inject AlchenomiconDatabaseHelper mDatabase;

    // LOGGING VARIABLES:
    private static final String LOG_TAG = AlchemyRepository.class.getSimpleName();

    /** CONSTRUCTOR METHODS ____________________________________________________________________ **/

    @Inject
    public AlchemyRepository() {
        AlchenomiconApplication.getInstance().getAppComponent().inject(this);
    }

    /** REPOSITORY METHODS _____________________________________________________________________ **/

    @Override
    public void getAllIngredients(final AlchemyPresenterListener listener) {
        Thread databaseThread = new Thread(new Runnable() {
            @Override
            public void run() {
                mDatabase.getAllIngredients(new AlchenomiconDatabaseListener.IngredientQueryListener() {
                    @Override
                    public void onQueryFinished(HashSet<String> ingredientList) {
                        Log.d(LOG_TAG, "onQueryFinished(): Query for ingredient list has finished.");
                        mIngredientList = ingredientList;
                        listener.onIngredientListLoaded();
                    }
                });
            }
        });
        databaseThread.start();
    }

    @Override
    public HashSet<String> getIngredientList() {
        return mIngredientList;
    }

    @Override
    public void setIngredientList(HashSet<String> ingredientList) {
        this.mIngredientList = ingredientList;
    }
}
