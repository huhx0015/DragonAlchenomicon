package com.huhx0015.dragonalchenomicon.modules;

import com.huhx0015.dragonalchenomicon.contracts.RecipeListContract;
import com.huhx0015.dragonalchenomicon.data.repositories.RecipeListRepository;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Michael Yoon Huh on 5/13/2017.
 */

@Module
public class PersistenceModule {

    @Provides
    public RecipeListContract.Repository provideRepository() {
        return new RecipeListRepository();
    }
}
