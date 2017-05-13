package com.huhx0015.dragonalchenomicon.modules;

import android.content.Context;
import com.huhx0015.dragonalchenomicon.data.database.AlchenomiconDatabaseHelper;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Michael Yoon Huh on 5/11/2017.
 */

@Module
public class DataModule {

    private Context mContext;

    public DataModule(Context context) {
        this.mContext = context.getApplicationContext();
    }

    @Provides
    @Singleton
    AlchenomiconDatabaseHelper provideDatabase() {
        return new AlchenomiconDatabaseHelper(mContext);
    }
}
