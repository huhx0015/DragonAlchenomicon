package com.huhx0015.dragonalchenomicon.utils;

import com.huhx0015.dragonalchenomicon.R;

/**
 * Created by Michael Yoon Huh on 5/11/2017.
 */

public class AlchenomiconImageUtils {

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

            default:
                return R.drawable.dq8_item_332;
        }

    }
}
