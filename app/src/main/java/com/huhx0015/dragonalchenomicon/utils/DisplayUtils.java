package com.huhx0015.dragonalchenomicon.utils;

import android.content.Context;

/**
 * Created by Michael Yoon Huh on 5/17/2017.
 */

public class DisplayUtils {

    /**
     * getOrientation(): Retrieves the device's current orientation.
     *
     * Configuration.ORIENTATION_PORTRAIT
     * Configuration.ORIENTATION_LANDSCAPE
     * @param context
     * @return
     */
    public static int getOrientation(Context context) {
        return context.getResources().getConfiguration().orientation;
    }
}