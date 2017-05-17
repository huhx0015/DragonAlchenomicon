package com.huhx0015.dragonalchenomicon.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Michael Yoon Huh on 5/17/2017.
 */

public class AlchenomiconContentProvider extends ContentProvider {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // DATABASE VARIABLES:
    private AlchenomiconDatabaseHelper mDatabase;

    // QUERY VARIABLES:
    private static final int RECIPE = 100;
    private static final int RECIPE_ID = 101;

    // URI VARIABLES:
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    /** CONTENT PROVIDER METHODS _______________________________________________________________ **/

    @Override
    public boolean onCreate() {
        mDatabase = new AlchenomiconDatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase database = mDatabase.getReadableDatabase();
        Cursor cursor;

        switch(sUriMatcher.match(uri)){
            case RECIPE:
                cursor = database.query(
                        AlchenomiconContentContract.AlchenomiconRecipeEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            case RECIPE_ID:
                long _id = ContentUris.parseId(uri);
                cursor = database.query(
                        AlchenomiconContentContract.AlchenomiconRecipeEntry.TABLE_NAME,
                        projection,
                        AlchenomiconContentContract.AlchenomiconRecipeEntry._ID + " = ?",
                        new String[]{String.valueOf(_id)},
                        null,
                        null,
                        sortOrder
                );
                break;
            default:
                throw new UnsupportedOperationException("query(): Unknown uri: " + uri);
        }

        // Set the notification URI for the cursor to the one passed into the function. This causes
        // the cursor to register a content observer to watch for changes that happen to this URI
        // and any of it's descendants. By descendants, we mean any URI that begins with this path.
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch(sUriMatcher.match(uri)){
            case RECIPE:
                return AlchenomiconContentContract.AlchenomiconRecipeEntry.CONTENT_TYPE;
            case RECIPE_ID:
                return AlchenomiconContentContract.AlchenomiconRecipeEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("getType(): Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    /** URI METHODS ____________________________________________________________________________ **/

    // buildUriMatcher(): Builds a UriMatcher that is used to determine witch database request is being made.
    public static UriMatcher buildUriMatcher(){
        String content = AlchenomiconContentContract.CONTENT_AUTHORITY;

        // All paths to the UriMatcher have a corresponding code to return when a match is found.
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(content, AlchenomiconContentContract.PATH_RECIPE, RECIPE);
        matcher.addURI(content, AlchenomiconContentContract.PATH_RECIPE + "/#", RECIPE_ID);

        return matcher;
    }
}
