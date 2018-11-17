package com.kadouk.app.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zoli on 11/05/2018.
 */

import com.google.gson.annotations.SerializedName;

public class Media {


    @SerializedName("path")
    private int path;

    public Media(int path) {
        this.path = path;
    }

    public int getPath() {
        return path;
    }

    public void setPath(int path) {
        this.path = path;
    }
}

