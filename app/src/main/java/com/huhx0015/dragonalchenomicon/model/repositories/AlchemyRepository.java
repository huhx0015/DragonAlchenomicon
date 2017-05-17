package com.huhx0015.dragonalchenomicon.model.repositories;

import android.util.Log;
import com.huhx0015.dragonalchenomicon.application.AlchenomiconApplication;
import com.huhx0015.dragonalchenomicon.constants.AlchenomiconConstants;
import com.huhx0015.dragonalchenomicon.model.contracts.AlchemyContract;
import com.huhx0015.dragonalchenomicon.database.AlchenomiconDatabaseHelper;
import com.huhx0015.dragonalchenomicon.view.listeners.AlchemyPresenterListener;
import com.huhx0015.dragonalchenomicon.view.listeners.AlchenomiconDatabaseListener;
import com.huhx0015.dragonalchenomicon.model.objects.AlchenomiconRecipe;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
    private List<AlchenomiconRecipe> mRecipeResultList;
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
    public void loadAllIngredients(final AlchemyPresenterListener listener) {
        Thread databaseThread = new Thread(new Runnable() {
            @Override
            public void run() {
                mDatabase.getAllIngredients(new AlchenomiconDatabaseListener.IngredientQueryListener() {
                    @Override
                    public void onQueryFinished(HashSet<String> ingredientList) {
                        Log.d(LOG_TAG, "onQueryFinished(): Query for ingredient list has finished.");
                        mIngredientList = ingredientList;
                        listener.onAlchemyListLoaded();
                    }
                });
            }
        });
        databaseThread.start();
    }

    @Override
    public void loadRecipes(final AlchemyPresenterListener listener) {
        Thread databaseThread = new Thread(new Runnable() {
            @Override
            public void run() {

                // Removes NULL ingredients from the selected ingredient list before querying the
                // database for recipes containing the ingredients from the list.
                List<String> selectedIngredients = new ArrayList<>();
                for (String ingredient : mSelectedIngredientList) {
                    if (!ingredient.equals(AlchenomiconConstants.NULL_IDENTIFIER)) {
                        selectedIngredients.add(ingredient);
                    }
                }

                mDatabase.getRecipesContainingIngredients(selectedIngredients, new AlchenomiconDatabaseListener.RecipeQueryListener() {
                    @Override
                    public void onQueryFinished(List<AlchenomiconRecipe> recipeList) {
                        Log.d(LOG_TAG, "onQueryFinished(): Query for recipe list has finished.");
                        mRecipeResultList = recipeList;
                        listener.onAlchemyListLoaded();
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
    public List<AlchenomiconRecipe> getRecipeResults() {
        return mRecipeResultList;
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
        initSelectedList();
    }

    @Override
    public void setRecipeResults(List<AlchenomiconRecipe> recipeResults) {
        this.mRecipeResultList = recipeResults;
    }
}
