package com.example.agenda.providers;

import com.example.agenda.models.Test;

import java.util.List;

public class TestDAO implements SQLiteGenericDAO<Test> {

    @Override
    public long create(Test test){
        return 0;
    }

    @Override
    public void update(Test test)  {

    }

    @Override
    public void delete(Test test) {

    }

    @Override
    public Test searchById(long id) {
        return null;
    }

    @Override
    public List<Test> searchByName(String name)  {
        return null;
    }

    @Override
    public List<Test> searchAll()  {
        return null;
    }
}
