package com.kadouk.app;

import android.annotation.SuppressLint;
import android.app.Application;
import android.support.v4.app.Fragment;

@SuppressLint("Registered")
public class Globals extends Application {

    public static Fragment active ;

    public static Fragment getActive() {
        return active;
    }

    public static void setActive(Fragment active) {
        Globals.active = active;
    }
}