package com.huhx0015.dragonalchenomicon;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

/** DRAGONALCHENOMICON
 * DEVELOPER: Michael Yoon Huh (huhx0015)
 * LAST UPDATED: 5/30/2014 */

// DQ8DB class is responsible for the SQL management of the Dragon Quest VIII alchemy recipe
// database. Also responsible for creating the initial SQL database for the Dragon
// Quest VIII alchemy recipe list.
public class DQ8DB extends SQLiteOpenHelper {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // SQL database instance variables.
    private final Context dqContext;
    private SQLiteDatabase dqDatabase;

    // Database file for Dragon Quest VIII recipes.
    private static String DB_NAME = "dq8_recipe.db";
    private static String DB_PATH; //"/data/data/com.huhx0015.dragonalchenomicon/databases/";
    public SQLiteDatabase dq8Database;

    // SQL database field variables.
    private static final String DATABASE_NAME = "DQ8_RECIPE";
    public static final String DATABASE_TABLE = "dq8_recipe";
    public static final int DATABASE_VERSION = 1;
    public static final String KEY_ROWID = "KEY_ROWID";
    public static final String KEY_ITEM = "KEY_ITEM";
    public static final String KEY_CATEGORY = "KEY_CATEGORY";
    public static final String KEY_DUPE = "KEY_DUPE";
    public static final String KEY_REC1 = "KEY_REC1";
    public static final String KEY_REC2 = "KEY_REC2";
    public static final String KEY_REC3 = "KEY_REC3";
    public static final String KEY_DESC = "KEY_DESC";
    public static final String KEY_ATT1 = "KEY_ATT1";
    public static final String KEY_ATT2 = "KEY_ATT2";
    public static final String KEY_ATT3 = "KEY_ATT3";
    public static final String KEY_USEDBY = "KEY_USEDBY";

    /** CONSTRUCTOR ____________________________________________________________________________

    /*
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     **/
    public DQ8DB(Context context) {
        super(context, DB_NAME, null, 1);

        DB_PATH = context.getDatabasePath(DB_NAME).getPath();

        this.dqContext = context;
    }

    /** OVERRIDE METHODS _______________________________________________________________________ **/

