package com.huhx0015.dragonalchenomicon.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.huhx0015.dragonalchenomicon.injections.annotations.ApplicationContext;
import com.huhx0015.dragonalchenomicon.constants.AlchenomiconConstants;
import com.huhx0015.dragonalchenomicon.view.listeners.AlchenomiconDatabaseListener;
import com.huhx0015.dragonalchenomicon.model.objects.AlchenomiconRecipe;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Michael Yoon Huh on 4/25/2017.
 */

@Singleton
public class AlchenomiconDatabaseHelper extends SQLiteAssetHelper {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // DATABASE VARIABLES:
    private static final String DATABASE_NAME = "dq8_alchenomicon_table.db";
    private static final int DATABASE_VERSION = 1;

    // LOGGING VARIABLES:
    private static final String LOG_TAG = AlchenomiconDatabaseHelper.class.getSimpleName();

    // TABLE ATTRIBUTE VARIABLES:
    private static final String TABLE_DQ8_RECIPE = "DQ8_ALCHEMY_TABLE";
    private static final String KEY_ROWID = "KEY_ROWID";
    private static final String KEY_ITEM = "KEY_ITEM";
    private static final String KEY_CATEGORY = "KEY_CATEGORY";
    private static final String KEY_DESC = "KEY_DESC";
    private static final String KEY_REC1 = "KEY_REC1";
    private static final String KEY_REC2 = "KEY_REC2";
    private static final String KEY_REC3 = "KEY_REC3";
    private static final String KEY_ATT1 = "KEY_ATT1";
    private static final String KEY_ATT2 = "KEY_ATT2";
    private static final String KEY_ATT3 = "KEY_ATT3";

    // TABLE COLUMN VARIABLES:
    private static final String[] TABLE_ALL_COLUMNS = {
            KEY_ROWID,
            KEY_ITEM,
            KEY_CATEGORY,
            KEY_DESC,
            KEY_REC1,
            KEY_REC2,
            KEY_REC3,
            KEY_ATT1,
            KEY_ATT2,
            KEY_ATT3
    };

    /** CONSTRUCTOR METHODS ____________________________________________________________________ **/

