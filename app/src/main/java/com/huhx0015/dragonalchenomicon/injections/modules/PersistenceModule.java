package com.huhx0015.dragonalchenomicon.injections.modules;

import com.huhx0015.dragonalchenomicon.model.contracts.AlchemyContract;
import com.huhx0015.dragonalchenomicon.model.contracts.AlchenomiconContract;
import com.huhx0015.dragonalchenomicon.model.contracts.IngredientPickerAdapterContract;
import com.huhx0015.dragonalchenomicon.model.contracts.IngredientPickerContract;
import com.huhx0015.dragonalchenomicon.model.contracts.RecipeListAdapterContract;
import com.huhx0015.dragonalchenomicon.model.contracts.RecipeListContract;
import com.huhx0015.dragonalchenomicon.model.repositories.AlchemyRepository;
import com.huhx0015.dragonalchenomicon.model.repositories.AlchenomiconRepository;
import com.huhx0015.dragonalchenomicon.model.repositories.IngredientPickerAdapterRepository;
import com.huhx0015.dragonalchenomicon.model.repositories.IngredientPickerRepository;
import com.huhx0015.dragonalchenomicon.model.repositories.RecipeListAdapterRepository;
import com.huhx0015.dragonalchenomicon.model.repositories.RecipeListRepository;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Michael Yoon Huh on 5/13/2017.
 */

@Module
public class PersistenceModule {

    /** MODULE METHODS _________________________________________________________________________ **/

    @Provides
    public AlchemyContract.Repository provideAlchemyRepository() {
        return new AlchemyRepository();
    }

    @Provides
    public AlchenomiconContract.Repository provideAlchenomiconRepository() {
        return new AlchenomiconRepository();
    }

    @Provides
    public IngredientPickerAdapterContract.Repository provideIngredientPickerAdapterRepository() {
        return new IngredientPickerAdapterRepository();
    }

    @Provides
    public IngredientPickerContract.Repository provideIngredientPickerRepository() {
        return new IngredientPickerRepository();
    }

    @Provides
    public RecipeListAdapterContract.Repository provideRecipeListAdapterRepository() {
        return new RecipeListAdapterRepository();
    }

    @Provides
    public RecipeListContract.Repository provideRecipeListRepository() {
        return new RecipeListRepository();
    }

}
