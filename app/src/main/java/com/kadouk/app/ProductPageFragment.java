package com.kadouk.app;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.kadouk.app.model.Content;
import com.kadouk.app.webService.APIClient;
import com.kadouk.app.webService.APIInterface;


import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductPageFragment extends Fragment {

    RecyclerView mRecyclerViewProduct;
    RecyclerView.LayoutManager mLayoutManagerProduct;
    TextView toolbarTitle;
    List<com.kadouk.app.model.Media> Media;
    RecyclerView.Adapter mAdapter;

    public ProductPageFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_page, container, false);

        // maybe need  String id = getIntent().getExtras().getString("Id");


        Intent intent = getActivity().getIntent();
        String name = getArguments().getString("name");
        String id = getArguments().getString("appId");
        Log.i("passs",name);
        Log.i("passs",id);
        TextView appName = view.findViewById(R.id.product_page_txv_app_name);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        //toolbarTitle.setText(name);
        appName.setText(name);
        //toolbar.setTitle(name);
//       ((MainActivity)getActivity()).setSupportActionBar(toolbar);
//        ((MainActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
//        ((MainActivity)getActivity()).showUpButton();
//        ActionBar actionBar = ((MainActivity)getActivity()).getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true);
//        }
//        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Your Title");
//        toolbar.setTitle(name);
//        actionBar.setDisplayHomeAsUpEnabled(true);


//        mRecyclerView = findViewById(R.id.recycler_screenshots);
//        preparingList();
//        mRecyclerView.setHasFixedSize(true);
//        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//        mRecyclerView.setLayoutManager(mLayoutManager);
//        mAdapter = new ProductScreenshotsAdapter(this,itemImage);
//        mRecyclerView.setAdapter(mAdapter);
//    }


        mRecyclerViewProduct = view.findViewById(R.id.recycler_screenshots);
        mRecyclerViewProduct.setHasFixedSize(true);
        mLayoutManagerProduct = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewProduct.setLayoutManager(mLayoutManagerProduct);
        //mAdapter = new ProductScreenshotsAdapter(getContext(),itemImage);
       // mRecyclerView.setAdapter(mAdapter);

        getAppData(Integer.parseInt(id));

        return view;

    }

    private void getAppData(int id) {

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<Content> call = apiInterface.getAppDataByID(id);
        call.enqueue(new Callback<Content>() {
            @Override
            public void onResponse(Call<Content> call, Response<Content> response) {
                Media = response.body().getMedia();
                Log.i("passs", String.valueOf(Media));
                mRecyclerViewProduct.setAdapter(new ProductScreenshotsAdapter(getContext(), response.body().getMedia()));
                Log.i("passs", String.valueOf(response.body().getId()));
                Log.i("passs", response.body().getName());
                Log.i("passs", response.body().getDesc());
                Log.i("passs", String.valueOf(response.body().getReport()));
                Log.i("passs", String.valueOf(response.body().getAge()));
                Log.i("passs", response.body().getTag());
                Log.i("passs", response.body().getSize());
                Log.i("passs", response.body().getCost());
                //               String a = response.body().getCost();
                Log.i("passs", response.body().getImage());

            }

            @Override
            public void onFailure(Call<Content> call, Throwable t) {
                Log.i("passs", "faild");
            }
        });

    }
}
