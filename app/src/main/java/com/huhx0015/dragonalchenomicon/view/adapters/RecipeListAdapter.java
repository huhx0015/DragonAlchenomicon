package com.huhx0015.dragonalchenomicon.view.adapters;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.huhx0015.dragonalchenomicon.R;
import com.huhx0015.dragonalchenomicon.constants.AlchenomiconConstants;
import com.huhx0015.dragonalchenomicon.model.contracts.RecipeListAdapterContract;
import com.huhx0015.dragonalchenomicon.model.objects.AlchenomiconRecipe;
import com.huhx0015.dragonalchenomicon.presenters.RecipeListAdapterPresenter;
import com.huhx0015.dragonalchenomicon.utils.AlchenomiconImageUtils;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Michael Yoon Huh on 5/11/2017.
 */

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeListViewHolder>
        implements RecipeListAdapterContract.View {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // PRESENTER VARIABLES:
    private RecipeListAdapterContract.Presenter mPresenter;

    /** CONSTRUCTOR METHODS ____________________________________________________________________ **/

    public RecipeListAdapter(List<AlchenomiconRecipe> recipeList) {
        setPresenter(new RecipeListAdapterPresenter(this)); // Sets the presenter for this adapter.
        mPresenter.getRepository().setRecipeList(recipeList);
    }

    /** ADAPTER METHODS ________________________________________________________________________ **/

    @Override
    public RecipeListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_recipe_list, parent, false);
        return new RecipeListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeListViewHolder holder, int position) {
        mPresenter.setRecipeRow(holder, position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mPresenter.getRepository().getRecipeCount();
    }

    @Override
    public void onViewRecycled(RecipeListViewHolder holder) {
        super.onViewRecycled(holder);
        mPresenter.clearRecipeImage(holder.recipeImage);
    }

    /** VIEW METHODS ___________________________________________________________________________ **/

    @Override
    public void setPresenter(RecipeListAdapterContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showRecipeRow(RecipeListViewHolder holder, AlchenomiconRecipe recipe) {

        // RECIPE NAME:
        mPresenter.setRecipeName(recipe.recipeName, holder.recipeName);

        // RECIPE IMAGE:
        int recipeResource = AlchenomiconImageUtils.getItemImage(recipe.recipeName);
        mPresenter.setRecipeImage(recipeResource, holder.recipeImage);

        // RECIPE INGREDIENTS:
        int recipeCount = 0;
        for (String ingredient : recipe.recipeIngredientList) {
            switch (recipeCount) {
                case 0:
                    mPresenter.setRecipeIngredient(ingredient, holder.firstIngredient);
                    break;
                case 1:
                    mPresenter.setRecipeIngredient(ingredient, holder.secondIngredient);
                    break;
                case 2:
                    mPresenter.setRecipeIngredient(ingredient, holder.thirdIngredient);
                    break;
            }
            recipeCount++;
        }
    }

    @Override
    public void showRecipeName(String recipe, TextView view) {
        view.setText(recipe);
    }

    @Override
    public void showRecipeImage(int resource, final ImageView view) {
        Glide.with(view.getContext())
                .asBitmap()
                .load(resource)
                .into(new SimpleTarget<Bitmap>(AlchenomiconConstants.ICON_ORIGINAL_SIZE, AlchenomiconConstants.ICON_ORIGINAL_SIZE) {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

                        // Disables anti-aliasing to maintain pixelation.
                        Bitmap scaledBitmap = Bitmap.createScaledBitmap(resource,
                                AlchenomiconConstants.ICON_UPSCALED_SIZE,
                                AlchenomiconConstants.ICON_UPSCALED_SIZE, false);
                        view.setImageBitmap(scaledBitmap);
                    }
                });
    }

    @Override
    public void showRecipeIngredient(String ingredient, int resource, TextView view) {
        int ingredientVisibility = !ingredient.equals(AlchenomiconConstants.NULL_IDENTIFIER) ? View.VISIBLE : View.GONE;
        view.setCompoundDrawablesWithIntrinsicBounds(resource, 0, 0, 0);
        view.setText(ingredient);
        view.setVisibility(ingredientVisibility);
    }

    @Override
    public void clearRecipeImage(ImageView view) {
        view.setImageBitmap(null);
    }

    /** SUBCLASSES _____________________________________________________________________________ **/

    public static class RecipeListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.adapter_recipe_image) ImageView recipeImage;
        @BindView(R.id.adapter_recipe_ingredient_1) TextView firstIngredient;
        @BindView(R.id.adapter_recipe_ingredient_2) TextView secondIngredient;
        @BindView(R.id.adapter_recipe_ingredient_3) TextView thirdIngredient;
        @BindView(R.id.adapter_recipe_name) TextView recipeName;

        RecipeListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            initText();
        }

        private void initText() {
            firstIngredient.setShadowLayer(2, 2, 2, Color.BLACK);
            secondIngredient.setShadowLayer(2, 2, 2, Color.BLACK);
            thirdIngredient.setShadowLayer(2, 2, 2, Color.BLACK);
            recipeName.setShadowLayer(2, 2, 2, Color.BLACK);
        }
    }
}