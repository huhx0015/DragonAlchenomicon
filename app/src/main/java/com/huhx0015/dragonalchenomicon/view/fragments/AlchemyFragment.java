package com.huhx0015.dragonalchenomicon.view.fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.huhx0015.dragonalchenomicon.R;
import com.huhx0015.dragonalchenomicon.utils.DisplayUtils;
import com.huhx0015.dragonalchenomicon.view.adapters.RecipeListAdapter;
import com.huhx0015.dragonalchenomicon.constants.AlchenomiconConstants;
import com.huhx0015.dragonalchenomicon.model.contracts.AlchemyContract;
import com.huhx0015.dragonalchenomicon.view.dialog.IngredientPickerDialog;
import com.huhx0015.dragonalchenomicon.view.listeners.IngredientPickerListener;
import com.huhx0015.dragonalchenomicon.model.objects.AlchenomiconRecipe;
import com.huhx0015.dragonalchenomicon.presenters.AlchemyPresenter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Michael Yoon Huh on 5/11/2017.
 */

public class AlchemyFragment extends Fragment implements AlchemyContract.View, IngredientPickerListener {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // CONSTANT VARIABLES:
    private static final int INGREDIENT_BUTTON_1_ID = 0;
    private static final int INGREDIENT_BUTTON_2_ID = 1;
    private static final int INGREDIENT_BUTTON_3_ID = 2;

    // CONTEXT VARIABLES:
    private Context mContext;

    // LOGGING VARIABLES:
    private static final String LOG_TAG = AlchemyFragment.class.getSimpleName();

    // PRESENTER VARIABLES:
    private AlchemyContract.Presenter mPresenter;

    // SAVE INSTANCE VARIABLES:
    private static final String INSTANCE_INGREDIENT_LIST = AlchemyFragment.class.getSimpleName() + "_INGREDIENT_LIST";
    private static final String INSTANCE_SELECTED_INGREDIENT_LIST = AlchemyFragment.class.getSimpleName() + "_SELECTED_INGREDIENT_LIST";
    private static final String INSTANCE_RECIPE_RESULTS = AlchemyFragment.class.getSimpleName() + "_RECIPE_RESULTS";

    // VIEW VARIABLES:
    private Unbinder mUnbinder;

    // VIEW INJECTION VARIABLES:
    @BindView(R.id.alchemy_clear_button) Button mClearButton;
    @BindView(R.id.alchemy_ingredient_1_icon) ImageView mFirstSelectedIngredientView;
    @BindView(R.id.alchemy_ingredient_2_icon) ImageView mSecondSelectedIngredientView;
    @BindView(R.id.alchemy_ingredient_3_icon) ImageView mThirdSelectedIngredientView;
    @BindView(R.id.alchemy_list_progressbar) ProgressBar mProgressBar;
    @BindView(R.id.alchemy_recycler_view) RecyclerView mRecyclerView;
    @BindView(R.id.alchemy_ingredient_1_text) TextView mFirstSelectedIngredientText;
    @BindView(R.id.alchemy_ingredient_2_text) TextView mSecondSelectedIngredientText;
    @BindView(R.id.alchemy_ingredient_3_text) TextView mThirdSelectedIngredientText;
    @BindView(R.id.alchemy_no_results_text) TextView mNoResultsText;

    /** INSTANCE METHODS _______________________________________________________________________ **/

    public static AlchemyFragment newInstance() {
        return new AlchemyFragment();
    }

    /** FRAGMENT LIFECYCLE METHODS _____________________________________________________________ **/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View alchemyView = inflater.inflate(R.layout.fragment_alchemy, container, false);
        mUnbinder = ButterKnife.bind(this, alchemyView);
        setPresenter(new AlchemyPresenter(this)); // Sets the presenter for this fragment.

