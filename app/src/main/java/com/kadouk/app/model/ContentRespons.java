package com.kadouk.app.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by zoli on 09/10/2018.
 */

public class ContentRespons {

    @SerializedName("contents")
    private List<Contents> contents;

    public List<Contents> getContents() {
        return contents;
    }

    public void setContents(List<Contents> contents) {
        this.contents = contents;
    }
}
