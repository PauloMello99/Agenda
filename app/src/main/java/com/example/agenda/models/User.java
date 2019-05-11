package com.example.agenda.models;

import android.support.annotation.Nullable;

import java.io.Serializable;

public class User implements Serializable {

    private long id;
    private String name;
    private double score;
    private String phone;
    private String address;
    private String url;
    private String photoUri;

    public User() {
    }

    public User(long id, String name, double score, String phone, String address, String url,@Nullable String photoUri) {
        this.id = id;
        this.name = name;
        this.score = score;
        this.phone = phone;
        this.address = address;
        this.url = url;
        this.photoUri = photoUri;
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

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }
}