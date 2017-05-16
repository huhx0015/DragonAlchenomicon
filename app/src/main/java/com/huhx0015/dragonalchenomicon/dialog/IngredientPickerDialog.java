package com.huhx0015.dragonalchenomicon.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.huhx0015.dragonalchenomicon.R;
import com.huhx0015.dragonalchenomicon.adapters.IngredientPickerDialogAdapter;
import com.huhx0015.dragonalchenomicon.constants.AlchenomiconConstants;
import com.huhx0015.dragonalchenomicon.contracts.IngredientPickerContract;
import com.huhx0015.dragonalchenomicon.listeners.IngredientPickerAdapterListener;
import com.huhx0015.dragonalchenomicon.listeners.IngredientPickerListener;
import com.huhx0015.dragonalchenomicon.presenters.IngredientPickerPresenter;
import java.util.HashSet;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Michael Yoon Huh on 5/11/2017.
 */

public class IngredientPickerDialog extends BottomSheetDialogFragment implements
        IngredientPickerContract.View, IngredientPickerAdapterListener {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // BUNDLE VARIABLES
    private static final String BUNDLE_BUTTON_ID = IngredientPickerDialog.class + "_BUTTON_ID";
    private static final String BUNDLE_INGREDIENT_LIST = IngredientPickerDialog.class + "_INGREDIENT_LIST";

    // CONSTANT VARIABLES
    private static final int BOTTOM_SHEET_PEEK_HEIGHT = 512;

    // LISTENER VARIABLES:
    private IngredientPickerListener mListener;

    // LOGGING VARIABLES:
    private static final String LOG_TAG = IngredientPickerDialog.class.getSimpleName();

    // PRESENTER VARIABLES:
    private IngredientPickerContract.Presenter mPresenter;

    // VIEW VARIABLES:
    private Unbinder mUnbinder;

    // VIEW INJECTION VARIABLES:
    @BindView(R.id.ingredient_picker_recycler_view) RecyclerView mRecyclerView;

    /** CONSTRUCTOR METHODS ____________________________________________________________________ **/

    public static IngredientPickerDialog newInstance(HashSet<String> ingredientList, int buttonId,
                                                     IngredientPickerListener listener) {
        IngredientPickerDialog dialog = new IngredientPickerDialog();
        Bundle arguments = new Bundle();
        arguments.putSerializable(BUNDLE_INGREDIENT_LIST, ingredientList);
        arguments.putInt(BUNDLE_BUTTON_ID, buttonId);
        dialog.setPickerListener(listener);
        dialog.setArguments(arguments);
        return dialog;
    }

    /** FRAGMENT LIFECYCLE METHODS _____________________________________________________________ **/

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View view = View.inflate(getContext(), R.layout.dialog_ingredient_picker, null);
        dialog.setContentView(view);
        mUnbinder = ButterKnife.bind(this, view);
        setPresenter(new IngredientPickerPresenter(this)); // Sets the presenter for this fragment.

        Bundle arguments = getArguments();
        if (arguments != null) {
            int buttonId = arguments.getInt(BUNDLE_BUTTON_ID);
            HashSet<String> ingredientList = (HashSet<String>) arguments.getSerializable(BUNDLE_INGREDIENT_LIST);
            mPresenter.setButtonId(buttonId);
            mPresenter.setIngredientList(ingredientList);
        }

        // Sets the BottomSheetDialogFragment height.
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(((View) view.getParent()));
        bottomSheetBehavior.setPeekHeight(BOTTOM_SHEET_PEEK_HEIGHT);

        initView();
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

    /** LAYOUT METHODS _________________________________________________________________________ **/

    private void initView() {
        mPresenter.loadIngredientList();
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setItemPrefetchEnabled(true);
        layoutManager.setInitialPrefetchItemCount(AlchenomiconConstants.PREFETCH_VALUE);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setDrawingCacheEnabled(true);
        mRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        IngredientPickerDialogAdapter adapter = new IngredientPickerDialogAdapter(mPresenter.getIngredientList(), this, getContext());
        adapter.setHasStableIds(true);
        mRecyclerView.setAdapter(adapter);
    }

    /** SET METHODS ____________________________________________________________________________ **/

    private void setPickerListener(IngredientPickerListener listener) {
        this.mListener = listener;
    }

    /** LISTENER METHODS _______________________________________________________________________ **/

    @Override
    public void onIngredientClicked(String ingredient) {
        mPresenter.onIngredientSelected(ingredient);
    }

    /** VIEW METHODS ___________________________________________________________________________ **/

    @Override
    public void setPresenter(IngredientPickerContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showIngredientList() {
        initRecyclerView();
    }

    @Override
    public void dismissPickerDialog(String ingredient) {
        mListener.onIngredientPickerDismissed(ingredient, mPresenter.getButtonId());
        dismiss();
    }
}