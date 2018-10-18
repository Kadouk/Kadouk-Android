package com.kadouk.app.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by jahan on 10/18/18.
 */

public class CatagoryResponse {

    @SerializedName("contents")
    private List<Content> contents;

    @SerializedName("catName")
    private String catName;

    public CatagoryResponse(List<Content> contents, String catName) {
        this.contents = contents;
        this.catName = catName;
    }

    public List<Content> getContents() {
        return contents;
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }
}