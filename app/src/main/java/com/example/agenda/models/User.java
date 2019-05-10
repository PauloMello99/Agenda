package com.example.agenda.models;

import java.io.Serializable;

public class User implements Serializable {

    private long id;
    private String name;
    private String date;
    private String phone;
    private String address;
    private String url;
    private String photoUri;

    public User() {
    }

    public User(long id, String name, String date, String phone, String address, String url, String photoUri) {
        this.id = id;
        this.name = name;
        this.date = date;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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