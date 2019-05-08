package com.example.agenda.models;

import java.io.Serializable;

public class User implements Serializable {

    private long id;
    private String name;
    private String date;


    public User(long id, String name, String date) {
        this.id = id;
        this.name = name;
        this.date = date;
    }

    public User(){
        this(0,"","");
    }

    public User(String name, String date) {
       this(0,name,date);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
