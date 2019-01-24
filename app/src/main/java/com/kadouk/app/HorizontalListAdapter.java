package com.kadouk.app;

/**
 * Created by zoli on 08/16/2018.
 */
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.kadouk.app.model.Contents;

import java.util.List;

public class HorizontalListAdapter extends RecyclerView.Adapter<HorizontalListAdapter.ViewHolder> {

    List<Contents> contents;
    Context adapterContext;
    Typeface typeface;

    public HorizontalListAdapter(Context context, List<Contents> contents) {
        super();
        adapterContext = context;
        this.contents = contents;
        Glide.get(context).clearMemory();
        Log.i("LOADPIC2", String.valueOf(contents));
        typeface = Typeface.createFromAsset(adapterContext.getAssets(), "fonts/B Koodak Bold_0.ttf");
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.cat_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, @SuppressLint("RecyclerView") int position) {

        int appPosition = position * 2;
        viewHolder.txvAppName1.setTypeface(typeface);
        viewHolder.txvAppDesc1.setTypeface(typeface);
        viewHolder.txvAppName2.setTypeface(typeface);
        viewHolder.txvAppDesc2.setTypeface(typeface);
        viewHolder.txvAppCost1.setTypeface(typeface);
        viewHolder.txvAppCost2.setTypeface(typeface);

        viewHolder.txvAppName1.setText(contents.get(appPosition).getName());
        viewHolder.txvAppDesc1.setText(contents.get(appPosition).getDesc());
        viewHolder.txvAppCost1.setText(contents.get(appPosition).getCost());
        String costUp = String.valueOf(contents.get(appPosition).getCost());

        if(!costUp.equals("رایگان"))
            viewHolder.imgDownloadIconUp.setImageResource(R.mipmap.ic_purchase);
        else
            viewHolder.imgDownloadIconUp.setImageResource(R.mipmap.ic_download);

        String imageUrl = "http://kadouk.com/kadouk/public/api/download/image/" + contents.get(appPosition).getImage();
        Log.i("LOADPIC", imageUrl + appPosition);

        Glide
                .with(adapterContext)
                .load(imageUrl)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(viewHolder.imgApp1);

        //////////////////////////////////////////////////////////////////////////
        viewHolder.txvAppName2.setText(contents.get(appPosition + 1).getName());
        viewHolder.txvAppDesc2.setText(contents.get(appPosition + 1).getDesc());
        viewHolder.txvAppCost2.setText(contents.get(appPosition + 1).getCost());
        String costDown = String.valueOf(contents.get(appPosition+1).getCost());

        if(!costDown.equals("رایگان"))
            viewHolder.imgDownloadIconDown.setImageResource(R.mipmap.ic_purchase);
        else
            viewHolder.imgDownloadIconDown.setImageResource(R.mipmap.ic_download);

        String imageUrl2 = "http://kadouk.com/kadouk/public/api/download/image/" + contents.get(appPosition + 1).getImage();
        Log.i("LOADPIC", imageUrl + appPosition+1 + "  "+ contents.size());

        Glide
                .with(adapterContext)
                .load(imageUrl2)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(viewHolder.imgApp2);

        Log.e("adapter", String.valueOf(position));
    }

    @Override
    public int getItemCount() {
        return contents.size()/2;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgApp1, imgApp2, imgDownloadIconUp, imgDownloadIconDown;
        TextView txvAppName1, txvAppDesc1, txvAppName2, txvAppDesc2, txvAppCost1, txvAppCost2;
        private ItemClickListener clickListener;

        public ViewHolder(View itemView) {

            super(itemView);
            imgApp1 = itemView.findViewById(R.id.img_app1);
            txvAppName1 = itemView.findViewById(R.id.txv_app_name1);
            txvAppDesc1 = itemView.findViewById(R.id.txv_app_desc1);
            txvAppCost1 = itemView.findViewById(R.id.txv_app_cost1);
            imgDownloadIconUp = itemView.findViewById(R.id.download_icon_up);

            imgApp2 = itemView.findViewById(R.id.img_app2);
            txvAppName2 = itemView.findViewById(R.id.txv_app_name2);
            txvAppDesc2 = itemView.findViewById(R.id.txv_app_desc2);
            txvAppCost2 = itemView.findViewById(R.id.txv_app_cost2);
            imgDownloadIconDown = itemView.findViewById(R.id.download_icon_down);

            imgApp1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int onClickPosition = getAdapterPosition()*2;
                    if(onClickPosition != RecyclerView.NO_POSITION){

                        Bundle appData = new Bundle();
                        appData.putString("name", contents.get(onClickPosition).getName());
                        appData.putString("appId", String.valueOf(contents.get(onClickPosition).getId()));
                        ProductPageFragment frag = new ProductPageFragment();
                        frag.setArguments(appData);

                        MainActivity mainActivity = (MainActivity) adapterContext;
                        mainActivity.backStackGame = "Game1";
                        mainActivity.addFragmentOnTop(frag);

                        Log.i("Click","shomare " + onClickPosition + " - " + contents.get(onClickPosition).getName());
                    }
                }
            });
            imgApp2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int onClickPosition = getAdapterPosition()*2+1;
                    if(onClickPosition != RecyclerView.NO_POSITION){

                        Bundle appData = new Bundle();
                        appData.putString("name", contents.get(onClickPosition).getName());
                        appData.putString("appId", String.valueOf(contents.get(onClickPosition).getId()));
                        ProductPageFragment frag = new ProductPageFragment();
                        frag.setArguments(appData);

                        MainActivity mainActivity = (MainActivity) adapterContext;
                        mainActivity.backStackGame = "Game1";
                        mainActivity.addFragmentOnTop(frag);

                        Log.i("Click","shomare " + onClickPosition + " - " + contents.get(onClickPosition).getName());
                   }
                }
            });
        }
    }
}