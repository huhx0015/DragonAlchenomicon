package com.huhx0015.dragonalchenomicon.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.huhx0015.dragonalchenomicon.R;
import com.huhx0015.dragonalchenomicon.contracts.AlchemyContract;
import com.huhx0015.dragonalchenomicon.presenters.AlchemyPresenter;
import java.util.HashSet;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Michael Yoon Huh on 5/11/2017.
 */

public class AlchemyFragment extends Fragment implements AlchemyContract.View {

    // PRESENTER VARIABLES:
    private AlchemyPresenter mAlchemyPresenter;

    private static final String LOG_TAG = AlchemyFragment.class.getSimpleName();

    private Unbinder mUnbinder;

    public static AlchemyFragment newInstance() {
        return new AlchemyFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View alchemyView = inflater.inflate(R.layout.fragment_alchemy, container, false);
        mUnbinder = ButterKnife.bind(this, alchemyView);

        initView();
        mAlchemyPresenter = new AlchemyPresenter(this);

        return alchemyView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }

    /** LAYOUT METHODS _________________________________________________________________________ **/

    private void initView() {

    }

    @OnClick(R.id.alchenomicon_item_1)
    public void firstIngredientButton() {
        mAlchemyPresenter.onIngredientButtonClicked(1);
    }

    @OnClick(R.id.alchenomicon_item_2)
    public void secondIngredientButton() {
        mAlchemyPresenter.onIngredientButtonClicked(2);
    }

    @OnClick(R.id.alchenomicon_item_3)
    public void thirdIngredientButton() {
        mAlchemyPresenter.onIngredientButtonClicked(3);
    }

    /** VIEW METHODS ___________________________________________________________________________ **/

    @Override
    public void setPresenter(AlchemyContract.Presenter presenter) {

    }

    @Override
    public void showIngredientDialog(HashSet<String> ingredientList) {
        Log.d(LOG_TAG, "showIngredientDialog(): Ingredient dialog shown.");
    }

    @Override
    public void showRecipeResults() {

    }

    @Override
    public void setLoadingIndicator(boolean isActive) {

    }

    @Override
    public void showNoRecipeResults() {

    }
}
