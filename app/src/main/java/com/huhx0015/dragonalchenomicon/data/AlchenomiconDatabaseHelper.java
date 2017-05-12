package com.huhx0015.dragonalchenomicon.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.huhx0015.dragonalchenomicon.annotations.ApplicationContext;
import com.huhx0015.dragonalchenomicon.model.AlchenomiconRecipe;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Michael Yoon Huh on 4/25/2017.
 *
 * Using supplied MySQL database:
 * https://blog.reigndesign.com/blog/using-your-own-sqlite-database-in-android-applications/
 */

@Singleton
public class AlchenomiconDatabaseHelper extends SQLiteAssetHelper {

    // Database Info
    private static final String DATABASE_NAME = "dq8_alchenomicon_table.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase alchenomiconDatabase;

    private static String DATABASE_PATH; // "/data/data/com.huhx0015.dragonalchenomicon/databases/";

    // Post Table Columns
    public static final String TABLE_DQ8_RECIPE = "DQ8_ALCHEMY_TABLE";
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

    private static final String[] TABLE_COLUMNS = {
            KEY_ROWID,
            KEY_ITEM,
            KEY_CATEGORY,
            KEY_DESC,
            KEY_REC1,
            KEY_REC2,
            KEY_REC3,
            KEY_ATT1,
            KEY_ATT2,
            KEY_ATT3
    };

    private static final String LOG_TAG = AlchenomiconDatabaseHelper.class.getSimpleName();



    /** CONSTRUCTOR METHODS ____________________________________________________________________ **/

//    private static AlchenomiconDatabaseHelper dbInstance;

    // Opens the existing database "dq8_alchenomicon_table.db" using methods from SQLiteAssetHelper.
//    public AlchenomiconDatabaseHelper(Context context) {
//        super(context, DATABASE_NAME, null, DATABASE_VERSION);
//    }

