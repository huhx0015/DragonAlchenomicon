package com.huhx0015.dragonalchenomicon.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.huhx0015.dragonalchenomicon.R;
import com.huhx0015.dragonalchenomicon.adapters.RecipeListAdapter;
import com.huhx0015.dragonalchenomicon.application.AlchenomiconApplication;
import com.huhx0015.dragonalchenomicon.contracts.RecipeListContract;
import com.huhx0015.dragonalchenomicon.data.AlchenomiconDatabaseHelper;
import com.huhx0015.dragonalchenomicon.model.AlchenomiconRecipe;
import com.huhx0015.dragonalchenomicon.presenters.RecipeListPresenter;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Michael Yoon Huh on 5/11/2017.
 */

public class RecipeListFragment extends Fragment implements RecipeListContract.View {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // CONSTANT VARIABLES:
    private static final int RECIPE_LIST_PREFETCH_VALUE = 6;
    private static final String INSTANCE_RECIPE_LIST = RecipeListFragment.class.getSimpleName() + "_RECIPE_LIST";
    private static final String LOG_TAG = RecipeListFragment.class.getSimpleName();

    // DATABASE VARIABLES:
    @Inject AlchenomiconDatabaseHelper mDatabase;

    // PRESENTER VARIABLES:
    private RecipeListPresenter mRecipeListPresenter;

    // RECIPE DATA VARIABLES:
    private List<AlchenomiconRecipe> mRecipeList;

    // VIEW VARAIBLES:
    private Unbinder mUnbinder;

    // VIEW INJECTION VARIABLES:
    @BindView(R.id.recipe_list_recyclerview) RecyclerView mRecyclerView;

    /** CONSTRUCTOR METHODS ____________________________________________________________________ **/

    public static RecipeListFragment newInstance() {
        return new RecipeListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View recipeListView = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        mUnbinder = ButterKnife.bind(this, recipeListView);
        mRecipeListPresenter = new RecipeListPresenter(this);
        AlchenomiconApplication.getInstance().getAppComponent().inject(this);

        if (savedInstanceState != null) {
            mRecipeList = savedInstanceState.getParcelableArrayList(INSTANCE_RECIPE_LIST);
        } else {
            mRecipeList = mDatabase.getAllRecipes();
        }

        mRecipeListPresenter.onRecipeListLoad();

        return recipeListView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mRecipeList != null) {
            outState.putParcelableArrayList(INSTANCE_RECIPE_LIST, new ArrayList<>(mRecipeList));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    private void initView() {
        initRecyclerView();
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setItemPrefetchEnabled(true);
        layoutManager.setInitialPrefetchItemCount(RECIPE_LIST_PREFETCH_VALUE);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setDrawingCacheEnabled(true);
        mRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        RecipeListAdapter adapter = new RecipeListAdapter(mRecipeList);
        adapter.setHasStableIds(true);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void setPresenter(RecipeListContract.Presenter presenter) {

    }

    @Override
    public void showRecipeList() {
        initRecyclerView();
    }
}
