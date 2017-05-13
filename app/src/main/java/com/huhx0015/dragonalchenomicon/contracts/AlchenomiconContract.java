package com.huhx0015.dragonalchenomicon.contracts;

import com.huhx0015.dragonalchenomicon.interfaces.BasePresenter;
import com.huhx0015.dragonalchenomicon.interfaces.BaseView;

/** -----------------------------------------------------------------------------------------------
 *  [AlchenomiconContract] CLASS
 *  DESCRIPTION: AlchenomiconContract is an interface that is the contract between the view and
 *  presenter for AlchenomiconActivity. It defines the responsibility of the Model, View, and the
 *  Presenter.
 *
 *  REFERENCE: https://www.codeproject.com/Articles/1098822/Learn-Android-MVP-Pattern-By-Example
 *  -----------------------------------------------------------------------------------------------
 */

public interface AlchenomiconContract {

    // View: Defines the methods that the concrete View aka Fragment will implement. This way you can
    // proceed to create and test the Presenter without worrying about Android-specific components
    // such as Context.
    interface View extends BaseView<Presenter> {

        void initViewPager();

        void initBottomNavigationView();

        void updateViewPager(int position);
    }

    // Presenter: Defines the methods that the concrete Presenter class will implement. Also known
    // as user actions, this is where the business logic for the app is defined.
    interface Presenter extends BasePresenter {

        int getCurrentPage();

        void onBottomNavigationClicked(int position);

        void onInitListeners();

        void onPageSelected(int position);

    }

    // Repository: Defines the methods that the concrete persistence class will implement. This way
    // the Presenter does not need to be concerned about how data is persisted.
    interface Repository {

        int getCurrentPage();

        void updatePage(int posiiton);
    }
}
