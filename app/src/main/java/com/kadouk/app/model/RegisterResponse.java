package com.kadouk.app.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zoli on 08/09/2018.
 */

public class RegisterResponse {

    @SerializedName("token")
    private String token;

    @SerializedName("name")
    private String name;

    @SerializedName("status")
    private String status;

    @SerializedName("error")
    private String error;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
