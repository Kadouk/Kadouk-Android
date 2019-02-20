package com.kadouk.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.kadouk.app.model.Media;

import java.util.List;

public class ProductScreenshotsAdapter extends RecyclerView.Adapter<ProductScreenshotsAdapter.ViewHolder> {

    List<Media> screenshots;
    Context adapterContext;

    public ProductScreenshotsAdapter(Context context, List<Media> screenshots) {
        super();
        adapterContext = context;
        this.screenshots = screenshots;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.content_screenshots_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, @SuppressLint("RecyclerView") int position) {
        String imageUrl = "http://kadouk.com/kadouk/public/api/download/image/"
                + screenshots.get(position).getPath();
        //"http://kadouk.com/kadouk/public/api/download/Path/" +itemName.get(position).getImage();
//"http://kadouk.com/kadouk/public/api/download/Path/" +
        Glide
                .with(adapterContext)
                .load(imageUrl)
                .into(viewHolder.ImageViewScreenshot);
        //viewHolder.imgThumbnail.setImageResource(itemImage.get(position));

        Log.i("screenshots",imageUrl);
    }

    @Override
    public int getItemCount() {
        return screenshots.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ImageViewScreenshot;

        private ItemClickListener clickListener;

        public ViewHolder(View itemView) {

            super(itemView);
            ImageViewScreenshot = itemView.findViewById(R.id.product_screenshots);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                // click on screenshot of product
                }
            });
        }
    }
}