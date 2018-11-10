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

import com.kadouk.app.model.CatagoryResponse;
import com.kadouk.app.model.Content;
import com.kadouk.app.webService.APIClient;
import com.kadouk.app.webService.APIInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowCategoryFragment extends Fragment {

    RecyclerView mRecyclerViewCat;
    RecyclerView.LayoutManager mLayoutManagerCat;
    RecyclerView.Adapter mAdapterCat1, mAdapterCat2, mAdapterCat3, mAdapterCat4, mAdapterCat5;
    List<Content> content;
    TextView toolbarTitle;

    public ShowCategoryFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_category, container, false);
        mRecyclerViewCat = view.findViewById(R.id.show_cat_recycler);
        toolbarTitle = view.findViewById(R.id.show_cat_title);
        mRecyclerViewCat.setHasFixedSize(true);
        mLayoutManagerCat = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerViewCat.setLayoutManager(mLayoutManagerCat);
        //SnapHelper snapHelperCat = new PagerSnapHelper();
        mRecyclerViewCat.setLayoutManager(mLayoutManagerCat);
      //  snapHelperCat.attachToRecyclerView(mRecyclerViewCat);
        Intent intent = getActivity().getIntent();
        String id = getArguments().getString("id");
        Log.i("passs",id);
        //toolbarTitle.setText(name);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        ((MainActivity)getActivity()).setSupportActionBar(toolbar);
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        //((MainActivity)getActivity()).showUpButton();
        ActionBar actionBar = ((MainActivity)getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        getContentCat(Integer.parseInt(id));
        return view;
    }

    private void getContentCat(int id) {

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<CatagoryResponse> call = apiInterface.getContentByID(id);
        call.enqueue(new Callback<CatagoryResponse>() {
            @Override
            public void onResponse(Call<CatagoryResponse> call, Response<CatagoryResponse> response) {
                //Log.i("test",response.code() + "");
                if(response.code() == 200){
                    content = response.body().getContents();
                    toolbarTitle.setVisibility(View.VISIBLE);
                    toolbarTitle.setText(response.body().getCatName());
                    mRecyclerViewCat.setAdapter(new VerticalListAdapter(getContext(),content));
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
            public void onFailure(Call<CatagoryResponse> call, Throwable t) {
                Log.i("Retro","Fail");
            }
        });

    }

}