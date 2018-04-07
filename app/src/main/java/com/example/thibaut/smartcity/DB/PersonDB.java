package com.example.thibaut.smartcity.DB;

import android.provider.BaseColumns;


public final class PersonDB {

    private PersonDB(){}

    public static class FeedEntry implements BaseColumns{
        public static final String TABLE_NAME = "person";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_FIRSTNAME = "firstname";
        public static final String COLUMN_BIRTHDAY = "birthday";
        public static final String COLUMN_GENDER = "gender";
        public static final String COLUMN_CITY = "city";
        public static final String COLUMN_WEIGHT = "weight";
        public static final String COLUMN_SIZE = "size";

    }
}