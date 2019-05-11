package com.example.agenda.models;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.List;

public class Test implements Serializable {

    private long id;
    private String subject;
    private String date;
    private List<String> topics;

    public Test(){}

    public Test(long id, String subject, String date, List<String> topics) {
        this.id = id;
        this.subject = subject;
        this.date = date;
        this.topics = topics;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getTopics() {
        return topics;
    }

    public void setTopics(List<String> topics) {
        this.topics = topics;
    }

    @NonNull
    @Override
    public String toString() {
        return this.subject;
    }
}
