package com.kadouk.app.model;

import com.google.gson.annotations.SerializedName;

public class AppUpdate {

    @SerializedName("update")
    private String update;

    public String getStatus() {
        return update;
    }

    public void setStatus(String status) {
        this.update = status;
    }
}
