package com.kadouk.app;

/**
 * Created by zoli on 08/16/2018.
 */
import android.view.View;

public interface ItemClickListener {

    void onClick(View view, int position, boolean isLongClick);
}