    @Inject
    public AlchenomiconDatabaseHelper(@ApplicationContext Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

//    public static synchronized AlchenomiconDatabaseHelper getInstance(Context context) {
//
//        // Use the application context, which will ensure that you
//        // don't accidentally leak an Activity's context.
//        // See this article for more information: http://bit.ly/6LRzfx
//        if (dbInstance == null) {
//            dbInstance = new AlchenomiconDatabaseHelper(context.getApplicationContext());
//        }
//        return dbInstance;
//    }

    /** DATABASE ACCESS METHODS ________________________________________________________________ **/

    public HashSet<String> getAllIngredients() {

        HashSet<String> ingredientList = new HashSet<>();

        // "getReadableDatabase()" and "getWriteableDatabase()" return the same object (except under
        // low disk space scenarios)
        SQLiteDatabase database = getReadableDatabase();

        // Prepares the cursor.
        Cursor cursor = database.query(TABLE_DQ8_RECIPE, TABLE_COLUMNS,
                null, null, null, null, null);

        // Reads the database for all recipes and adds it to the list of recipes.
        try {
            if (cursor.moveToFirst()) {
                do {

                    ingredientList.add(cursor.getString(cursor.getColumnIndex(KEY_REC1)));
                    ingredientList.add(cursor.getString(cursor.getColumnIndex(KEY_REC2)));
                    ingredientList.add(cursor.getString(cursor.getColumnIndex(KEY_REC3)));

                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(LOG_TAG, "getAllIngredients(): Error while trying to get ingredients from database.");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return ingredientList;

    }

    public List<AlchenomiconRecipe> getAllRecipes() {

        List<AlchenomiconRecipe> recipeList = new ArrayList<>();

        // "getReadableDatabase()" and "getWriteableDatabase()" return the same object (except under
        // low disk space scenarios)
        SQLiteDatabase database = getReadableDatabase();

        // Prepares the cursor.
        Cursor cursor = database.query(TABLE_DQ8_RECIPE, TABLE_COLUMNS,
                null, null, null, null, null);

        // Reads the database for all recipes and adds it to the list of recipes.
        try {
            if (cursor.moveToFirst()) {
                do {

                    AlchenomiconRecipe recipe = new AlchenomiconRecipe();
                    recipe.recipeId = cursor.getInt(cursor.getColumnIndex(KEY_ROWID)); // ROW_ID
                    recipe.recipeName = cursor.getString(cursor.getColumnIndex(KEY_ITEM)); // ITEM
                    recipe.recipeCategoryId = cursor.getInt(cursor.getColumnIndex(KEY_CATEGORY)); // CATEGORY
                    recipe.recipeDescription = cursor.getString(cursor.getColumnIndex(KEY_DESC)); // DESCRIPTION

                    // RECIPE LIST:
                    List<String> recipeIngredientList = new ArrayList<>();
                    recipeIngredientList.add(cursor.getString(cursor.getColumnIndex(KEY_REC1)));
                    recipeIngredientList.add(cursor.getString(cursor.getColumnIndex(KEY_REC2)));
                    recipeIngredientList.add(cursor.getString(cursor.getColumnIndex(KEY_REC3)));
                    recipe.recipeIngredientList = recipeIngredientList;

                    // ATTRIBUTE LIST:
                    List<String> recipeAttributeList = new ArrayList<>();
                    recipeAttributeList.add(cursor.getString(cursor.getColumnIndex(KEY_REC1)));
                    recipeAttributeList.add(cursor.getString(cursor.getColumnIndex(KEY_REC2)));
                    recipeAttributeList.add(cursor.getString(cursor.getColumnIndex(KEY_REC3)));
                    recipe.recipeAttributeList = recipeAttributeList;

                    recipeList.add(recipe);

                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(LOG_TAG, "getAllRecipes(): Error while trying to get recipes from database.");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return recipeList;
    }






    // Called when the database needs to be upgraded.
    // This method will only be called if a database already exists on disk with the same DATABASE_NAME,
    // but the DATABASE_VERSION is different than the version of the database that exists on disk.
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//
//        if (oldVersion != newVersion) {
//
//            // Simplest implementation is to drop all old tables and recreate them
//            db.execSQL("DROP TABLE IF EXISTS " + TABLE_POSTS);
//            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
//            onCreate(db);
//        }
//    }



//
//
//    /**
//     * Creates a empty database on the system and rewrites it with your own database.
//     * */
//    public void initDatabase(Context context) throws IOException {
//
//        boolean databaseExists = checkDatabase(context); // Checks if the database already exists.
//
//        if (!databaseExists){
//
//            //By calling this method and empty database will be created into the default system path
//            //of your application so we are gonna be able to overwrite that database with our database.
//            this.getReadableDatabase();
//
//            try {
//
//                copyDatabase();
//
//            } catch (IOException e) {
//
//                throw new Error("Error copying database");
//
//            }
//        }
//    }
//
//    /**
//     * Check if the database already exist to avoid re-copying the file each time you open the application.
//     * @return true if it exists, false if it doesn't
//     */
//    private boolean checkDatabase(Context context){
//
//        File databasePath = context.getDatabasePath(DATABASE_NAME);
//        return databasePath != null;

//        SQLiteDatabase alchenomiconDatabase = null;
//
//        try {
//            if (databasePath != null) {
//
//
//                databasePath.getPath();
//            }
//
//
//            String myPath = DATABASE_NAME;
//            alchenomiconDatabase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
//
//        } catch(SQLiteException e){
//
//            //database does't exist yet.
//
//        }
//
//        if(alchenomiconDatabase != null){
//
//            alchenomiconDatabase.close();
//
//        }
//
//        return alchenomiconDatabase != null ? true : false;
//    }
//
//    /**
//     * Copies your database from your local assets-folder to the just created empty database in the
//     * system folder, from where it can be accessed and handled.
//     * This is done by transfering bytestream.
//     * */
//    private void copyDatabase(Context context) throws IOException{
//
//        //Open your local db as the input stream
//        InputStream myInput = context.getAssets().open(DATABASE_NAME);
//
//        // Gets the database path.
//        File databasePath = context.getDatabasePath(DATABASE_NAME);
//        if (databasePath != null) {
//
//        }
//
//
//        // Path to the just created empty db
//        String outFileName = DB_PATH + DB_NAME;
//
//        //Open the empty db as the output stream
//        OutputStream myOutput = new FileOutputStream(outFileName);
//
//        //transfer bytes from the inputfile to the outputfile
//        byte[] buffer = new byte[1024];
//        int length;
//        while ((length = myInput.read(buffer))>0){
//            myOutput.write(buffer, 0, length);
//        }
//
//        //Close the streams
//        myOutput.flush();
//        myOutput.close();
//        myInput.close();
//
//    }

//    public void openDataBase() throws SQLException{
//
//        //Open the database
//        String myPath = DB_PATH + DB_NAME;
//        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
//
//    }
//
//
//
//    // open() sets up the SQL database file for reading & writing.
//    public void openDatabase(Context context) throws SQLException {
//
//        context.getApplicationContext().getDatabasePath()
//
//
//        // Path location to the DB file.
//        String TOTAL_PATH = DATABASE_PATH;
//
//        // Attempts to open the SQL file.
//        try {
//            dq8Database = SQLiteDatabase.openDatabase(TOTAL_PATH, null, SQLiteDatabase.OPEN_READONLY);
//        }
//        catch (SQLiteException e) {
//            e.printStackTrace();
//        }
//    }
}
