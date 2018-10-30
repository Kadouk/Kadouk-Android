package com.kadouk.app;

/**
 * Created by zoli on 08/16/2018.
 */
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.kadouk.app.model.Content;

import java.util.List;

public class HorizontalListAdapter extends RecyclerView.Adapter<HorizontalListAdapter.ViewHolder> {

    List<Content> content;
    Context adapterContext;
    int appPosition = 0;

    public HorizontalListAdapter(Context context, List<Content> content) {
        super();
        adapterContext = context;
        this.content = content;
        Glide.get(context).clearMemory();
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

        viewHolder.txvAppName1.setText(content.get(appPosition).getName());
        viewHolder.txvAppDesc1.setText(content.get(appPosition).getDesc());
        String imageUrl = "http://kadouk.com/kadouk/public/api/download/image/" + content.get(appPosition).getImage();
        Log.i("LOADPIC", imageUrl + appPosition);

        Glide
                .with(adapterContext)
                .load(imageUrl)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(viewHolder.imgApp1);

        //////////////////////////////////////////////////////////////////////////
        viewHolder.txvAppName2.setText(content.get(appPosition + 1).getName());
        viewHolder.txvAppDesc2.setText(content.get(appPosition + 1).getDesc());
        String imageUrl2 = "http://kadouk.com/kadouk/public/api/download/image/" + content.get(appPosition + 1).getImage();
        Log.i("LOADPIC", imageUrl + appPosition+1 + "  "+ content.size());

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
        return content.size()/2;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgApp1, imgApp2;
        TextView txvAppName1, txvAppDesc1, txvAppName2, txvAppDesc2;
        private ItemClickListener clickListener;

        public ViewHolder(View itemView) {

            super(itemView);
            imgApp1 = itemView.findViewById(R.id.img_app1);
            txvAppName1 = itemView.findViewById(R.id.txv_app_name1);
            txvAppDesc1 = itemView.findViewById(R.id.txv_app_desc1);

            imgApp2 = itemView.findViewById(R.id.img_app2);
            txvAppName2 = itemView.findViewById(R.id.txv_app_name2);
            txvAppDesc2 = itemView.findViewById(R.id.txv_app_desc2);

            imgApp1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int onClickPosition = getAdapterPosition()*2;
                    if(onClickPosition != RecyclerView.NO_POSITION){
                        Intent intent = new Intent(adapterContext,ShowCategoryActivity.class);
                        intent.putExtra("Name",content.get(onClickPosition).getName());
                        intent.putExtra("Id",content.get(onClickPosition).getId());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        adapterContext.startActivity(intent);
                        Log.i("Click","shomare " + onClickPosition + " - " + content.get(onClickPosition).getName());
                    }
                }
            });
            imgApp2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int onClickPosition = getAdapterPosition()*2+1;
                    if(onClickPosition != RecyclerView.NO_POSITION){
                        Bundle i = new Bundle();
                        i.putString("name", "Emmanuel");
                        ShowCategoryActivity frag = new ShowCategoryActivity();
                        frag.setArguments(i);
                        MainActivity mainActivity = (MainActivity) adapterContext;
                        mainActivity.backStackGame = "Game1";
                        mainActivity.addFragmentOnTop(frag);
//                        Intent intent = new Intent(adapterContext,ShowCategoryActivity.class);
//                        intent.putExtra("Name",content.get(onClickPosition).getName());
//                        intent.putExtra("Id",content.get(onClickPosition).getId());
//                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        adapterContext.startActivity(intent);
                        Log.i("Click","shomare " + onClickPosition + " - " + content.get(onClickPosition).getName());


                   }
                }
            });
        }
    }
}