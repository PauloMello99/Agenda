package com.example.agenda.providers;

public class TestSchema {

    public TestSchema() {
    }

    static final String TABLE_NAME = "test";
    static final String COLUMN_ID = "_id";
    static final String COLUMN_SUBJECT = "subject";
    static final String COLUMN_DATE = "date";
    static final String COLUMN_TOPICS = "topics";

    static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
            + COLUMN_SUBJECT + " TEXT NOT NULL, "
            + COLUMN_DATE + " TEXT NOT NULL, "
            + COLUMN_TOPICS + " TEXT NOT NULL"
            +");";

    static final String WHERE_ID_EQUAL = COLUMN_ID + " = ? ";
    static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    static final String SELECT_ALL = "SELECT * FROM " + TABLE_NAME;
    static final String SELECT_BY_ID = "SELECT * FROM " + TABLE_NAME + " WHERE " + WHERE_ID_EQUAL;
}
