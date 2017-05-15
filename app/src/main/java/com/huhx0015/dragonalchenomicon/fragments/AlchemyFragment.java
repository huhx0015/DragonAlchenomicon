package com.huhx0015.dragonalchenomicon.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.huhx0015.dragonalchenomicon.R;
import com.huhx0015.dragonalchenomicon.contracts.AlchemyContract;
import com.huhx0015.dragonalchenomicon.dialog.IngredientPickerDialog;
import com.huhx0015.dragonalchenomicon.interfaces.IngredientPickerListener;
import com.huhx0015.dragonalchenomicon.presenters.AlchemyPresenter;
import java.util.HashSet;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Michael Yoon Huh on 5/11/2017.
 */

public class AlchemyFragment extends Fragment implements AlchemyContract.View, IngredientPickerListener {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // LOGGING VARIABLES:
    private static final String LOG_TAG = AlchemyFragment.class.getSimpleName();

    // PRESENTER VARIABLES:
    private AlchemyContract.Presenter mPresenter;

    // SAVE INSTANCE VARIABLES:
    private static final String INSTANCE_INGREDIENT_LIST = AlchemyFragment.class.getSimpleName() + "_INGREDIENT_LIST";

    // VIEW VARIABLES:
    private Unbinder mUnbinder;

    /** INSTANCE METHODS _______________________________________________________________________ **/

    public static AlchemyFragment newInstance() {
        return new AlchemyFragment();
    }

    /** FRAGMENT LIFECYCLE METHODS _____________________________________________________________ **/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View alchemyView = inflater.inflate(R.layout.fragment_alchemy, container, false);
        mUnbinder = ButterKnife.bind(this, alchemyView);
        setPresenter(new AlchemyPresenter(this)); // Sets the presenter for this fragment.
        initIngredientList(savedInstanceState);
        return alchemyView;
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

        HashSet<String> ingredientList = mPresenter.getIngredientList();
        if (ingredientList != null) {
            outState.putSerializable(INSTANCE_INGREDIENT_LIST, ingredientList);
        }
    }

    /** LAYOUT METHODS _________________________________________________________________________ **/

    @OnClick(R.id.alchemy_ingredient_1)
    public void firstIngredientButton() {
        mPresenter.onIngredientButtonClicked(1);
    }

    @OnClick(R.id.alchemy_ingredient_2)
    public void secondIngredientButton() {
        mPresenter.onIngredientButtonClicked(2);
    }

    @OnClick(R.id.alchemy_ingredient_3)
    public void thirdIngredientButton() {
        mPresenter.onIngredientButtonClicked(3);
    }

    /** INIT METHODS ___________________________________________________________________________ **/

    private void initIngredientList(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            HashSet<String> ingredientList = (HashSet<String>) savedInstanceState.getSerializable(INSTANCE_INGREDIENT_LIST);

            if (ingredientList != null) {
                mPresenter.setIngredientList(ingredientList);
            }
        }
    }

    /** DIALOG METHODS _________________________________________________________________________ **/

    private void displayIngredientPickerDialog() {
        FragmentManager fragmentManager = getFragmentManager();
        IngredientPickerDialog pickerDialog = IngredientPickerDialog.newInstance(mPresenter.getIngredientList(), this);
        pickerDialog.show(fragmentManager, IngredientPickerDialog.class.getSimpleName());
    }

    /** LISTENER METHODS _______________________________________________________________________ **/

    @Override
    public void onIngredientPickerDismissed(String ingredient) {

    }

    /** VIEW METHODS ___________________________________________________________________________ **/

    @Override
    public void setPresenter(AlchemyContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showIngredientDialog() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                displayIngredientPickerDialog();
                Log.d(LOG_TAG, "showIngredientDialog(): Ingredient dialog shown.");
            }
        });
    }
}
