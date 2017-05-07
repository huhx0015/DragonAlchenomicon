package com.huhx0015.dragonalchenomicon;

import android.support.annotation.NonNull;

/**
 * This specifies the contract between the view and the presenter.
 */
public interface AlchenomiconContract {

    interface View extends BaseView<Presenter> {

        void showRecipeResults();

        void setLoadingIndicator(boolean isActive);

        void showNoRecipeResults();

//        void setLoadingIndicator(boolean active);
//
//        void showTasks(List<Task> tasks);
//
//        void showAddTask();
//
//        void showTaskDetailsUi(String taskId);
//
//        void showTaskMarkedComplete();
//
//        void showTaskMarkedActive();
//
//        void showCompletedTasksCleared();
//
//        void showLoadingTasksError();
//
//        void showNoTasks();
//
//        void showActiveFilterLabel();
//
//        void showCompletedFilterLabel();
//
//        void showAllFilterLabel();
//
//        void showNoActiveTasks();
//
//        void showNoCompletedTasks();
//
//        void showSuccessfullySavedMessage();
//
//        boolean isActive();
//
//        void showFilteringPopUpMenu();
    }

    interface Presenter extends BasePresenter {

//        void result(int requestCode, int resultCode);
//
//        void loadTasks(boolean forceUpdate);
//
//        void addNewTask();
//
//        void openTaskDetails(@NonNull Task requestedTask);
//
//        void completeTask(@NonNull Task completedTask);
//
//        void activateTask(@NonNull Task activeTask);
//
//        void clearCompletedTasks();
//
//        void setFiltering(TasksFilterType requestType);
//
//        TasksFilterType getFiltering();
    }
}
