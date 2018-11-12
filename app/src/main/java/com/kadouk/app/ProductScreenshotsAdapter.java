package com.kadouk.app;

/**
 * Created by zoli on 11/11/2018.
 */import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class ProductScreenshotsAdapter extends RecyclerView.Adapter<ProductScreenshotsAdapter.ViewHolder> {

    private ArrayList<Integer> itemImage;
    Context context1;

    public ProductScreenshotsAdapter(Context context, ArrayList<Integer> itemImage) {
        super();
        context1 = context;
        this.itemImage = itemImage;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, @SuppressLint("RecyclerView") int position) {
        viewHolder.imgThumbnail.setImageResource(itemImage.get(position));

        String imageUrl = "http://kadouk.com/kadouk/public/api/download/image/1/app/43/43-1.png";
        //Log.i("LOADPIC", imageUrl + appPosition);

        Glide
                .with(context1)
                .load(imageUrl)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(viewHolder.imgThumbnail);
        viewHolder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if (isLongClick) {
                    Log.e("screen","shomare " + position + " - " + " (Long click)");
                } else {
                    Log.e("screen","shomare " + position + " - " );
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemImage.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        ImageView imgThumbnail;
        private ItemClickListener clickListener;

        ViewHolder(View itemView) {
            super(itemView);
            imgThumbnail = itemView.findViewById(R.id.product_screenshots);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getPosition(), false);
        }

        @Override
        public boolean onLongClick(View view) {
            clickListener.onClick(view, getPosition(), true);
            return true;
        }
    }
}