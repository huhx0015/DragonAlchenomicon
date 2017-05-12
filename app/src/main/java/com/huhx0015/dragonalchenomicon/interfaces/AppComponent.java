package com.huhx0015.dragonalchenomicon.interfaces;

import android.support.v7.app.AppCompatActivity;
import com.huhx0015.dragonalchenomicon.modules.DataModule;
import com.huhx0015.dragonalchenomicon.presenters.AlchenomiconPresenter;
import com.huhx0015.dragonalchenomicon.modules.AppModule;
import javax.inject.Singleton;
import dagger.Component;

/**
 * Created by Michael Yoon Huh on 5/11/2017.
 */

@Singleton
@Component(
        modules = {
                AppModule.class,
                DataModule.class
        }
)
public interface AppComponent {
    void inject(AppCompatActivity activity);
    void inject(AlchenomiconPresenter presenter);
}