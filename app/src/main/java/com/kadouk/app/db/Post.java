package com.kadouk.app.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by guendouz on 15/02/2018.
 */

@Entity
public class Post {
    @PrimaryKey(autoGenerate = false)
    private int id;
    private String title;
    private String content;
    private int dlID;

    public Post() {
    }

    @Ignore
    public Post(String title, String content, int id) {
        this.title = title;
        this.content = content;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getDlID() {
        return dlID;
    }

    public void setDlID(int dlID) {
        this.dlID = dlID;
    }
}
