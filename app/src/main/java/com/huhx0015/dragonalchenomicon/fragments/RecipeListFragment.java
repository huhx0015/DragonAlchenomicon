package com.huhx0015.dragonalchenomicon.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.huhx0015.dragonalchenomicon.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Michael Yoon Huh on 5/11/2017.
 */

public class RecipeListFragment extends Fragment {

    private Unbinder mUnbinder;

    public static RecipeListFragment newInstance() {
        return new RecipeListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View recipeListView = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        mUnbinder = ButterKnife.bind(this, recipeListView);
        initView();
        return recipeListView;
    }

    private void initView() {

    }
}
