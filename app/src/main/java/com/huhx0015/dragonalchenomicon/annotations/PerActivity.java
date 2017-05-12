package com.huhx0015.dragonalchenomicon.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.inject.Scope;

/**
 * Created by Michael Yoon Huh on 5/11/2017.
 *
 * Reference: https://blog.mindorks.com/introduction-to-dagger-2-using-dependency-injection-in-android-part-2-b55857911bcd
 */

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivity {
}
