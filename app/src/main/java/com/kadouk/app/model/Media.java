package com.kadouk.app.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zoli on 11/05/2018.
 */

import com.google.gson.annotations.SerializedName;

public class Media {


    @SerializedName("path")
    private String path;

    public Media(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}

