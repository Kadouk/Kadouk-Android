package com.kadouk.app;

import android.app.Application;

import com.downloader.PRDownloader;
import com.downloader.PRDownloaderConfig;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;

/**
 * Created by zoli on 01/17/2019.
 */

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

        PRDownloaderConfig config = PRDownloaderConfig.newBuilder()
                .setDatabaseEnabled(true)
                .build();
        PRDownloader.initialize(this, config);


        }
}