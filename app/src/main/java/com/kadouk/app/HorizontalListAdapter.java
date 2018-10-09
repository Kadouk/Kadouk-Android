package com.kadouk.app;

/**
 * Created by zoli on 10/09/2018.
 */
import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class HorizontalListAdapter extends RecyclerView.Adapter<HorizontalListAdapter.ViewHolder> {

    private ArrayList<String> itemName;
    private ArrayList<Integer> itemImage;
    public int lastItemPosition = -1;


    public HorizontalListAdapter(Context context, ArrayList<String> itemName, ArrayList<Integer> itemImage) {
        super();
        Context context1 = context;
        this.itemName = itemName;
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
        viewHolder.tvSpecies.setText(itemName.get(position));
        viewHolder.imgThumbnail.setImageResource(itemImage.get(position));

        Log.e("adapter",String.valueOf(position));

        viewHolder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if (isLongClick) {
                    Log.e("Click","#" + position + " - " + itemName.get(position) + " (Long click)");
                } else {
                    Log.e("Click","#" + position + " - " + itemName.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemName.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        ImageView imgThumbnail;
        TextView tvSpecies;
        private ItemClickListener clickListener;

        public ViewHolder(View itemView) {
            super(itemView);
            imgThumbnail = itemView.findViewById(R.id.img_thumbnail);
            tvSpecies = itemView.findViewById(R.id.tv_species);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        public void setClickListener(ItemClickListener itemClickListener) {
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