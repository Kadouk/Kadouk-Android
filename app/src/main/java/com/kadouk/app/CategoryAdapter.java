package com.kadouk.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
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
import com.kadouk.app.model.CategoryResponse;
import com.kadouk.app.model.Content;
import com.kadouk.app.model.Contents;
import com.kadouk.app.webService.APIClient;
import com.kadouk.app.webService.APIInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zoli on 02/21/2019.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    List<Contents> content;
    Context adapterContext;
    GameFragment gameFragment;
    static int lastPosition = 1;

    public CategoryAdapter(Context context, List<Contents> content, GameFragment gameFragment) {
        super();
        adapterContext = context;
        this.content = content;
        this.gameFragment = gameFragment;
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
        Glide
                .with(adapterContext)
                .load(imageUrl)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(viewHolder.imgCategory);

        Log.e("adapter",String.valueOf(position));
    }

    @Override
    public int getItemCount() {
        return content.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgCategory;
        TextView tvSpecies;
        private ItemClickListener clickListener;

        public ViewHolder(View itemView) {

            super(itemView);
            imgCategory = itemView.findViewById(R.id.img_thumbnail);


            imgCategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition()+1;
                    if(position != RecyclerView.NO_POSITION && lastPosition != position ){
                        Log.i("Click","shomare " + position + " - "
                                + content.get(position).getName());
                        gameFragment.getCatContent(position);
                        lastPosition = position;
                    }
                }
            });

        }
    }
}