package com.huhx0015.dragonalchenomicon.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import com.huhx0015.dragonalchenomicon.adapters.AlchenomiconPagerAdapter;
import com.huhx0015.dragonalchenomicon.contracts.AlchenomiconContract;
import com.huhx0015.dragonalchenomicon.presenters.AlchenomiconPresenter;
import com.huhx0015.dragonalchenomicon.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class AlchenomiconActivity extends AppCompatActivity implements AlchenomiconContract.View {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // LOGGING VARIABLES:
    private static final String LOG_TAG = AlchenomiconActivity.class.getSimpleName();

    // PRESENTER VARIABLES:
    private AlchenomiconPresenter mAlchenomiconPresenter;

    // VIEW INJECTION VARIABLES:
    @BindView(R.id.alchenomicon_navigation) BottomNavigationView mBottomNavigationView;
    @BindView(R.id.alchenomicon_view_pager) ViewPager mViewPager;

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

        // VIEWPAGER:
        AlchenomiconPagerAdapter pagerAdapter = new AlchenomiconPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(pagerAdapter);
        ViewPager.SimpleOnPageChangeListener pageListener = new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {

            }
        };
        mViewPager.addOnPageChangeListener(pageListener);

        // BOTTOM NAVIGATION VIEW:
        mBottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    /** LISTENERS ______________________________________________________________________________ **/

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_alchemy_pot:
                    mViewPager.setCurrentItem(0);
                    return true;
                case R.id.navigation_recipe_list:
                    mViewPager.setCurrentItem(1);
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
