package com.huhx0015.dragonalchenomicon.model;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

/**
 * Created by Michael Yoon Huh on 5/7/2017.
 *
 * Tip: Avoid Internal Getters/Setters on Android.
 * Reference: https://developer.android.com/training/articles/perf-tips.html#GettersSetters
 */

public class AlchenomiconRecipe implements Parcelable {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // RECIPE VARIABLES:
    public int recipeId;
    public int recipeCategoryId;
    public List<String> recipeIngredientList;
    public List<String> recipeAttributeList;
    public String recipeName;
    public String recipeDescription;

    /** CONSTRUCTOR METHODS ____________________________________________________________________ **/

    public AlchenomiconRecipe() {}

    /** PARCELABLE METHODS _____________________________________________________________________ **/

    protected AlchenomiconRecipe(Parcel in) {
        recipeId = in.readInt();
        recipeCategoryId = in.readInt();
        recipeIngredientList = in.createStringArrayList();
        recipeAttributeList = in.createStringArrayList();
        recipeName = in.readString();
        recipeDescription = in.readString();
    }

    public static final Creator<AlchenomiconRecipe> CREATOR = new Creator<AlchenomiconRecipe>() {
        @Override
        public AlchenomiconRecipe createFromParcel(Parcel in) {
            return new AlchenomiconRecipe(in);
        }

        @Override
        public AlchenomiconRecipe[] newArray(int size) {
            return new AlchenomiconRecipe[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(recipeId);
        dest.writeInt(recipeCategoryId);
        dest.writeStringList(recipeIngredientList);
        dest.writeStringList(recipeAttributeList);
        dest.writeString(recipeName);
        dest.writeString(recipeDescription);
    }
}