package com.kadouk.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.kadouk.app.model.CategoryResponse;
import com.kadouk.app.model.ContentRespons;
import com.kadouk.app.model.Contents;
import com.kadouk.app.webService.APIClient;
import com.kadouk.app.webService.APIInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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


        return view;
    }

    public void getContent(int id) {

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<CategoryResponse> call = apiInterface.getContentByID(id);
        call.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if(response.code() == 200) {

                    List<Contents> contents;
                    contents = response.body().getContents();
                    mRecyclerViewApps.setAdapter(new DownloadListAdapter(getContext(), contents));

                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                Log.i("Retro","Fail");
            }
        });
    }
}