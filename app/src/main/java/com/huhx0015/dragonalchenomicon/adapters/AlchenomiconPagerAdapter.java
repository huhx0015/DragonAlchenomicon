package com.huhx0015.dragonalchenomicon.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.huhx0015.dragonalchenomicon.fragments.AlchemyFragment;
import com.huhx0015.dragonalchenomicon.fragments.RecipeListFragment;

/**
 * Created by Michael Yoon Huh on 5/11/2017.
 */

public class AlchenomiconPagerAdapter extends FragmentStatePagerAdapter {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    private static final int NUMBER_OF_FRAGMENTS = 2;

    /** CONSTRUCTOR METHODS ____________________________________________________________________ **/

    public AlchenomiconPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    /** PAGER ADAPTER METHODS __________________________________________________________________ **/

    // getCount(): Returns the number of fragments in the PagerAdapter object.
    @Override
    public int getCount() {
        return NUMBER_OF_FRAGMENTS;
    }

    // getItem(): Returns the fragment based on the current position in the PagerAdapter object.
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
                return RecipeListFragment.newInstance();
            default:
                return AlchemyFragment.newInstance();
        }
    }

    // getItemPosition(): Returns the item position in the PagerAdapter object.
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
