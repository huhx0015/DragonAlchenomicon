package com.huhx0015.dragonalchenomicon.application;

import android.app.Application;
import com.huhx0015.dragonalchenomicon.injections.components.AppComponent;
import com.huhx0015.dragonalchenomicon.injections.components.DaggerAppComponent;
import com.huhx0015.dragonalchenomicon.injections.modules.DataModule;
import com.huhx0015.dragonalchenomicon.injections.modules.PersistenceModule;

/**
 * Created by Michael Yoon Huh on 5/11/2017.
 */

public class AlchenomiconApplication extends Application {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // APPLICATION VARIABLES:
    private static AlchenomiconApplication instance;
    private static AppComponent mAppComponent;

    /** INSTANCE METHODS _______________________________________________________________________ **/

    public static AlchenomiconApplication getInstance() {
        if (instance == null) {
            instance = new AlchenomiconApplication();
        }
        return instance;
    }

    /** APPLICATION LIFECYCLE METHODS __________________________________________________________ **/

    @Override
    public void onCreate() {
        super.onCreate();
        getAppComponent();
    }

    /** DAGGER METHODS _________________________________________________________________________ **/

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