package com.huhx0015.dragonalchenomicon.model.repositories;

import com.huhx0015.dragonalchenomicon.contracts.RecipeListAdapterContract;
import com.huhx0015.dragonalchenomicon.model.objects.AlchenomiconRecipe;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by Michael Yoon Huh on 5/14/2017.
 */

public class RecipeListAdapterRepository implements RecipeListAdapterContract.Repository {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // RECIPE LIST VARIABLES:
    private List<AlchenomiconRecipe> mRecipeList;
    private int mRecipeCount;

    /** CONSTRUCTOR METHODS ____________________________________________________________________ **/

    @Inject
    public RecipeListAdapterRepository() {}

    /** REPOSITORY METHODS _____________________________________________________________________ **/

    @Override
    public int getRecipeCount() {
        return mRecipeCount;
    }

    @Override
    public List<AlchenomiconRecipe> getRecipeList() {
        return mRecipeList;
    }

    @Override
    public void setRecipeList(List<AlchenomiconRecipe> recipeList) {
        this.mRecipeList = recipeList;
        this.mRecipeCount = recipeList.size();
    }
}
