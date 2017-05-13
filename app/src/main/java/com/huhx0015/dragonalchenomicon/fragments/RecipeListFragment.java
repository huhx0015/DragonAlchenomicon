package com.huhx0015.dragonalchenomicon.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import com.huhx0015.dragonalchenomicon.R;
import com.huhx0015.dragonalchenomicon.adapters.RecipeListAdapter;
import com.huhx0015.dragonalchenomicon.contracts.RecipeListContract;
import com.huhx0015.dragonalchenomicon.model.AlchenomiconRecipe;
import com.huhx0015.dragonalchenomicon.presenters.RecipeListPresenter;
import org.jetbrains.annotations.NotNull;
import java.util.ArrayList;
import java.util.List;
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

    // DATA VARIABLES:
    private List<AlchenomiconRecipe> mRecipeList;

    // PRESENTER VARIABLES:
    private RecipeListContract.Presenter mPresenter;

    // VIEW VARIABLES:
    private Unbinder mUnbinder;

    // VIEW INJECTION VARIABLES:
    @BindView(R.id.recipe_list_progressbar) ProgressBar mProgressBar;
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

        setPresenter(new RecipeListPresenter(this)); // Sets the presenter for this fragment.
        initRecipeList(savedInstanceState); // Initializes the recipe list.

        return recipeListView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unsubscribe();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    /** FRAGMENT EXTENSION METHODS _____________________________________________________________ **/

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (mRecipeList != null) {
            outState.putParcelableArrayList(INSTANCE_RECIPE_LIST, new ArrayList<>(mRecipeList));
        }
    }

    /** INIT METHODS ___________________________________________________________________________ **/

    private void initRecipeList(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            List<AlchenomiconRecipe> recipeList = savedInstanceState.getParcelableArrayList(INSTANCE_RECIPE_LIST);

            if (recipeList != null) {
                mPresenter.onRecipeListLoaded(recipeList); // Recipe list is shown.
                return;
            }
        }

        mPresenter.onLoadRecipeList(); // Recipe list is loaded.
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

    /** VIEW METHODS ___________________________________________________________________________ **/

    @Override
    public void setPresenter(@NotNull RecipeListContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showProgressBar(boolean isDisplay) {
        if (isDisplay) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showRecipeList(List<AlchenomiconRecipe> recipeList) {
        mRecipeList = recipeList;
        initRecyclerView();
        mRecyclerView.setVisibility(View.VISIBLE);
    }
}
