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

            default:
                return R.drawable.dq8_item_332;
        }

    }
}