package com.kadouk.app;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.Handler;
import android.os.Looper;

import com.downloader.PRDownloader;
import com.kadouk.app.db.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Demonstrates LiveData functionality.
 */

public class LiveDataDownloadViewModel extends ViewModel {
    private static final int ONE_SECOND = 10;

    private MutableLiveData<List<Post>> post = new MutableLiveData<>();


    public LiveDataDownloadViewModel() {

        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                List<Post> p = new ArrayList<>();

                for(int i = 0; i< PRDownloader.getAll().length; i++) {
                    if (PRDownloader.getAll()[i] != null) {
                        long progressPercent = 0;

                        if(PRDownloader.getAll()[i].getTotalBytes()>0) {
                            progressPercent = PRDownloader.getAll()[i].getDownloadedBytes() * 100 /
                                    PRDownloader.getAll()[i].getTotalBytes();
                        }

                        p.add(new Post(PRDownloader.getAll()[i].getFileName()+"",
                                progressPercent+"",3));

                    }

                }

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        post.setValue(p);

                    }
                });
            }
        }, ONE_SECOND, ONE_SECOND);

   }

    public LiveData<List<Post>> getElapsedTime() {
        return post;
    }
}
