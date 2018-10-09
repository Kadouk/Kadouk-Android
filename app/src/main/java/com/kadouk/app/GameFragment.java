package com.kadouk.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.kadouk.app.model.Content;
import com.kadouk.app.model.ContentRespons;
import com.kadouk.app.webService.APIClient;
import com.kadouk.app.webService.APIInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GameFragment extends Fragment {

    RecyclerView mRecyclerViewCat1,mRecyclerViewCat2,mRecyclerViewCat3,mRecyclerViewCat4,mRecyclerViewCat5;
    RecyclerView.LayoutManager mLayoutManagerCat1,mLayoutManagerCat2,mLayoutManagerCat3,mLayoutManagerCat4,mLayoutManagerCat5;
    RecyclerView.Adapter mAdapterCat1,mAdapterCat2,mAdapterCat3,mAdapterCat4,mAdapterCat5;
    List<Content> content;

    public GameFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        getContentCat1();
        getContentCat2();

        mRecyclerViewCat1 = view.findViewById(R.id.main_recycler_cat1);
        mRecyclerViewCat1.setHasFixedSize(true);
        mLayoutManagerCat1 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewCat1.setLayoutManager(mLayoutManagerCat1);

        mRecyclerViewCat2 = view.findViewById(R.id.main_recycler_cat2);
        mRecyclerViewCat2.setHasFixedSize(true);
        mLayoutManagerCat2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewCat2.setLayoutManager(mLayoutManagerCat2);

        return view;
    }


    private void getContentCat1() {

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<ContentRespons> call = apiInterface.content();
        call.enqueue(new Callback<ContentRespons>() {
            @Override
            public void onResponse(Call<ContentRespons> call, Response<ContentRespons> response) {
                if(response.code() == 200){

                    content = response.body().getContents();
                    mRecyclerViewCat1.setAdapter(new HorizontalListAdapter(getContext(),content));
//                    for (Content content1 : content){
//                        contentName.add(String.valueOf(content1.getName()));
//                        contentImageUrl.add(String.valueOf(content1.getImage()));
//                        Log.i("LOGIN", String.valueOf(content1.getId()));
//                        Log.i("LOGIN", String.valueOf(content1.getName()));
//                        Log.i("LOGIN", String.valueOf(content1.getDesc()));
//                        Log.i("LOGIN", String.valueOf(content1.getImage()));
//                    }
                }
            }

            @Override
            public void onFailure(Call<ContentRespons> call, Throwable t) {
                Log.i("Retro","Fail");
            }
        });

    }

    private void getContentCat2() {

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<ContentRespons> call = apiInterface.content();
        call.enqueue(new Callback<ContentRespons>() {
            @Override
            public void onResponse(Call<ContentRespons> call, Response<ContentRespons> response) {
                if(response.code() == 200){

                    content = response.body().getContents();
                    mRecyclerViewCat2.setAdapter(new HorizontalListAdapter(getContext(),content));
//                    for (Content content1 : content){
//                        contentName.add(String.valueOf(content1.getName()));
//                        contentImageUrl.add(String.valueOf(content1.getImage()));
//                        Log.i("LOGIN", String.valueOf(content1.getId()));
//                        Log.i("LOGIN", String.valueOf(content1.getName()));
//                        Log.i("LOGIN", String.valueOf(content1.getDesc()));
//                        Log.i("LOGIN", String.valueOf(content1.getImage()));
//                    }
                }
            }

            @Override
            public void onFailure(Call<ContentRespons> call, Throwable t) {
                Log.i("Retro","Fail");
            }
        });

    }


}
