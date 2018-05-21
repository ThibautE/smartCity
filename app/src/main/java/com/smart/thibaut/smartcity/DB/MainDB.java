package com.smart.thibaut.smartcity.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MainDB extends SQLiteOpenHelper {

    private static final String SQL_CREATE_ENTRIES =
            "CREATE  TABLE " + PersonDB.FeedEntry.TABLE_NAME + " ("  +
                    PersonDB.FeedEntry.COLUMN_NAME + " TEXT, " +
                    PersonDB.FeedEntry.COLUMN_FIRSTNAME + " TEXT, " +
                    PersonDB.FeedEntry.COLUMN_BIRTHDAY + " TEXT, " +
                    PersonDB.FeedEntry.COLUMN_GENDER + " TEXT, " +
                    PersonDB.FeedEntry.COLUMN_CITY + " TEXT," +
                    PersonDB.FeedEntry.COLUMN_SIZE + " REAL, " +
                    PersonDB.FeedEntry.COLUMN_WEIGHT + " REAL) ; ";
    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + PersonDB.FeedEntry.TABLE_NAME;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "DatabaseHandler.db";

    public MainDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i("CREATE","NEW CREATE");
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
        ContentValues values = new ContentValues();
        values.put(PersonDB.FeedEntry.COLUMN_NAME,"Etienne");
        values.put(PersonDB.FeedEntry.COLUMN_FIRSTNAME,"Thibaut");
        values.put(PersonDB.FeedEntry.COLUMN_CITY,"Montpellier");
        sqLiteDatabase.insert(PersonDB.FeedEntry.TABLE_NAME,null,values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }

    public void onDowngrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        onUpgrade(sqLiteDatabase, oldVersion, newVersion);
    }
}