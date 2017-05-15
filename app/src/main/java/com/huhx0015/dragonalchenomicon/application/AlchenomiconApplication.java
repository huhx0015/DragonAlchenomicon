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

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // APPLICATION VARIABLES:
    private static AlchenomiconApplication instance = new AlchenomiconApplication();
    private static AppComponent mAppComponent;

    /** INSTANCE METHODS _______________________________________________________________________ **/

    public static AlchenomiconApplication getInstance() {
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