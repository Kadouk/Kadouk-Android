package com.kadouk.app.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by zoli on 02/07/2019.
 */

public class AllCategoryResponse {

    @SerializedName("cats")
    private List<CategoryResponse> categoryRespons;

    public List<CategoryResponse> getCategoryRespons() {
        return categoryRespons;
    }

    public void setCategoryRespons(List<CategoryResponse> categoryRespons) {
        this.categoryRespons = categoryRespons;
    }
}
