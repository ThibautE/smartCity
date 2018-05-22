package hmin202.smart.thibaut.smartcity.DB;

import android.provider.BaseColumns;


public final class CityDB {

    private CityDB(){}

    public static class FeedEntry implements BaseColumns{
        public static final String TABLE_NAME = "person";
        public static final String COLUMN_CITY = "city";
    }
}