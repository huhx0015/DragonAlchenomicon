package com.huhx0015.dragonalchenomicon.interfaces;

import android.support.annotation.NonNull;
import io.reactivex.Scheduler;

/**
 * Allow providing different types of {@link Scheduler}s.
 *
 * Reference: https://github.com/googlesamples/android-architecture/tree/todo-mvp-rxjava/
 */

public interface BaseSchedulerProvider {

    @NonNull
    Scheduler computation();

    @NonNull
    Scheduler io();

    @NonNull
    Scheduler ui();
}
