package com.kadouk.app;

import android.app.Application;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;

/**
 * Created by zoli on 01/17/2019.
 */

// tuye in class font emuno init mikonim vase kole app,
// Font mogheye baz shodane app baz mishe,
// font ro az inja taghir bedim tu kole app taghir mikone,
// tu har activity e ke mikhaym fontesh custom ma beshe bayad ye tabe ezafe konim,
// tu MainActivity moshakhasesh mikonam vasat ke kodum tabast, akhare code MainActivity ro bebin
// bad az inke ino kamel check kardi SearchFragment ro check kon.
public class Font extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
            super.onCreate();
            ViewPump.init(ViewPump.builder()
                    .addInterceptor(new CalligraphyInterceptor(
                            new CalligraphyConfig.Builder()
                                    .setDefaultFontPath("fonts/B Koodak Bold_0.ttf")
                                    .setFontAttrId(R.attr.fontPath)
                                    .build()))
                    .build());
        }
}