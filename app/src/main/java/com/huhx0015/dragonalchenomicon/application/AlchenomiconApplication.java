package com.huhx0015.dragonalchenomicon.application;

import android.app.Application;

import com.huhx0015.dragonalchenomicon.interfaces.AppComponent;
import com.huhx0015.dragonalchenomicon.interfaces.DaggerAppComponent;
import com.huhx0015.dragonalchenomicon.modules.AppModule;
import com.huhx0015.dragonalchenomicon.modules.DataModule;

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
            // Dagger%COMPONENT_NAME%
            mAppComponent = DaggerAppComponent.builder()
                    // list of modules that are part of this component need to be created here too
                    .appModule(new AppModule(this)) // This also corresponds to the name of your module: %component_name%Module
                    .dataModule(new DataModule(this))
                    .build();
        }
        return mAppComponent;
    }
}