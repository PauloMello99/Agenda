package com.example.agenda.providers;

public class UserSchema {

    private UserSchema(){}

    static final String TABLE_NAME = "user";
    static final String COLUMN_ID = "_id";
    static final String COLUMN_NAME = "name";
    static final String COLUMN_SCORE = "score";
    static final String COLUMN_PHONE = "phone";
    static final String COLUMN_ADDRESS = "address";
    static final String COLUMN_URL = "url";
    static final String COLUMN_PHOTO_URI = "photo_uri";

    static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + COLUMN_NAME + " TEXT NOT NULL, "
            + COLUMN_SCORE + " DOUBLE NOT NULL, "
            + COLUMN_PHONE + " TEXT NOT NULL, "
            + COLUMN_ADDRESS + " TEXT NOT NULL, "
            + COLUMN_URL + " TEXT NOT NULL, "
            + COLUMN_PHOTO_URI + " TEXT"
            +");";

    static final String WHERE_ID_EQUAL = COLUMN_ID + " = ? ";
    static final String WHERE_NAME_EQUAL = COLUMN_NAME + " = ? ";
    static final String WHERE_PHONE_EQUAL = COLUMN_PHONE + " = ? ";
    static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    static final String SELECT_ALL = "SELECT * FROM " + TABLE_NAME;
    static final String SELECT_BY_ID = "SELECT * FROM " + TABLE_NAME + " WHERE " + WHERE_ID_EQUAL;
    static final String SELECT_BY_NAME = "SELECT * FROM " + TABLE_NAME + " WHERE " + WHERE_NAME_EQUAL;
    static final String SELECT_BY_PHONE = "SELECT * FROM " + TABLE_NAME + " WHERE " + WHERE_PHONE_EQUAL;
}
