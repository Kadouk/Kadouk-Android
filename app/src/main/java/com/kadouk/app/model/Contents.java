package com.kadouk.app.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by zoli on 11/06/2018.
 */

public class Contents {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("desc")
    private String desc;

    @SerializedName("report")
    private int report;

    @SerializedName("age")
    private int age;

    @SerializedName("tag")
    private String tag;

    @SerializedName("size")
    private String size;

    @SerializedName("cost")
    private String cost;

    @SerializedName("file")
    private String file;

    @SerializedName("image")
    private String image;

    @SerializedName("media")
    private List<Screenshots> Screenshots;


    public Contents(int id, String name, String desc, int report, int age, String tag, String size, String cost, String file, String image, List<com.kadouk.app.model.Screenshots> screenshots) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.report = report;
        this.age = age;
        this.tag = tag;
        this.size = size;
        this.cost = cost;
        this.file = file;
        this.image = image;
        Screenshots = screenshots;
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

    public int getReport() {
        return report;
    }

    public void setReport(int report) {
        this.report = report;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<com.kadouk.app.model.Screenshots> getScreenshots() {
        return Screenshots;
    }

    public void setScreenshots(List<com.kadouk.app.model.Screenshots> screenshots) {
        Screenshots = screenshots;
    }
}