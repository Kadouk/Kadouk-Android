package com.kadouk.app;

import android.annotation.SuppressLint;
import android.app.Application;
import android.support.v4.app.Fragment;

import com.kadouk.app.model.CategoryResponse;

import java.util.List;

@SuppressLint("Registered")
public class Globals extends Application {

    public static Fragment active ;

    public static String number;

    public static String Token;

    public static List<CategoryResponse> allCategories;

    public static Fragment getActive() {
        return active;
    }

    public static void setActive(Fragment active) {
        Globals.active = active;
    }

    public static String getNumber() {
        return number;
    }

    public static void setNumber(String number) {
        Globals.number = number;
    }

    public static String getToken() {
        return Token;
    }

    public static void setToken(String Token) {
        Globals.Token = Token;
    }

    public static List<CategoryResponse> getAllCategories() {
        return allCategories;
    }

    public static void setAllCategories(List<CategoryResponse> allCategories) {
        Globals.allCategories = allCategories;
    }
}