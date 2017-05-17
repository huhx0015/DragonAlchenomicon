package com.huhx0015.dragonalchenomicon.model.repositories;

import com.huhx0015.dragonalchenomicon.contracts.IngredientPickerAdapterContract;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by Michael Yoon Huh on 5/15/2017.
 */

public class IngredientPickerAdapterRepository implements IngredientPickerAdapterContract.Repository {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // INGREDIENT LIST VARIABLES:
    private List<String> mIngredientList;
    private int mIngredientCount;

    /** CONSTRUCTOR METHODS ____________________________________________________________________ **/

    @Inject
    public IngredientPickerAdapterRepository() {}

    /** REPOSITORY METHODS _____________________________________________________________________ **/

    @Override
    public int getIngredientCount() {
        return mIngredientCount;
    }

    @Override
    public List<String> getIngredientList() {
        return mIngredientList;
    }

    @Override
    public void setIngredientList(HashSet<String> ingredientList) {
        this.mIngredientList = new ArrayList<>(ingredientList);
        this.mIngredientCount = ingredientList.size();

        // Sorts the ingredient list alphabetically.
        Collections.sort(mIngredientList, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return s1.compareToIgnoreCase(s2);
            }
        });
    }
}
