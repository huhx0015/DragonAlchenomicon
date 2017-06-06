package com.huhx0015.dragonalchenomicon.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;
import com.huhx0015.dragonalchenomicon.R;
import com.huhx0015.dragonalchenomicon.utils.DisplayUtils;
import com.huhx0015.dragonalchenomicon.view.adapters.IngredientPickerDialogAdapter;
import com.huhx0015.dragonalchenomicon.constants.AlchenomiconConstants;
import com.huhx0015.dragonalchenomicon.model.contracts.IngredientPickerContract;
import com.huhx0015.dragonalchenomicon.view.listeners.IngredientPickerAdapterListener;
import com.huhx0015.dragonalchenomicon.view.listeners.IngredientPickerListener;
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

    // CONTEXT VARIABLES:
    private Context mContext;

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
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPresenter(new IngredientPickerPresenter(this)); // Sets the presenter for this fragment.

        if (savedInstanceState != null) {
            initSavedInstance(savedInstanceState);
        }
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        View view = View.inflate(mContext, R.layout.dialog_ingredient_picker, null);
        dialog.setContentView(view);
        mUnbinder = ButterKnife.bind(this, view);
        initBottomSheet(view);
        initIngredientList();
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
        mRecyclerView.setAdapter(null);
        mUnbinder.unbind();
    }

    /** FRAGMENT EXTENSION METHODS _____________________________________________________________ **/

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // INGREDIENT LIST:
        HashSet<String> ingredientList = mPresenter.getRepository().getIngredientList();
        if (ingredientList != null) {
            outState.putSerializable(BUNDLE_INGREDIENT_LIST, ingredientList);
        }

        // BUTTON ID:
        outState.putInt(BUNDLE_BUTTON_ID, mPresenter.getRepository().getButtonId());
    }

    /** INIT METHODS ___________________________________________________________________________ **/

    private void initIngredientList() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            int buttonId = arguments.getInt(BUNDLE_BUTTON_ID);
            HashSet<String> ingredientList = (HashSet<String>) arguments.getSerializable(BUNDLE_INGREDIENT_LIST);
            mPresenter.getRepository().setButtonId(buttonId);
            mPresenter.getRepository().setIngredientList(ingredientList);
            mPresenter.loadIngredientList();
        }
    }

    private void initSavedInstance(Bundle savedInstanceState) {

        // INGREDIENT LIST:
        HashSet<String> ingredientList = (HashSet<String>) savedInstanceState.getSerializable(BUNDLE_INGREDIENT_LIST);
        if (ingredientList != null) {
            mPresenter.getRepository().setIngredientList(ingredientList);
        }

        // BUTTON ID:
        int buttonId = savedInstanceState.getInt(BUNDLE_BUTTON_ID);
        mPresenter.getRepository().setButtonId(buttonId);
    }

    private void initBottomSheet(final View view) {
        final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(((View) view.getParent()));
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                int height = view.getMeasuredHeight();
                bottomSheetBehavior.setPeekHeight(height); // Sets the BottomSheetDialogFragment height.
            }
        });
    }

    private void initRecyclerView() {
        GridLayoutManager layoutManager;
        if (DisplayUtils.getOrientation(mContext) == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager = new GridLayoutManager(mContext, AlchenomiconConstants.COLUMNS_DOUBLE);
        } else {
            layoutManager = new GridLayoutManager(mContext, AlchenomiconConstants.COLUMNS_DOUBLE);
        }

        layoutManager.setItemPrefetchEnabled(true);
        layoutManager.setInitialPrefetchItemCount(AlchenomiconConstants.PREFETCH_VALUE);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setDrawingCacheEnabled(true);
        mRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        IngredientPickerDialogAdapter adapter = new IngredientPickerDialogAdapter(mPresenter.getRepository().getIngredientList(), this);
        adapter.setHasStableIds(true);
        mRecyclerView.setAdapter(adapter);
    }

    /** SET METHODS ____________________________________________________________________________ **/

    public void setPickerListener(IngredientPickerListener listener) {
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
        mListener.onIngredientPickerDismissed(ingredient, mPresenter.getRepository().getButtonId());
        dismiss();
    }
}