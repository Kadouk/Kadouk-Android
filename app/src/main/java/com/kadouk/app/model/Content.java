package com.kadouk.app.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zoli on 09/10/2018.
 */

public class Content {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("desc")
    private String desc;

    @SerializedName("image")
    private String image;

    public Content(int id, String name, String desc, String image){
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
