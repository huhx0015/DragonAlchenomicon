package com.huhx0015.dragonalchenomicon.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.huhx0015.dragonalchenomicon.R;
import com.huhx0015.dragonalchenomicon.contracts.RecipeListAdapterContract;
import com.huhx0015.dragonalchenomicon.model.AlchenomiconRecipe;
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

    // ADAPTER VARIABLES:
    private Context mContext;

    // CONSTANT VARIABLES:
    private static final String NULL_IDENTIFIER = "NULL";

    // PRESENTER VARIABLES:
    private RecipeListAdapterContract.Presenter mPresenter;

    /** CONSTRUCTOR METHODS ____________________________________________________________________ **/

    public RecipeListAdapter(List<AlchenomiconRecipe> recipeList, Context context) {
        this.mContext = context;
        setPresenter(new RecipeListAdapterPresenter(this)); // Sets the presenter for this adapter.
        mPresenter.setRecipeList(recipeList);
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
        return mPresenter.getRecipeCount();
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
        mPresenter.setRecipeName(recipe.recipeName, holder.receipeName);

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
    public void showRecipeImage(int resource, ImageView view) {
        Glide.with(mContext)
                .load(resource)
                .fitCenter()
                .into(view);
    }

    @Override
    public void showRecipeIngredient(String ingredient, int resource, TextView view) {
        int ingredientVisibility = !ingredient.equals(NULL_IDENTIFIER) ? View.VISIBLE : View.GONE;
        view.setCompoundDrawablesWithIntrinsicBounds(resource, 0, 0, 0);
        view.setText(ingredient);
        view.setVisibility(ingredientVisibility);
    }

    @Override
    public void clearRecipeImage(ImageView view) {
        Glide.clear(view);
    }

    /** SUBCLASSES _____________________________________________________________________________ **/

    public class RecipeListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.adapter_recipe_image) ImageView recipeImage;
        @BindView(R.id.adapter_recipe_ingredient_1) TextView firstIngredient;
        @BindView(R.id.adapter_recipe_ingredient_2) TextView secondIngredient;
        @BindView(R.id.adapter_recipe_ingredient_3) TextView thirdIngredient;
        @BindView(R.id.adapter_recipe_name) TextView receipeName;

        RecipeListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}