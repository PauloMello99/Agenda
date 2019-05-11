package com.example.agenda.providers;

import java.util.List;

public interface SQLiteGenericDAO<T> {

    long create(T t);
    void update(T t);
    void delete(T t);
    T searchById(long id);
    List<T> searchByName(String name);
    List<T> searchAll();
}