        initSavedInstance(savedInstanceState);
        initText();

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
        mRecyclerView.setAdapter(null);
        mUnbinder.unbind();
    }

    /** FRAGMENT EXTENSION METHODS _____________________________________________________________ **/

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // SELECTED INGREDIENT LIST:
        outState.putStringArray(INSTANCE_SELECTED_INGREDIENT_LIST, mPresenter.getRepository().getSelectedIngredientList());

        // INGREDIENT LIST:
        HashSet<String> ingredientList = mPresenter.getRepository().getIngredientList();
        if (ingredientList != null) {
            outState.putSerializable(INSTANCE_INGREDIENT_LIST, ingredientList);
        }

        // RECIPE RESULTS:
        List<AlchenomiconRecipe> recipeResults = mPresenter.getRepository().getRecipeResults();
        if (recipeResults != null) {
            outState.putParcelableArrayList(INSTANCE_RECIPE_RESULTS, new ArrayList<>(recipeResults));
        }
    }

    /** LAYOUT METHODS _________________________________________________________________________ **/

    @OnClick(R.id.alchemy_ingredient_1_button)
    public void firstIngredientButton() {
        mPresenter.onIngredientButtonClicked(INGREDIENT_BUTTON_1_ID);
    }

    @OnClick(R.id.alchemy_ingredient_2_button)
    public void secondIngredientButton() {
        mPresenter.onIngredientButtonClicked(INGREDIENT_BUTTON_2_ID);
    }

    @OnClick(R.id.alchemy_ingredient_3_button)
    public void thirdIngredientButton() {
        mPresenter.onIngredientButtonClicked(INGREDIENT_BUTTON_3_ID);
    }

    @OnClick(R.id.alchemy_clear_button)
    public void clearIngredientButton() {
        mPresenter.onClearButtonClicked();
    }

    /** INIT METHODS ___________________________________________________________________________ **/

    private void initSavedInstance(Bundle savedInstanceState) {
        if (savedInstanceState != null) {

            // SELECTED INGREDIENT LIST:
            String[] selectedIngredientList = savedInstanceState.getStringArray(INSTANCE_SELECTED_INGREDIENT_LIST);
            if (selectedIngredientList != null) {
                mPresenter.setSelectedIngredientList(selectedIngredientList);
            }

            // INGREDIENT LIST:
            HashSet<String> ingredientList = (HashSet<String>) savedInstanceState.getSerializable(INSTANCE_INGREDIENT_LIST);
            if (ingredientList != null) {
                mPresenter.getRepository().setIngredientList(ingredientList);
            }

            // RECIPE RESULTS:
            List<AlchenomiconRecipe> recipeResults = savedInstanceState.getParcelableArrayList(INSTANCE_RECIPE_RESULTS);
            if (recipeResults != null) {
                mPresenter.setRecipeResults(recipeResults);
            }

            // DIALOG FRAGMENT: Restores the listener for the ingredient picker dialog, if it was
            // shown before the screen configuration change.
            IngredientPickerDialog pickerDialog = (IngredientPickerDialog) getFragmentManager()
                    .findFragmentByTag(IngredientPickerDialog.class.getSimpleName());
            if (pickerDialog != null) {
                pickerDialog.setPickerListener(this);
            }
        }
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

        RecipeListAdapter adapter = new RecipeListAdapter(mPresenter.getRepository().getRecipeResults());
        adapter.setHasStableIds(true);
        mRecyclerView.setAdapter(adapter);
    }

    private void initText() {
        mFirstSelectedIngredientText.setShadowLayer(2, 2, 2, Color.BLACK);
        mSecondSelectedIngredientText.setShadowLayer(2, 2, 2, Color.BLACK);
        mThirdSelectedIngredientText.setShadowLayer(2, 2, 2, Color.BLACK);
        mClearButton.setShadowLayer(2, 2, 2, Color.BLACK);
        mNoResultsText.setShadowLayer(2, 2, 2, Color.BLACK);
    }

    /** DIALOG METHODS _________________________________________________________________________ **/

    private void displayIngredientPickerDialog(int buttonId) {
        FragmentManager fragmentManager = getFragmentManager();
        IngredientPickerDialog pickerDialog = IngredientPickerDialog.newInstance(mPresenter.getRepository().getIngredientList(), buttonId, this);
        pickerDialog.show(fragmentManager, IngredientPickerDialog.class.getSimpleName());
    }

    /** LISTENER METHODS _______________________________________________________________________ **/

    @Override
    public void onIngredientPickerDismissed(String ingredient, int buttonId) {
        mPresenter.setSelectedIngredient(ingredient, buttonId);
    }

    /** VIEW METHODS ___________________________________________________________________________ **/

    @Override
    public void setPresenter(AlchemyContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void showIngredientDialog(final int buttonId) {
        getActivity().runOnUiThread(() -> {
            displayIngredientPickerDialog(buttonId);
            Log.d(LOG_TAG, "showIngredientDialog(): Ingredient dialog shown.");
        });
    }

    @Override
    public void showSelectedIngredient(int resource, int position) {
        ImageView selectedIngredientView = null;
        switch (position) {
            case INGREDIENT_BUTTON_1_ID:
                selectedIngredientView = mFirstSelectedIngredientView;
                break;
            case INGREDIENT_BUTTON_2_ID:
                selectedIngredientView = mSecondSelectedIngredientView;
                break;
            case INGREDIENT_BUTTON_3_ID:
                selectedIngredientView = mThirdSelectedIngredientView;
                break;
        }

        if (selectedIngredientView != null) {
            final ImageView ingredientView = selectedIngredientView;
            Glide.with(getContext())
                    .load(resource)
                    .asBitmap()
                    .into(new SimpleTarget<Bitmap>(AlchenomiconConstants.ICON_ORIGINAL_SIZE, AlchenomiconConstants.ICON_ORIGINAL_SIZE) {
                        @Override
                        public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {

                            // Disables anti-aliasing to maintain pixelation.
                            Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap,
                                    AlchenomiconConstants.ICON_UPSCALED_SIZE,
                                    AlchenomiconConstants.ICON_UPSCALED_SIZE, false);
                            ingredientView.setImageBitmap(scaledBitmap);
                        }
                    });
        }
    }

    @Override
    public void showNoResults(final boolean isDisplayed) {
        getActivity().runOnUiThread(() -> {
            if (isDisplayed) {
                mNoResultsText.setVisibility(View.VISIBLE);
            } else {
                mNoResultsText.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void showRecipeList(final boolean isDisplayed) {
        getActivity().runOnUiThread(() -> {
            if (isDisplayed) {
                initRecyclerView();
                mRecyclerView.setVisibility(View.VISIBLE);
            } else {
                mRecyclerView.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void showProgressBar(final boolean isDisplayed) {
        getActivity().runOnUiThread(() -> {
            if (isDisplayed) {
                mProgressBar.setVisibility(View.VISIBLE);
            } else {
                mProgressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void clearSelectedIngredients() {
        mFirstSelectedIngredientView.setImageBitmap(null);
        mSecondSelectedIngredientView.setImageBitmap(null);
        mThirdSelectedIngredientView.setImageBitmap(null);
    }

    @Override
    public void updateSelectedIngredientText(int position) {
        String ingredientName = mPresenter.getRepository().getSelectedIngredientList()[position].toUpperCase();

        switch (position) {
            case INGREDIENT_BUTTON_1_ID:
                mFirstSelectedIngredientText.setText(!ingredientName.equals(AlchenomiconConstants.NULL_IDENTIFIER) ?
                        ingredientName : getString(R.string.alchemy_ingredient_1));
                break;
            case INGREDIENT_BUTTON_2_ID:
                mSecondSelectedIngredientText.setText(!ingredientName.equals(AlchenomiconConstants.NULL_IDENTIFIER) ?
                        ingredientName : getString(R.string.alchemy_ingredient_2));
                break;
            case INGREDIENT_BUTTON_3_ID:
                mThirdSelectedIngredientText.setText(!ingredientName.equals(AlchenomiconConstants.NULL_IDENTIFIER) ?
                        ingredientName : getString(R.string.alchemy_ingredient_3));
                break;
        }
    }
}
