package com.huhx0015.dragonalchenomicon.view.fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import com.huhx0015.dragonalchenomicon.R;
import com.huhx0015.dragonalchenomicon.utils.DisplayUtils;
import com.huhx0015.dragonalchenomicon.view.adapters.RecipeListAdapter;
import com.huhx0015.dragonalchenomicon.constants.AlchenomiconConstants;
import com.huhx0015.dragonalchenomicon.model.contracts.RecipeListContract;
import com.huhx0015.dragonalchenomicon.model.objects.AlchenomiconRecipe;
import com.huhx0015.dragonalchenomicon.presenters.RecipeListPresenter;
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

    // CONTEXT VARIABLES:
    private Context mContext;

    // LOGGING VARIABLES:
    private static final String LOG_TAG = RecipeListFragment.class.getSimpleName();

    // PRESENTER VARIABLES:
    private RecipeListContract.Presenter mPresenter;

    // SAVE INSTANCE VARIABLES:
    private static final String INSTANCE_RECIPE_LIST = RecipeListFragment.class.getSimpleName() + "_RECIPE_LIST";

    // VIEW VARIABLES:
    private Unbinder mUnbinder;

    // VIEW INJECTION VARIABLES:
    @BindView(R.id.recipe_list_progressbar) ProgressBar mProgressBar;
    @BindView(R.id.recipe_list_recyclerview) RecyclerView mRecyclerView;

    /** INSTANCE METHODS _______________________________________________________________________ **/

    public static RecipeListFragment newInstance() {
        return new RecipeListFragment();
    }

    /** FRAGMENT LIFECYCLE METHODS _____________________________________________________________ **/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
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

        List<AlchenomiconRecipe> recipeList = mPresenter.getRecipeList();
        if (recipeList != null) {
            outState.putParcelableArrayList(INSTANCE_RECIPE_LIST, new ArrayList<>(recipeList));
        }
    }

    /** INIT METHODS ___________________________________________________________________________ **/

    private void initRecipeList(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            List<AlchenomiconRecipe> recipeList = savedInstanceState.getParcelableArrayList(INSTANCE_RECIPE_LIST);

            if (recipeList != null) {
                mPresenter.setRecipeList(recipeList);
                mPresenter.onLoadRecipeList(); // Recipe list is shown.
                return;
            }
        }

        mPresenter.onLoadRecipeList(); // Recipe list is loaded.
    }

    private void initRecyclerView() {
        GridLayoutManager layoutManager;
        if (DisplayUtils.getOrientation(mContext) == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager = new GridLayoutManager(mContext, AlchenomiconConstants.COLUMNS_SINGLE);
        } else {
            layoutManager = new GridLayoutManager(mContext, AlchenomiconConstants.COLUMNS_DOUBLE);
        }

        layoutManager.setItemPrefetchEnabled(true);
        layoutManager.setInitialPrefetchItemCount(AlchenomiconConstants.PREFETCH_VALUE);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setDrawingCacheEnabled(true);
        mRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        RecipeListAdapter adapter = new RecipeListAdapter(mPresenter.getRecipeList(), mContext);
        adapter.setHasStableIds(true);
        mRecyclerView.setAdapter(adapter);
    }

    /** VIEW METHODS ___________________________________________________________________________ **/

    @Override
    public void setPresenter(RecipeListContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showProgressBar(final boolean isDisplay) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isDisplay) {
                    mProgressBar.setVisibility(View.VISIBLE);
                } else {
                    mProgressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void showRecipeList() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                initRecyclerView();
                mRecyclerView.setVisibility(View.VISIBLE);
            }
        });
    }
}
