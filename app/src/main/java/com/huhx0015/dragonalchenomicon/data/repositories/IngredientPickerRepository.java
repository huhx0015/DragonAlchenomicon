package com.huhx0015.dragonalchenomicon.data.repositories;

import com.huhx0015.dragonalchenomicon.contracts.IngredientPickerContract;
import java.util.HashSet;
import javax.inject.Inject;

/**
 * Created by Michael Yoon Huh on 5/15/2017.
 */

public class IngredientPickerRepository implements IngredientPickerContract.Repository {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // INGREDIENT LIST VARIABLES:
    private HashSet<String> mIngredientList;

    /** CONSTRUCTOR METHODS ____________________________________________________________________ **/

    @Inject
    public IngredientPickerRepository() {}

    /** REPOSITORY METHODS _____________________________________________________________________ **/

    @Override
    public HashSet<String> getIngredientList() {
        return mIngredientList;
    }

    @Override
    public void setIngredientList(HashSet<String> ingredientList) {
        this.mIngredientList = ingredientList;
    }
}
