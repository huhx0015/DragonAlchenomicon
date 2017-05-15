package com.huhx0015.dragonalchenomicon.modules;

import com.huhx0015.dragonalchenomicon.contracts.AlchenomiconContract;
import com.huhx0015.dragonalchenomicon.contracts.RecipeListContract;
import com.huhx0015.dragonalchenomicon.data.repositories.AlchenomiconRepository;
import com.huhx0015.dragonalchenomicon.data.repositories.RecipeListRepository;
import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Michael Yoon Huh on 5/13/2017.
 */

@Module
public class PersistenceModule {

    /** MODULE METHODS _________________________________________________________________________ **/

    @Provides
    @Singleton
    public AlchenomiconContract.Repository provideAlchenomiconRepository() {
        return new AlchenomiconRepository();
    }

    @Provides
    public RecipeListContract.Repository provideRecipeListRepository() {
        return new RecipeListRepository();
    }
}