    @Inject
    public AlchenomiconDatabaseHelper(@ApplicationContext Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /** DATABASE ACCESS METHODS ________________________________________________________________ **/

    public synchronized void getAllIngredients(AlchenomiconDatabaseListener.IngredientQueryListener listener) {

        HashSet<String> ingredientList = null;
        Cursor cursor = readDatabase();

        // Reads the database for all recipes and adds it to the list of recipes.
        try {
            if (cursor.moveToFirst()) {
                do {
                    if (ingredientList == null) {
                        ingredientList = new HashSet<>();
                    }

                    ingredientList.add(cursor.getString(cursor.getColumnIndex(KEY_REC1)));
                    ingredientList.add(cursor.getString(cursor.getColumnIndex(KEY_REC2)));
                    ingredientList.add(cursor.getString(cursor.getColumnIndex(KEY_REC3)));

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "loadAllIngredients(): An error occurred while attempting to query the database for ingredients: " + e.getLocalizedMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }

            if (ingredientList != null) {
                ingredientList.remove(AlchenomiconConstants.NULL_IDENTIFIER); // Removes any NULL ingredients from list.
                listener.onQueryFinished(ingredientList);
            } else {
                Log.e(LOG_TAG, "loadAllIngredients(): Failed to retrieve any ingredients from the database.");
            }
        }
    }

    public synchronized void getAllRecipes(AlchenomiconDatabaseListener.RecipeQueryListener listener) {

        List<AlchenomiconRecipe> recipeList = null;
        Cursor cursor = readDatabase();

        // Reads the database for all recipes and adds it to the list of recipes.
        try {
            if (cursor.moveToFirst()) {
                do {
                    if (recipeList == null) {
                        recipeList = new ArrayList<>();
                    }

                    AlchenomiconRecipe recipe = getRecipeRow(cursor);
                    recipeList.add(recipe);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "getAllRecipes(): An error occurred while trying to get recipes from database: " + e.getLocalizedMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            if (recipeList != null) {
                listener.onQueryFinished(recipeList);
            } else {
                Log.e(LOG_TAG, "getAllRecipes(): Failed to retrieve any recipes from the database.");
            }
        }
    }

    public synchronized void getRecipesContainingIngredients(List<String> selectedIngredients,
                                                             AlchenomiconDatabaseListener.RecipeQueryListener listener) {
        List<AlchenomiconRecipe> recipeList = null;
        Cursor cursor = readDatabase();

        // Reads the database for all recipes and adds it to the list of recipes.
        try {
            if (cursor.moveToFirst()) {
                do {
                    if (recipeList == null) {
                        recipeList = new ArrayList<>();
                    }

                    LinkedList<String> recipeIngredientList = new LinkedList<>();
                    recipeIngredientList.add(cursor.getString(cursor.getColumnIndex(KEY_REC1)));
                    recipeIngredientList.add(cursor.getString(cursor.getColumnIndex(KEY_REC2)));
                    recipeIngredientList.add(cursor.getString(cursor.getColumnIndex(KEY_REC3)));

                    int ingredientCount = 0;
                    while (!recipeIngredientList.isEmpty() && ingredientCount < selectedIngredients.size()) {
                        String ingredient = recipeIngredientList.getFirst();
                        String selectedIngredient = selectedIngredients.get(ingredientCount);

                        if (ingredient.equals(selectedIngredient)) {
                            ingredientCount++;
                        }

                        recipeIngredientList.removeFirst();
                    }

                    // If the recipe contains the selected ingredients, the recipe is added to the
                    // list.
                    if (ingredientCount == selectedIngredients.size()) {
                        AlchenomiconRecipe recipe = getRecipeRow(cursor);
                        recipeList.add(recipe);
                    }

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "getRecipesContainingIngredients(): An error occurred while trying to get recipes from database: " + e.getLocalizedMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
            if (recipeList != null) {
                listener.onQueryFinished(recipeList);
            } else {
                Log.e(LOG_TAG, "getRecipesContainingIngredients(): Failed to retrieve any recipes from the database.");
            }
        }
    }

    private AlchenomiconRecipe getRecipeRow(Cursor cursor) {
        AlchenomiconRecipe recipe = new AlchenomiconRecipe();

        recipe.recipeId = cursor.getInt(cursor.getColumnIndex(KEY_ROWID)); // ROW_ID
        recipe.recipeName = cursor.getString(cursor.getColumnIndex(KEY_ITEM)); // ITEM
        recipe.recipeCategoryId = cursor.getInt(cursor.getColumnIndex(KEY_CATEGORY)); // CATEGORY
        recipe.recipeDescription = cursor.getString(cursor.getColumnIndex(KEY_DESC)); // DESCRIPTION

        // RECIPE LIST:
        List<String> recipeIngredientList = new ArrayList<>();
        recipeIngredientList.add(cursor.getString(cursor.getColumnIndex(KEY_REC1)));
        recipeIngredientList.add(cursor.getString(cursor.getColumnIndex(KEY_REC2)));
        recipeIngredientList.add(cursor.getString(cursor.getColumnIndex(KEY_REC3)));
        recipe.recipeIngredientList = recipeIngredientList;

        // ATTRIBUTE LIST:
        List<String> recipeAttributeList = new ArrayList<>();
        recipeAttributeList.add(cursor.getString(cursor.getColumnIndex(KEY_ATT1)));
        recipeAttributeList.add(cursor.getString(cursor.getColumnIndex(KEY_ATT2)));
        recipeAttributeList.add(cursor.getString(cursor.getColumnIndex(KEY_ATT3)));
        recipe.recipeAttributeList = recipeAttributeList;

        return recipe;
    }

    private Cursor readDatabase() {

        // "getReadableDatabase()" and "getWriteableDatabase()" return the same object (except under
        // low disk space scenarios)
        SQLiteDatabase database = getReadableDatabase();

        // Prepares the cursor.
        return database.query(TABLE_DQ8_RECIPE, TABLE_ALL_COLUMNS, null, null, null, null, null);
    }
}