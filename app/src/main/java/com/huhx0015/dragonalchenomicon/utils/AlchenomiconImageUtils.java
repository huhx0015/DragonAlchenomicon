package com.huhx0015.dragonalchenomicon.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import com.huhx0015.dragonalchenomicon.R;

/**
 * Created by Michael Yoon Huh on 5/11/2017.
 */

public class AlchenomiconImageUtils {

    /** RESOURCE METHODS _______________________________________________________________________ **/

    public static int getItemImage(String itemName) {

        switch (itemName) {

            case "Medicinal Herb":
                return R.drawable.dq8_item_257;

            case "Angel Cheese":
                return R.drawable.dq8_item_327;

            case "Plain Cheese":
                return R.drawable.dq8_item_310;

            case "C-C-Cold Cheese":
                return R.drawable.dq8_item_319;

            case "Chilly Cheese":
                return R.drawable.dq8_item_317;

            case "Cold Cheese":
                return R.drawable.dq8_item_318;

            case "Dragon Dung":
                return R.drawable.dq8_item_299;

            case "Special Medicine":
                return R.drawable.dq8_item_261;

            case "Lesser Panacea":
                return R.drawable.dq8_item_262;

            case "Greater Panacea":
                return R.drawable.dq8_item_263;

            case "Fresh Milk":
                return R.drawable.dq8_item_305;

            default:
                return R.drawable.dq8_item_332;
        }
    }

    /** CONVERSION METHODS _____________________________________________________________________ **/

    // convertPixelsToDp: This method converts device specific pixels to density independent pixels.
    // Source: http://stackoverflow.com/questions/4605527/converting-pixels-to-dp
    public static float convertPixelsToDp(float px, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }
}
