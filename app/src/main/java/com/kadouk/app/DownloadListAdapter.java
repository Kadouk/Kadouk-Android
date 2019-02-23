package com.kadouk.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.kadouk.app.model.Contents;

import java.util.List;

/**
 * Created by zoli on 02/23/2019.
 */

public class DownloadListAdapter extends RecyclerView.Adapter<DownloadListAdapter.ViewHolder> {

    List<Contents> contents;
    Context adapterContext;

    public DownloadListAdapter(Context context, List<Contents> contents) {
        super();
        adapterContext = context;
        this.contents = contents;
        Glide.get(context).clearMemory();
    }

    @Override
    public DownloadListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_cat, viewGroup, false);
        DownloadListAdapter.ViewHolder viewHolder = new DownloadListAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DownloadListAdapter.ViewHolder viewHolder, @SuppressLint("RecyclerView") int position) {

        viewHolder.txvAppName.setText(contents.get(position).getName());
        viewHolder.txvAppDesc.setText(contents.get(position).getDesc());
       // viewHolder.downloadProgressBar.setProgress(20);

        String imageUrl = "http://kadouk.com/kadouk/public/api/download/image/"
                + contents.get(position).getImage();

        Log.i("LOADPIC", imageUrl + position);

        Glide
                .with(adapterContext)
                .load(imageUrl)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(viewHolder.imgApp);
    }

    @Override
    public int getItemCount() {
        return contents.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgApp;
        TextView txvAppName, txvAppDesc;
        ProgressBar downloadProgressBar;
        private ItemClickListener clickListener;

        public ViewHolder(View itemView) {

            super(itemView);
            imgApp = itemView.findViewById(R.id.img_app);
            txvAppName = itemView.findViewById(R.id.txv_app_name);
            txvAppDesc = itemView.findViewById(R.id.txv_app_desc);
            downloadProgressBar = itemView.findViewById(R.id.download_progerss);
//            downloadProgressBar.setMax(100);
            ;


            imgApp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int onClickPosition = getAdapterPosition();
                    if(onClickPosition != RecyclerView.NO_POSITION){

                        Bundle appData = new Bundle();
                        appData.putString("name", contents.get(onClickPosition).getName());
                        appData.putString("appId",
                                String.valueOf(contents.get(onClickPosition).getId()));
                        ProductPageFragment frag = new ProductPageFragment();
                        frag.setArguments(appData);

                        MainActivity mainActivity = (MainActivity) adapterContext;
                        mainActivity.backStackGame = "Game1";
                        mainActivity.addFragmentOnTop(frag);

                        Log.i("Click","shomare " + onClickPosition + " - "
                                + contents.get(onClickPosition).getName());
                    }
                }
            });
        }
    }
}