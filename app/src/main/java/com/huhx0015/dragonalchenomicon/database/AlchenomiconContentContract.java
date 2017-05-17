package com.huhx0015.dragonalchenomicon.database;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Michael Yoon Huh on 5/17/2017.
 *
 * Reference: https://guides.codepath.com/android/Creating-Content-Providers
 */

public class AlchenomiconContentContract {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // CONTENT AUTHORITY VARIABLES:
    public static final String CONTENT_AUTHORITY = "com.huhx0015.dragonalchenomicon";
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // PATH VARIABLES:
    public static final String PATH_RECIPE = "recipe";

    /** SUBCLASSES _____________________________________________________________________________ **/

    // AlchenomiconRecipeEntry(): Handles all information regarding the table schema and the URIs
    // related to it.
    public static final class AlchenomiconRecipeEntry implements BaseColumns {

        // Content URI represents the base location for the table.
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_RECIPE).build();

        // These are special type prefixes that specify if a URI returns a list or a specific item.
        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_URI  + "/" + PATH_RECIPE;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_URI + "/" + PATH_RECIPE;

        // Defines the table schema.
        public static final String TABLE_NAME = "DQ8_ALCHEMY_TABLE";
        public static final String KEY_ROWID = "KEY_ROWID";
        public static final String KEY_ITEM = "KEY_ITEM";
        public static final String KEY_CATEGORY = "KEY_CATEGORY";
        public static final String KEY_DESC = "KEY_DESC";
        public static final String KEY_REC1 = "KEY_REC1";
        public static final String KEY_REC2 = "KEY_REC2";
        public static final String KEY_REC3 = "KEY_REC3";
        public static final String KEY_ATT1 = "KEY_ATT1";
        public static final String KEY_ATT2 = "KEY_ATT2";
        public static final String KEY_ATT3 = "KEY_ATT3";

        // Defines a function to build a URI to find a specific recipe by it's identifier.
        public static Uri buildAlchenomiconRecipeUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
