package com.example.agenda.providers;

public class UserSchema {

    public static  final String TABLE_NAME = "user";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DATE = "date";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_NAME + " INTEGER NOT NULL, "
            + COLUMN_DATE + " TEXT NOT NULL);";

    public static final String WHERE_ID_EQUAL = COLUMN_ID + " = ? ";
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    public static final String SELECT_ALL = "SELECT * FROM " + TABLE_NAME;
    public static final String SELECT_BY_ID = "SELECT * FROM " + TABLE_NAME + " WHERE " + WHERE_ID_EQUAL;
}
