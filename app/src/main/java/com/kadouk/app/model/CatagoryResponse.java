package com.kadouk.app.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by jahan on 10/18/18.
 */

public class CatagoryResponse {

    @SerializedName("contents")
    private List<Contents> contents;

    @SerializedName("catName")
    private String catName;

    public CatagoryResponse(List<Contents> contents, String catName) {
        this.contents = contents;
        this.catName = catName;
    }

    public List<Contents> getContents() {
        return contents;
    }

    public void setContents(List<Contents> contents) {
        this.contents = contents;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }
}