package com.example.agenda.providers;

import android.app.AlertDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class AgendaSQLHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "BancaDB";
    private static final int DATABASE_VERSION = 1;
    private Context context;

    public AgendaSQLHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(UserSchema.CREATE_TABLE);
        } catch (Exception ex){
            AlertDialog builder = new AlertDialog.Builder(context).setMessage(""+ex).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            db.execSQL(UserSchema.DROP_TABLE);
            onCreate(db);
        } catch (Exception ex){
            Log.e(DATABASE_NAME, "Error upgrade database");
        }
    }
}
