package com.kadouk.app;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DownloadsFragment extends Fragment {

    RecyclerView mRecyclerViewApps;
    RecyclerView.LayoutManager mLayoutManagerApps;


    public DownloadsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_downloads, container, false);

        getContent(2);

        mRecyclerViewApps = view.findViewById(R.id.download_recycler);
        mRecyclerViewApps.setHasFixedSize(true);
        mLayoutManagerApps = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,
                false);
        mRecyclerViewApps.setLayoutManager(mLayoutManagerApps);

        mRecyclerViewApps.setAdapter(postsAdapter);

        return view;
    }
    private DownloadListAdapter postsAdapter;
    private LiveDataDownloadViewModel postViewModel;
    public void getContent(int id) {



        postViewModel = ViewModelProviders.of(this).get(LiveDataDownloadViewModel.class);
        postViewModel.getElapsedTime().observe(this, contents -> postsAdapter.setData(contents));

//        postViewModel = ViewModelProviders.of(this).get(MovieListViewModel.class);
//        postViewModel.init();
//        postViewModel.getMovies().observe(this, new Observer<MovieModel>() {
//            @Override
//            public void onChanged(@Nullable MovieModel movieModels) {
//                movieList.addAll(movieModels.getData());
//                postsAdapter.notifyDataSetChanged();
//            }
//        });
        postsAdapter = new DownloadListAdapter(getContext(), 10);




//        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
//        Call<CategoryResponse> call = apiInterface.getContentByID(id);
//        call.enqueue(new Callback<CategoryResponse>() {
//            @Override
//            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
//                if(response.code() == 200) {
//
//                    List<Contents> contents;
//                    contents = response.body().getContents();
//                    mRecyclerViewApps.setAdapter(new DownloadListAdapter(getContext() 10));
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<CategoryResponse> call, Throwable t) {
//                Log.i("Retro","Fail");
//            }
//        });
    }
}