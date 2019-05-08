package com.example.agenda.providers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.agenda.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserDAO implements SQLiteGenericDAO<User> {

    private AgendaSQLHelper helper;

    public UserDAO(Context context) {
        this.helper = new AgendaSQLHelper(context);
    }

    @Override
    public long create(User user) {
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues contentValues = getContentValues(user);
        long id = database.insert(UserSchema.TABLE_NAME,null, contentValues);
        if(id != -1) user.setId(id);
        database.close();
        return id;
    }

    @Override
    public void update(User user) {
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues contentValues = getContentValues(user);
        database.update(UserSchema.TABLE_NAME,contentValues,UserSchema.WHERE_ID_EQUAL,new String[]{String.valueOf(user.getId())});
        database.close();
    }

    @Override
    public void delete(User user)  {
        SQLiteDatabase database = helper.getWritableDatabase();
        database.delete(UserSchema.TABLE_NAME,UserSchema.WHERE_ID_EQUAL,new String[]{String.valueOf(user.getId())});
        database.close();
    }

    @Override
    public User searchById(long id)  {
        SQLiteDatabase database = helper.getReadableDatabase();
        Cursor cursor = database.rawQuery(UserSchema.SELECT_BY_ID, new String[]{String.valueOf(id)});

        if (cursor != null)
            cursor.moveToNext();

        assert cursor != null;
        User user = getUserFromCursor(cursor);

        cursor.close();
        database.close();
        return user;
    }

    @Override
    public List<User> searchByName(String name) {
        List<User> users = new ArrayList<>();
        SQLiteDatabase database = helper.getReadableDatabase();
        Cursor cursor = database.rawQuery(UserSchema.SELECT_BY_NAME, new String[]{String.valueOf(name)});

        if (cursor.moveToFirst()) {
            do {
                User user = getUserFromCursor(cursor);
                users.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return users;
    }

    @Override
    public List<User> searchAll()  {
        List<User> users = new ArrayList<>();
        SQLiteDatabase database = helper.getReadableDatabase();
        Cursor cursor = database.rawQuery(UserSchema.SELECT_ALL,null);
        if (cursor.moveToFirst()) {
            do {
                User user = getUserFromCursor(cursor);
                users.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return users;
    }

    private User getUserFromCursor(Cursor cursor){
        return new User(Long.parseLong(cursor.getString(0)),cursor.getString(1),cursor.getString(2));
    }

    private ContentValues getContentValues(User user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserSchema.COLUMN_NAME,user.getName());
        contentValues.put(UserSchema.COLUMN_DATE,user.getDate());

        return contentValues;
    }
}
