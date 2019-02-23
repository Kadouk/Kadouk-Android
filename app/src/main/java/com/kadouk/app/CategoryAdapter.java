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
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.kadouk.app.model.Contents;

import java.util.List;

/**
 * Created by zoli on 02/21/2019.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    List<Contents> content;
    Context adapterContext;

    public CategoryAdapter(Context context, List<Contents> content) {
        super();
        adapterContext = context;
        this.content = content;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.categories_item_horizontal, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, @SuppressLint("RecyclerView") int position) {
        String imageUrl = "http://kadouk.com/kadouk/public/api/download/image/"+content.get(position).getImage();
        Log.i("LOADPIC",imageUrl + position);
        //"http://kadouk.com/kadouk/public/api/download/Path/" +itemName.get(position).getImage();
//"http://kadouk.com/kadouk/public/api/download/Path/" +
        Glide
                .with(adapterContext)
                .load(imageUrl)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(viewHolder.imgThumbnail);
        //viewHolder.imgThumbnail.setImageResource(itemImage.get(position));

        Log.e("adapter",String.valueOf(position));
    }

    @Override
    public int getItemCount() {
        return content.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgThumbnail;
        TextView tvSpecies;
        private ItemClickListener clickListener;

        public ViewHolder(View itemView) {

            super(itemView);
            imgThumbnail = itemView.findViewById(R.id.img_thumbnail);


//            imgThumbnail.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    int onClickPosition = getAdapterPosition();
//                    if(onClickPosition != RecyclerView.NO_POSITION){
//
//                        Bundle appData = new Bundle();
//                        appData.putString("name", content.get(onClickPosition).getName());
//                        appData.putString("appId",
//                                String.valueOf(content.get(onClickPosition).getId()));
//                        ProductPageFragment frag = new ProductPageFragment();
//                        frag.setArguments(appData);
//
//                        MainActivity mainActivity = (MainActivity) adapterContext;
//                        mainActivity.backStackGame = "Game1";
//                        mainActivity.addFragmentOnTop(frag);
//
//                        Log.i("Click","shomare " + onClickPosition + " - "
//                                + content.get(onClickPosition).getName());
//                    }
//                }
//            });

        }
    }
}