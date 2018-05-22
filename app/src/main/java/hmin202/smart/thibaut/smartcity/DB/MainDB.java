package hmin202.smart.thibaut.smartcity.DB;

import android.content.ContentValues;
import android.content.Context;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MainDB extends SQLiteOpenHelper {

    private static final String SQLite_CREATE =
            "CREATE  TABLE " + CityDB.FeedEntry.TABLE_NAME + " ("
                            + CityDB.FeedEntry.COLUMN_CITY + " TEXT);";

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "city.db";

    private static final String SQLite_DELETE =
            "DROP TABLE IF EXISTS " + CityDB.FeedEntry.TABLE_NAME;

    public MainDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQLite_CREATE);
        ContentValues values = new ContentValues();
        values.put(CityDB.FeedEntry.COLUMN_CITY,"");
        sqLiteDatabase.insert(CityDB.FeedEntry.TABLE_NAME,null,values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(SQLite_DELETE);
        onCreate(sqLiteDatabase);
    }

}