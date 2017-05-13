package com.huhx0015.dragonalchenomicon.application;

import android.app.Application;
import com.huhx0015.dragonalchenomicon.interfaces.AppComponent;
import com.huhx0015.dragonalchenomicon.interfaces.DaggerAppComponent;
import com.huhx0015.dragonalchenomicon.modules.DataModule;
import com.huhx0015.dragonalchenomicon.modules.PersistenceModule;

/**
 * Created by Michael Yoon Huh on 5/11/2017.
 */

public class AlchenomiconApplication extends Application {

    private static AlchenomiconApplication instance = new AlchenomiconApplication();
    private static AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        getAppComponent();
    }

    public static AlchenomiconApplication getInstance() {
        return instance;
    }

    public AppComponent getAppComponent() {
        if (mAppComponent == null){
            mAppComponent = DaggerAppComponent.builder()
                    .dataModule(new DataModule(this))
                    .persistenceModule(new PersistenceModule())
                    .build();
        }
        return mAppComponent;
    }
}