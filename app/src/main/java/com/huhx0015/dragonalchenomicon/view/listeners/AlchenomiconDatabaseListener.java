package com.huhx0015.dragonalchenomicon.view.listeners;

import com.huhx0015.dragonalchenomicon.model.objects.AlchenomiconRecipe;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Michael Yoon Huh on 5/13/2017.
 */

public interface AlchenomiconDatabaseListener {

    interface IngredientQueryListener {
        void onQueryFinished(HashSet<String> ingredientList);
    }

    interface RecipeQueryListener {
        void onQueryFinished(List<AlchenomiconRecipe> recipeList);
    }

}
