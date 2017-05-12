package com.huhx0015.dragonalchenomicon.interfaces;

import com.huhx0015.dragonalchenomicon.fragments.RecipeListFragment;
import com.huhx0015.dragonalchenomicon.modules.DataModule;
import com.huhx0015.dragonalchenomicon.presenters.AlchemyPresenter;
import com.huhx0015.dragonalchenomicon.presenters.AlchenomiconPresenter;
import com.huhx0015.dragonalchenomicon.modules.AppModule;
import com.huhx0015.dragonalchenomicon.presenters.RecipeListPresenter;
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
    void inject(AlchenomiconPresenter presenter);
    void inject(AlchemyPresenter presenter);
    void inject(RecipeListPresenter presenter);
    void inject(RecipeListFragment fragment);
}