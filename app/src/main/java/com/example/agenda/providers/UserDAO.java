package com.example.agenda.providers;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.example.agenda.models.User;

import java.util.List;

public class UserDAO implements SQLiteGenericDAO<User> {

    private AgendaSQLHelper helper;

    public UserDAO(AgendaSQLHelper helper) {
        this.helper = helper;
    }

    @Override
    public long create(User user) throws Exception {
        SQLiteDatabase database = helper.getWritableDatabase();
        ContentValues contentValues = getContentValues(user);
        long id = database.insert(UserSchema.TABLE_NAME,null,contentValues);
        if(id != -1) user.setId(id);
        return id;
    }

    @Override
    public void update(User user) throws Exception {

    }

    @Override
    public void delete(User user) throws Exception {

    }

    @Override
    public User searchById(long id) throws Exception {
        return null;
    }

    @Override
    public User searchByName(String name) throws Exception {
        return null;
    }

    @Override
    public List<User> searchAll() throws Exception {
        return null;
    }
}