    // onCreate() creates the initial SQL database.
    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    // onUpgrade() is responsible for updating the table if it already exists.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(db);
    }

    @Override
    public synchronized void close() {

        if(dq8Database != null)
            dq8Database.close();

        super.close();

    }

    /** SQL FUNCTIONALITY _____________________________________________________________________  **/

    private void initializeDQDatabase(SQLiteDatabase db) {

        // Creates the SQL database with the Dragon Quest VIII alchemy recipe fields.
        db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" +
                        KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        KEY_ITEM + " TEXT NOT NULL, " +
                        KEY_CATEGORY + " TEXT NOT NULL, " +
                        KEY_DUPE + " TEXT NOT NULL, " +
                        KEY_REC1 + " TEXT NOT NULL, " +
                        KEY_REC2 + " TEXT NOT NULL, " +
                        KEY_REC3 + " TEXT NOT NULL, " +
                        KEY_DESC + " TEXT NOT NULL, " +
                        KEY_ATT1 + " TEXT NOT NULL, " +
                        KEY_ATT2 + " TEXT NOT NULL, " +
                        KEY_ATT3 + " TEXT NOT NULL, " +
                        KEY_USEDBY + " TEXT NOT NULL);"

        );

    }

    // open() sets up the SQL database file for reading & writing.
    public void openDQDatabase() throws SQLException {

        // Path location to the DB file.
        String TOTAL_PATH = DB_PATH;

        // Attempts to open the SQL file.
        try {
            dq8Database = SQLiteDatabase.openDatabase(TOTAL_PATH, null, SQLiteDatabase.OPEN_READONLY);
        }
        catch (SQLiteException e) {
            e.printStackTrace();
        }
    }

    // copy(): Copies the SQL database.
    public void copyDQDatabase() throws IOException {

        //Open your local db as the input stream
        InputStream myInput = dqContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    /** ADDITIONAL FUNCTIONALITY _______________________________________________________________ **/

    // createEntry() is used to create a new database entry for the alchemy list.
    public long createEntry(String item, String rec1, String rec2, String rec3, String desc,
                            String att1, String att2, String att3, String usedBy) {

        // Creates the content values and stores the strings into the table.
        ContentValues cv = new ContentValues();
        cv.put(KEY_ITEM, item);
        cv.put(KEY_REC1, rec1);
        cv.put(KEY_REC2, rec2);
        cv.put(KEY_REC3, rec3);
        cv.put(KEY_DESC, desc);
        cv.put(KEY_ATT1, att1);
        cv.put(KEY_ATT2, att2);
        cv.put(KEY_ATT3, att3);
        cv.put(KEY_USEDBY, usedBy);

        // Returns the new entry and places it into the table.
        return dq8Database.insert(DATABASE_TABLE, null, cv);
    }

    // getData() is used to retrieve alchemy recipe data from the SQL database.
    public String getData() {

        String[] columns = new String[] {
                KEY_ROWID,
                KEY_ITEM,
                KEY_CATEGORY,
                KEY_DUPE,
                KEY_REC1,
                KEY_REC2,
                KEY_REC3,
                KEY_DESC,
                KEY_ATT1,
                KEY_ATT2,
                KEY_ATT3,
                KEY_USEDBY};

        Cursor c = dq8Database.query(DATABASE_TABLE, columns, null, null, null, null, null);
        String result = "";

        int iRow = c.getColumnIndex(KEY_ROWID);
        int iItem = c.getColumnIndex(KEY_ITEM);
        int iCate = c.getColumnIndex(KEY_CATEGORY);
        int iDupe = c.getColumnIndex(KEY_DUPE);
        int iRec1 = c.getColumnIndex(KEY_REC1);
        int iRec2 = c.getColumnIndex(KEY_REC2);
        int iRec3 = c.getColumnIndex(KEY_REC3);
        int iDesc = c.getColumnIndex(KEY_DESC);
        int iAtt1 = c.getColumnIndex(KEY_ATT1);
        int iAtt2 = c.getColumnIndex(KEY_ATT2);
        int iAtt3 = c.getColumnIndex(KEY_ATT3);
        int iUsedBy = c.getColumnIndex(KEY_USEDBY);

        // Moves the cursor, reads data, then moves cursor to next field.
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            result = result + c.getString(iRow) + " " + c.getString(iItem) + " " + c.getString(iCate)
                    + " " + c.getString(iDupe) + " " + c.getString(iRec1) + " " + " " + c.getString(iRec2)
                    + " " + c.getString(iRec3) + " " + c.getString(iDesc) + " " + c.getString(iAtt1)
                    + " " + c.getString(iAtt2) + " " + c.getString(iAtt3) + " " + c.getString(iUsedBy) + "\n";
        }

        return null;
    }


    // getData() is used to retrieve alchemy recipe data from the SQL database.
    public String findRecipeData() {

        String[] columns = new String[] {
                KEY_ROWID,
                KEY_ITEM,
                KEY_CATEGORY,
                KEY_DUPE,
                KEY_REC1,
                KEY_REC2,
                KEY_REC3,
                KEY_DESC,
                KEY_ATT1,
                KEY_ATT2,
                KEY_ATT3,
                KEY_USEDBY};

        Cursor c = dq8Database.query(DATABASE_TABLE, columns, null, null, null, null, null);
        String result = "";

        int iRow = c.getColumnIndex(KEY_ROWID);
        int iItem = c.getColumnIndex(KEY_ITEM);
        int iCate = c.getColumnIndex(KEY_CATEGORY);
        int iDupe = c.getColumnIndex(KEY_DUPE);
        int iRec1 = c.getColumnIndex(KEY_REC1);
        int iRec2 = c.getColumnIndex(KEY_REC2);
        int iRec3 = c.getColumnIndex(KEY_REC3);
        int iDesc = c.getColumnIndex(KEY_DESC);
        int iAtt1 = c.getColumnIndex(KEY_ATT1);
        int iAtt2 = c.getColumnIndex(KEY_ATT2);
        int iAtt3 = c.getColumnIndex(KEY_ATT3);
        int iUsedBy = c.getColumnIndex(KEY_USEDBY);

        // Moves the cursor, reads data, then moves cursor to next field.
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            result = result + c.getString(iRow) + " " + c.getString(iItem) + " " + c.getString(iCate)
                    + " " + c.getString(iDupe) + " " + c.getString(iRec1) + " " + " " + c.getString(iRec2)
                    + " " + c.getString(iRec3) + " " + c.getString(iDesc) + " " + c.getString(iAtt1)
                    + " " + c.getString(iAtt2) + " " + c.getString(iAtt3) + " " + c.getString(iUsedBy) + "\n";
        }

        return null;
    }

    // getItem() is responsible for reading the item name from SQL data table.
    public String getItem(long l) throws SQLException {

        String[] columns = new String[] {
                KEY_ROWID,
                KEY_ITEM,
                KEY_CATEGORY,
                KEY_DUPE,
                KEY_REC1,
                KEY_REC2,
                KEY_REC3,
                KEY_DESC,
                KEY_ATT1,
                KEY_ATT2,
                KEY_ATT3,
                KEY_USEDBY
        };

        Cursor c = dq8Database.query(DATABASE_TABLE, columns, KEY_ROWID + "=" + l, null, null, null, null);

        if (c != null) {
            c.moveToFirst();
            String item = c.getString(1);
            return item;
        }

        return null;
    }

    // getRecipe() is responsible for reading the recipes from the SQL data table.
    public String getRecipe(long l) throws SQLException {

        String[] columns = new String[] {
                KEY_ROWID,
                KEY_ITEM,
                KEY_CATEGORY,
                KEY_DUPE,
                KEY_REC1,
                KEY_REC2,
                KEY_REC3,
                KEY_DESC,
                KEY_ATT1,
                KEY_ATT2,
                KEY_ATT3,
                KEY_USEDBY
        };

        Cursor c = dq8Database.query(DATABASE_TABLE, columns, KEY_ROWID + "=" + l, null, null, null, null);

        if (c != null) {
            c.moveToFirst();
            String recipe = c.getString(2);
            return recipe;
        }

        return null;
    }

    // updateEntry() updates the SQL table entries.
    public void updateEntry(long lRow, String dItem, String dRecipe1, String dRecipe2, String dRecipe3) throws SQLException {

        ContentValues cvUpdate = new ContentValues();
        cvUpdate.put(KEY_ITEM, dItem);
        cvUpdate.put(KEY_REC1, dRecipe1);
        cvUpdate.put(KEY_REC2, dRecipe2);
        cvUpdate.put(KEY_REC3, dRecipe3);

        dq8Database.update(DATABASE_TABLE, cvUpdate, KEY_ROWID + "=" + lRow, null);
    }

    // deleteEntry() removes SQL table entries.
    public void deleteEntry(long lRow1) throws SQLException {

        dq8Database.delete(DATABASE_TABLE, KEY_ROWID + "=" + lRow1, null);
    }
}
