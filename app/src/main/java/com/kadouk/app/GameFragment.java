package com.kadouk.app;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kadouk.app.model.CategoryResponse;
import com.kadouk.app.model.Content;
import com.kadouk.app.model.ContentRespons;
import com.kadouk.app.model.Contents;
import com.kadouk.app.webService.APIClient;
import com.kadouk.app.webService.APIInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GameFragment extends Fragment implements FragmentManager.OnBackStackChangedListener {

    RecyclerView mRecyclerViewApps, mRecyclerViewCategories;
    RecyclerView.LayoutManager mLayoutManagerApps, mLayoutManagerCategories;
    List<Contents> contents;
    ShowCategoryFragment showCategoryFragment = new ShowCategoryFragment();

    public GameFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);

        Toolbar myToolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((MainActivity)getActivity()).setSupportActionBar(myToolbar);
        getContent();

        getActivity().getSupportFragmentManager().addOnBackStackChangedListener(this);
        getContentCat1();

        mRecyclerViewApps = view.findViewById(R.id.main_recycler_cat1);
        mRecyclerViewApps.setHasFixedSize(true);
        mLayoutManagerApps = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,
                false);
        mRecyclerViewApps.setLayoutManager(mLayoutManagerApps);


        //txv_cat1_name = view.findViewById(R.id.cat1_txv_name);


        mRecyclerViewCategories = view.findViewById(R.id.category_recycler_view);
        mRecyclerViewCategories.setHasFixedSize(true);
        mLayoutManagerCategories = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewCategories.setLayoutManager(mLayoutManagerCategories);
        return view;
    }

    private void getContentCat1() {

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<CategoryResponse> call = apiInterface.getContentByID(2);
        call.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if(response.code() == 200) {
                    contents = response.body().getContents();

                    mRecyclerViewApps.setAdapter(new VerticalListAdapter(getContext(), contents));

                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                Log.i("Retro","Fail");
            }
        });
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////
    ///////// bzese crash mishe vaghti app ro baz mikoni va back mizani, chon upButton nadare va in mikhad hide kone. /////////
    //////////////////////////////////////////////////////////////////////////////////////////////////

    public void onBackStackChanged() {
        if (getActivity().getSupportFragmentManager().getBackStackEntryCount() < 1) {
            ((MainActivity) getActivity()).hideUpButton();
        }
    }


    private void getContent() {

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<ContentRespons> call = apiInterface.content();
        call.enqueue(new Callback<ContentRespons>() {
            @Override
            public void onResponse(Call<ContentRespons> call, Response<ContentRespons> response) {
                if(response.code() == 200){
                    List<Contents> categories;
                    categories = response.body().getContents();
                    mRecyclerViewCategories.setAdapter(new CategoryAdapter(getContext(),categories));
                }
            }

            @Override
            public void onFailure(Call<ContentRespons> call, Throwable t) {
                Log.i("Retrofit","Connect your internet");
            }
        });

    }

}