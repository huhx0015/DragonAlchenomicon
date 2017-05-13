package com.huhx0015.dragonalchenomicon.interfaces;

import com.huhx0015.dragonalchenomicon.data.repositories.AlchemyRepository;
import com.huhx0015.dragonalchenomicon.data.repositories.RecipeListRepository;
import com.huhx0015.dragonalchenomicon.modules.DataModule;
import com.huhx0015.dragonalchenomicon.modules.PersistenceModule;
import com.huhx0015.dragonalchenomicon.presenters.AlchemyPresenter;
import com.huhx0015.dragonalchenomicon.presenters.AlchenomiconPresenter;
import com.huhx0015.dragonalchenomicon.presenters.RecipeListPresenter;
import javax.inject.Singleton;
import dagger.Component;

/**
 * Created by Michael Yoon Huh on 5/11/2017.
 */

@Singleton
@Component(
        modules = {
                DataModule.class,
                PersistenceModule.class
        }
)
public interface AppComponent {
    void inject(AlchenomiconPresenter presenter);
    void inject(AlchemyPresenter presenter);
    void inject(AlchemyRepository repository);
    void inject(RecipeListPresenter presenter);
    void inject(RecipeListRepository repository);
}