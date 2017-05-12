package com.huhx0015.dragonalchenomicon.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import com.huhx0015.dragonalchenomicon.contracts.AlchenomiconContract;
import com.huhx0015.dragonalchenomicon.presenters.AlchenomiconPresenter;
import com.huhx0015.dragonalchenomicon.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AlchenomiconActivity extends AppCompatActivity implements AlchenomiconContract.View {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // LOGGING VARIABLES:
    private static final String LOG_TAG = AlchenomiconActivity.class.getSimpleName();

    // PRESENTER VARIABLES:
    private AlchenomiconPresenter mAlchenomiconPresenter;

    // VIEW INJECTION VARIABLES:
    @BindView(R.id.navigation) BottomNavigationView mBottomNavigationView;

    /** ACTIVITY LIFECYCLE METHODS _____________________________________________________________ **/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alchenomicon);
        ButterKnife.bind(this);

        // Create the presenter.
        mAlchenomiconPresenter = new AlchenomiconPresenter(this);

        initView();
    }

    /** ACTIVITY EXTENSION METHODS _____________________________________________________________ **/

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    /** LAYOUT METHODS _________________________________________________________________________ **/

    private void initView() {

        // BOTTOM NAVIGATION VIEW:
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    /** LISTENERS ______________________________________________________________________________ **/

    @OnClick(R.id.alchenomicon_item_1)
    public void firstIngredientButton() {
        mAlchenomiconPresenter.onIngredientButtonClicked(1);
    }

    @OnClick(R.id.alchenomicon_item_2)
    public void secondIngredientButton() {
        mAlchenomiconPresenter.onIngredientButtonClicked(2);
    }

    @OnClick(R.id.alchenomicon_item_3)
    public void thirdIngredientButton() {
        mAlchenomiconPresenter.onIngredientButtonClicked(3);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_dashboard:
                    return true;
                case R.id.navigation_notifications:
                    return true;
            }
            return false;
        }

    };

    /** VIEW METHODS ____________________________________________________________________________ **/

    @Override
    public void setPresenter(AlchenomiconContract.Presenter presenter) {

    }

    @Override
    public void showIngredientDialog() {
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
