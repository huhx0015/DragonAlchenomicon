package com.huhx0015.dragonalchenomicon.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.huhx0015.dragonalchenomicon.R;
import com.huhx0015.dragonalchenomicon.contracts.RecipeListAdapterContract;
import com.huhx0015.dragonalchenomicon.model.AlchenomiconRecipe;
import com.huhx0015.dragonalchenomicon.presenters.RecipeListAdapterPresenter;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Michael Yoon Huh on 5/11/2017.
 */

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeListViewHolder>
        implements RecipeListAdapterContract.View {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // CONSTANT VARIABLES:
    private static final String NULL_IDENTIFIER = "NULL";

    // PRESENTER VARIABLES:
    private RecipeListAdapterContract.Presenter mPresenter;

    /** CONSTRUCTOR METHODS ____________________________________________________________________ **/

    public RecipeListAdapter(List<AlchenomiconRecipe> recipeList) {
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

    /** VIEW METHODS ___________________________________________________________________________ **/

    @Override
    public void setPresenter(RecipeListAdapterContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showRecipeRow(RecipeListViewHolder holder, AlchenomiconRecipe recipe) {

        // RECIPE NAME:
        holder.receipeName.setText(recipe.recipeName);

        // RECIPE INGREDIENTS:
        int recipeCount = 0;
        for (String ingredient : recipe.recipeIngredientList) {
            if (!ingredient.equals(NULL_IDENTIFIER)) {
                switch (recipeCount) {
                    case 0:
                        holder.firstIngredient.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dq8_item_0, 0, 0, 0);
                        holder.firstIngredient.setText(ingredient);
                        break;
                    case 1:
                        holder.secondIngredient.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dq8_item_1, 0, 0, 0);
                        holder.secondIngredient.setText(ingredient);
                        break;
                    case 2:
                        holder.thirdIngredient.setCompoundDrawablesWithIntrinsicBounds(R.drawable.dq8_item_2, 0, 0, 0);
                        holder.thirdIngredient.setText(ingredient);
                        break;
                }
                recipeCount++;
            } else {
                break;
            }
        }
    }

    /** SUBCLASSES _____________________________________________________________________________ **/

    public class RecipeListViewHolder extends RecyclerView.ViewHolder {

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