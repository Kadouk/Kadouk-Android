package com.kadouk.app;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kadouk.app.model.App;
import com.kadouk.app.model.CatagoryResponse;
import com.kadouk.app.model.Content;
import com.kadouk.app.webService.APIClient;
import com.kadouk.app.webService.APIInterface;

import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductPageFragment extends Fragment {

    RecyclerView mRecyclerViewCat;
    List<Content> content;
    TextView toolbarTitle;

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
        getAppData(Integer.parseInt(id));
        return view;

    }

    private void getAppData(int id) {

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<App> call = apiInterface.getAppDataByID(id);
        call.enqueue(new Callback<App>() {
            @Override
            public void onResponse(Call<App> call, Response<App> response) {
                if(response.code() == 200) {
                    Log.i("passs", "ok");
                    Log.i("passs", response.body().getImage());
                }
            }

            @Override
            public void onFailure(Call<App> call, Throwable t) {
                Log.i("passs", "faild");
            }
        });




    }

}
