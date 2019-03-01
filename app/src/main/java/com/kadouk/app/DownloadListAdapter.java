package com.kadouk.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kadouk.app.db.Post;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zoli on 02/23/2019.
 */

public class DownloadListAdapter extends RecyclerView.Adapter<DownloadListAdapter.ViewHolder> {

//    List<Contents> contents;
//    private List<MovieModel.DataModel> moviesList;
    Context adapterContext;
    int progress;
    private List<Post> data;

    public DownloadListAdapter(Context context, int progress) {
        super();
        adapterContext = context;
//        this.contents = new ArrayList<>();
        this.progress = progress;
        Glide.get(context).clearMemory();
//        this.moviesList = moviesList;
        this.data = new ArrayList<>();
    }

    @Override
    public DownloadListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.download_item, viewGroup, false);
        DownloadListAdapter.ViewHolder viewHolder = new DownloadListAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DownloadListAdapter.ViewHolder viewHolder, @SuppressLint("RecyclerView") int position) {

//        MovieModel.DataModel movie = moviesList.get(position);
//        viewHolder.txvAppName.setText(movie.getName());

//        viewHolder.bind(data.get(position));
        viewHolder.txvAppName.setText(data.get(position).getTitle());
        viewHolder.txvAppDesc.setText(data.get(position).getContent());
//        viewHolder.downloadProgressBar.setProgress(Integer.valueOf(data.get(position).getContent()));


//        String imageUrl = "http://kadouk.com/kadouk/public/api/download/image/"
//                + contents.get(position).getImage();
//
//        Log.i("LOADPIC", imageUrl + position);
//
//        Glide
//                .with(adapterContext)
//                .load(imageUrl)
//                .apply(new RequestOptions()
//                        .diskCacheStrategy(DiskCacheStrategy.ALL))
//                .into(viewHolder.imgApp);
    }

    public void setData(List<Post> newData) {
        if (data != null) {
            PostDiffCallback postDiffCallback = new PostDiffCallback(data, newData);
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(postDiffCallback);

            data.clear();
            data.addAll(newData);
            diffResult.dispatchUpdatesTo(this);
        } else {
            // first initialization
            data = newData;
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgApp;
        TextView txvAppName, txvAppDesc;
        ProgressBar downloadProgressBar;
        private ItemClickListener clickListener;

        public ViewHolder(View itemView) {

            super(itemView);
//            imgApp = itemView.findViewById(R.id.img_app);
            txvAppName = itemView.findViewById(R.id.txv_app_name);
            txvAppDesc = itemView.findViewById(R.id.txv_app_desc);
            downloadProgressBar = itemView.findViewById(R.id.download_progerss);
            downloadProgressBar.setMax(100);
            ;


//            imgApp.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    int onClickPosition = getAdapterPosition();
//                    if(onClickPosition != RecyclerView.NO_POSITION){
//
//                        Bundle appData = new Bundle();
//                        appData.putString("name", contents.get(onClickPosition).getName());
//                        appData.putString("appId",
//                                String.valueOf(contents.get(onClickPosition).getId()));
//                        ProductPageFragment frag = new ProductPageFragment();
//                        frag.setArguments(appData);
//
//                        MainActivity mainActivity = (MainActivity) adapterContext;
//                        mainActivity.backStackGame = "Game1";
//                        mainActivity.addFragmentOnTop(frag);
//
//                        Log.i("Click","shomare " + onClickPosition + " - "
//                                + contents.get(onClickPosition).getName());
//                    }
//                }
//            });
        }


    }


    class PostDiffCallback extends DiffUtil.Callback {

        private final List<Post> oldPosts, newPosts;

        public PostDiffCallback(List<Post> oldPosts, List<Post> newPosts) {
            this.oldPosts = oldPosts;
            this.newPosts = newPosts;
        }

        @Override
        public int getOldListSize() {
            return oldPosts.size();
        }

        @Override
        public int getNewListSize() {
            return newPosts.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return oldPosts.get(oldItemPosition).getId() == newPosts.get(newItemPosition).getId();
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return oldPosts.get(oldItemPosition).equals(newPosts.get(newItemPosition));
        }
    }

}