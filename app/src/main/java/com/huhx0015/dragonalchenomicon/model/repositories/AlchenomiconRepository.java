package com.huhx0015.dragonalchenomicon.model.repositories;

import com.huhx0015.dragonalchenomicon.model.contracts.AlchenomiconContract;
import javax.inject.Inject;

/**
 * Created by Michael Yoon Huh on 5/13/2017.
 */

public class AlchenomiconRepository implements AlchenomiconContract.Repository {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    private int mCurrentPage = 0;

    /** CONSTRUCTOR METHODS ____________________________________________________________________ **/

    @Inject
    public AlchenomiconRepository() {}

    /** REPOSITORY METHODS _____________________________________________________________________ **/

    @Override
    public int getCurrentPage() {
        return mCurrentPage;
    }

    @Override
    public void setCurrentPage(int position) {
        this.mCurrentPage = position;
    }
}
