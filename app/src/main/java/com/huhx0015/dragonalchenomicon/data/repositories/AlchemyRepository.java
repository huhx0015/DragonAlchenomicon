package com.huhx0015.dragonalchenomicon.data.repositories;

import android.util.Log;
import com.huhx0015.dragonalchenomicon.application.AlchenomiconApplication;
import com.huhx0015.dragonalchenomicon.constants.AlchenomiconConstants;
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

    // CONSTANT VARIABLES:
    private static final int SELECTED_INGREDIENT_SIZE = 3;

    // DATA VARIABLES:
    private HashSet<String> mIngredientList;
    private String[] mSelectedIngredientList;

    // DATABASE VARIABLES:
    @Inject AlchenomiconDatabaseHelper mDatabase;

    // LOGGING VARIABLES:
    private static final String LOG_TAG = AlchemyRepository.class.getSimpleName();

    /** CONSTRUCTOR METHODS ____________________________________________________________________ **/

    @Inject
    public AlchemyRepository() {
        AlchenomiconApplication.getInstance().getAppComponent().inject(this);
    }

    /** INIT METHODS ___________________________________________________________________________ **/

    private void initSelectedList() {
        if (mSelectedIngredientList == null) {
            mSelectedIngredientList = new String[SELECTED_INGREDIENT_SIZE];

            // Initializes the selected ingredient list values with "NULL" identifiers.
            int position = 0;
            while (position < mSelectedIngredientList.length) {
                mSelectedIngredientList[position++] = AlchenomiconConstants.NULL_IDENTIFIER;
            }
        }
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
    public String getSelectedIngredient(int buttonId) {
        initSelectedList();
        return mSelectedIngredientList[buttonId];
    }

    @Override
    public String[] getSelectedIngredientList() {
        initSelectedList();
        return mSelectedIngredientList;
    }

    @Override
    public void setIngredientList(HashSet<String> ingredientList) {
        this.mIngredientList = ingredientList;
    }

    @Override
    public void setSelectedIngredient(String ingredient, int buttonId) {
        initSelectedList();
        mSelectedIngredientList[buttonId] = ingredient;
    }

    @Override
    public void setSelectedIngredientList(String[] ingredientList) {
        this.mSelectedIngredientList = ingredientList;
    }
}
