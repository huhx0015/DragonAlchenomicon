package com.huhx0015.dragonalchenomicon.view.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import com.huhx0015.dragonalchenomicon.view.adapters.AlchenomiconPagerAdapter;
import com.huhx0015.dragonalchenomicon.model.contracts.AlchenomiconContract;
import com.huhx0015.dragonalchenomicon.presenters.AlchenomiconPresenter;
import com.huhx0015.dragonalchenomicon.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Michael Yoon Huh on 4/25/2017.
 */

public class AlchenomiconActivity extends AppCompatActivity implements AlchenomiconContract.View {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // CONSTANT VARIABLES:
    private static final int BOTTOM_ALL_RECIPES = 1;

    // LOGGING VARIABLES:
    private static final String LOG_TAG = AlchenomiconActivity.class.getSimpleName();

    // PRESENTER VARIABLES:
    private AlchenomiconContract.Presenter mPresenter;

    // SAVE INSTANCE VARIABLES:
    private static final String INSTANCE_CURRENT_PAGE = AlchenomiconActivity.class.getSimpleName() + "_CURRENT_PAGE";

    // VIEW INJECTION VARIABLES:
    @BindView(R.id.alchenomicon_navigation) BottomNavigationView mBottomNavigationView;
    @BindView(R.id.alchenomicon_view_pager) ViewPager mViewPager;

    /** ACTIVITY LIFECYCLE METHODS _____________________________________________________________ **/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alchenomicon);
        ButterKnife.bind(this);
        setPresenter(new AlchenomiconPresenter(this)); // Sets the presenter for this activity.
        mPresenter.onInitListeners(); // Initializes the listeners for the activity.

        if (savedInstanceState != null) {
            int currentPage = savedInstanceState.getInt(INSTANCE_CURRENT_PAGE);
            mPresenter.onPageSelected(currentPage);
            mPresenter.setCurrentPage(currentPage);
        }
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

    /** ACTIVITY EXTENSION METHODS _____________________________________________________________ **/

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(INSTANCE_CURRENT_PAGE, mPresenter.getCurrentPage());
    }

    /** VIEW METHODS ____________________________________________________________________________ **/

    @Override
    public void setPresenter(AlchenomiconContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void initBottomNavigationView() {
        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_alchemy_pot:
                        mPresenter.onBottomNavigationClicked(0);
                        return true;
                    case R.id.navigation_recipe_list:
                        mPresenter.onBottomNavigationClicked(1);
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public void initViewPager() {
        AlchenomiconPagerAdapter pagerAdapter = new AlchenomiconPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(pagerAdapter);
        ViewPager.SimpleOnPageChangeListener pageListener = new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mPresenter.onPageSelected(position);
            }
        };
        mViewPager.addOnPageChangeListener(pageListener);
    }

    @Override
    public void updateViewPager(int position) {
        mViewPager.setCurrentItem(position);
    }

    @Override
    public void updateBottomNavigationSelected(int position) {
        int navigationId;
        switch (position) {
            case BOTTOM_ALL_RECIPES:
                navigationId = R.id.navigation_recipe_list;
                break;
            default:
                navigationId = R.id.navigation_alchemy_pot;
                break;
        }

        mBottomNavigationView.setSelectedItemId(navigationId);
    }
}