package com.huhx0015.dragonalchenomicon.model;

import java.util.List;

/**
 * Created by Michael Yoon Huh on 5/7/2017.
 *
 * Tip: Avoid Internal Getters/Setters on Android.
 * Reference: https://developer.android.com/training/articles/perf-tips.html#GettersSetters
 */

public class AlchenomiconRecipe {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // RECIPE VARIABLES:
    public int recipeId;
    public int recipeCategoryId;
    public List<String> recipeIngredientList;
    public List<String> recipeAttributeList;
    public String recipeName;
    public String recipeDescription;
}
