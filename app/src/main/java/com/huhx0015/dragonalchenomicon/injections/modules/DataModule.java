package com.huhx0015.dragonalchenomicon.injections.modules;

import android.content.Context;
import com.huhx0015.dragonalchenomicon.database.AlchenomiconDatabaseHelper;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Michael Yoon Huh on 5/11/2017.
 */

@Module
public class DataModule {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    private Context mContext;

    /** CONSTRUCTOR METHOD _____________________________________________________________________ **/

    public DataModule(Context context) {
        this.mContext = context.getApplicationContext();
    }

    /** MODULE METHODS _________________________________________________________________________ **/

    @Provides
    @Singleton
    AlchenomiconDatabaseHelper provideDatabase() {
        return new AlchenomiconDatabaseHelper(mContext);
    }
}
