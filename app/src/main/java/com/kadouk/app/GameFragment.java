package com.kadouk.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
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
    final Fragment fragment = new ShowCategoryActivity();
//    MainActivity main = new MainActivity();
    public GameFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);

        Button btn = view.findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                main.fragmentManager.beginTransaction().add(R.id.main_container, fragment, "5").hide(fragment).commit();
//                main.fragmentManager.beginTransaction().hide(Globals.getActive()).show(fragment).commit();
                //main.active = fragment;
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.main_container, fragment);
                fr.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                fr.addToBackStack(null);
                Globals.setActive(fragment);
                fr.commit();
            }
        });
        getContentCat1();
        getContentCat2();
        getContentCat3();
        getContentCat4();
        getContentCat5();

        mRecyclerViewCat1 = view.findViewById(R.id.main_recycler_cat1);
        mRecyclerViewCat1.setHasFixedSize(true);
        mLayoutManagerCat1 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewCat1.setLayoutManager(mLayoutManagerCat1);
        SnapHelper snapHelperCat1 = new PagerSnapHelper();
        mRecyclerViewCat1.setLayoutManager(mLayoutManagerCat1);
        snapHelperCat1.attachToRecyclerView(mRecyclerViewCat1);

        mRecyclerViewCat2 = view.findViewById(R.id.main_recycler_cat2);
        mRecyclerViewCat2.setHasFixedSize(true);
        mLayoutManagerCat2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        SnapHelper snapHelperCat2 = new PagerSnapHelper();
        mRecyclerViewCat2.setLayoutManager(mLayoutManagerCat2);
        snapHelperCat2.attachToRecyclerView(mRecyclerViewCat2);

        mRecyclerViewCat3 = view.findViewById(R.id.main_recycler_cat3);
        mRecyclerViewCat3.setHasFixedSize(true);
        mLayoutManagerCat3 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        SnapHelper snapHelperCat3 = new PagerSnapHelper();
        mRecyclerViewCat3.setLayoutManager(mLayoutManagerCat3);
        snapHelperCat3.attachToRecyclerView(mRecyclerViewCat3);

        mRecyclerViewCat4 = view.findViewById(R.id.main_recycler_cat4);
        mRecyclerViewCat4.setHasFixedSize(true);
        mLayoutManagerCat4 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        SnapHelper snapHelperCat4 = new PagerSnapHelper();
        mRecyclerViewCat4.setLayoutManager(mLayoutManagerCat4);
        snapHelperCat4.attachToRecyclerView(mRecyclerViewCat4);

        mRecyclerViewCat5 = view.findViewById(R.id.main_recycler_cat5);
        mRecyclerViewCat5.setHasFixedSize(true);
        mLayoutManagerCat5 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        SnapHelper snapHelperCat5 = new PagerSnapHelper();
        mRecyclerViewCat5.setLayoutManager(mLayoutManagerCat5);
        snapHelperCat5.attachToRecyclerView(mRecyclerViewCat5);

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

    private void getContentCat3() {

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<ContentRespons> call = apiInterface.content();
        call.enqueue(new Callback<ContentRespons>() {
            @Override
            public void onResponse(Call<ContentRespons> call, Response<ContentRespons> response) {
                if(response.code() == 200){

                    content = response.body().getContents();
                    mRecyclerViewCat3.setAdapter(new HorizontalListAdapter(getContext(),content));
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

    private void getContentCat4() {

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<ContentRespons> call = apiInterface.content();
        call.enqueue(new Callback<ContentRespons>() {
            @Override
            public void onResponse(Call<ContentRespons> call, Response<ContentRespons> response) {
                if(response.code() == 200){

                    content = response.body().getContents();
                    mRecyclerViewCat4.setAdapter(new HorizontalListAdapter(getContext(),content));
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

    private void getContentCat5() {

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<ContentRespons> call = apiInterface.content();
        call.enqueue(new Callback<ContentRespons>() {
            @Override
            public void onResponse(Call<ContentRespons> call, Response<ContentRespons> response) {
                if(response.code() == 200){

                    content = response.body().getContents();
                    mRecyclerViewCat5.setAdapter(new HorizontalListAdapter(getContext(),content));
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
