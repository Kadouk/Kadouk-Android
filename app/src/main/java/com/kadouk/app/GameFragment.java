package com.kadouk.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.kadouk.app.model.CatagoryResponse;
import com.kadouk.app.model.Content;
import com.kadouk.app.webService.APIClient;
import com.kadouk.app.webService.APIInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GameFragment extends Fragment implements FragmentManager.OnBackStackChangedListener {

    TextView txv_app_cat1, txv_app_cat2, txv_app_cat3, txv_app_cat4, txv_app_cat5;

    RecyclerView mRecyclerViewCat1, mRecyclerViewCat2, mRecyclerViewCat3, mRecyclerViewCat4, mRecyclerViewCat5;
    RecyclerView.LayoutManager mLayoutManagerCat1, mLayoutManagerCat2, mLayoutManagerCat3, mLayoutManagerCat4, mLayoutManagerCat5;
    List<Content> content;
    ShowCategoryFragment showCategoryFragment = new ShowCategoryFragment();
    MainActivity mainActivity = (MainActivity) getContext();
    public GameFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);


        TextView openCat1 = view.findViewById(R.id.txv_app_open_cat1);

        openCat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle i = new Bundle();
                i.putString("id", "1");
                showCategoryFragment.setArguments(i);
                ((MainActivity) getActivity()).backStackGame = "Game1";
                ((MainActivity) getActivity()).addFragmentOnTop(showCategoryFragment);
            }
        });


        TextView openCat2 = view.findViewById(R.id.txv_app_open_cat2);
        openCat2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle i = new Bundle();
                ShowCategoryFragment showCategoryFragment = new ShowCategoryFragment();
                i.putString("id", "2");
                showCategoryFragment.setArguments(i);
                ((MainActivity) getActivity()).backStackGame = "Game1";
                ((MainActivity) getActivity()).addFragmentOnTop(showCategoryFragment);
            }
        });

        TextView openCat3 = view.findViewById(R.id.txv_app_open_cat3);
        openCat3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle i = new Bundle();
                ShowCategoryFragment showCategoryFragment = new ShowCategoryFragment();
                i.putString("id", "3");
                showCategoryFragment.setArguments(i);
                ((MainActivity) getActivity()).backStackGame = "Game1";
                ((MainActivity) getActivity()).addFragmentOnTop(showCategoryFragment);
            }
        });

        TextView openCat4 = view.findViewById(R.id.txv_app_open_cat4);
        openCat4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle i = new Bundle();
                ShowCategoryFragment showCategoryFragment = new ShowCategoryFragment();
                i.putString("id", "4");
                showCategoryFragment.setArguments(i);
                ((MainActivity) getActivity()).backStackGame = "Game1";
                ((MainActivity) getActivity()).addFragmentOnTop(showCategoryFragment);
            }
        });

        TextView openCat5 = view.findViewById(R.id.txv_app_open_cat5);
        openCat5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle i = new Bundle();
                ShowCategoryFragment showCategoryFragment = new ShowCategoryFragment();
                i.putString("id", "5");
                showCategoryFragment.setArguments(i);
                ((MainActivity) getActivity()).backStackGame = "Game1";
                ((MainActivity) getActivity()).addFragmentOnTop(showCategoryFragment);
            }
        });

        getActivity().getSupportFragmentManager().addOnBackStackChangedListener(this);
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

        txv_app_cat1 = view.findViewById(R.id.txv_app_cat1);
        txv_app_cat2 = view.findViewById(R.id.txv_app_cat2);
        txv_app_cat3 = view.findViewById(R.id.txv_app_cat3);
        txv_app_cat4 = view.findViewById(R.id.txv_app_cat4);
        txv_app_cat5 = view.findViewById(R.id.txv_app_cat5);



        return view;


    }

    private void getContentCat1() {

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<CatagoryResponse> call = apiInterface.getContentByID(1);
        call.enqueue(new Callback<CatagoryResponse>() {
            @Override
            public void onResponse(Call<CatagoryResponse> call, Response<CatagoryResponse> response) {
                if(response.code() == 200){
                    content = response.body().getContents();
                    txv_app_cat1.setText(response.body().getCatName());

                    mRecyclerViewCat1.setAdapter(new HorizontalListAdapter(getContext(),content));
                }
            }

            @Override
            public void onFailure(Call<CatagoryResponse> call, Throwable t) {
                Log.i("Retro","Fail");
            }
        });

    }

    private void getContentCat2() {

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<CatagoryResponse> call = apiInterface.getContentByID(2);
        call.enqueue(new Callback<CatagoryResponse>() {
            @Override
            public void onResponse(Call<CatagoryResponse> call, Response<CatagoryResponse> response) {
                if(response.code() == 200){

                    content = response.body().getContents();
                    txv_app_cat2.setText(response.body().getCatName());

                    mRecyclerViewCat2.setAdapter(new HorizontalListAdapter(getContext(),content));
                }
            }

            @Override
            public void onFailure(Call<CatagoryResponse> call, Throwable t) {
                Log.i("Retro","Fail");
            }
        });
    }

    private void getContentCat3() {

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<CatagoryResponse> call = apiInterface.getContentByID(3);
        call.enqueue(new Callback<CatagoryResponse>() {
            @Override
            public void onResponse(Call<CatagoryResponse> call, Response<CatagoryResponse> response) {
                if(response.code() == 200){

                    content = response.body().getContents();
                    txv_app_cat3.setText(response.body().getCatName());

                    mRecyclerViewCat3.setAdapter(new HorizontalListAdapter(getContext(),content));
                }
            }

            @Override
            public void onFailure(Call<CatagoryResponse> call, Throwable t) {
                Log.i("Retro","Fail");
            }
        });
    }

    private void getContentCat4() {

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<CatagoryResponse> call = apiInterface.getContentByID(4);
        call.enqueue(new Callback<CatagoryResponse>() {
            @Override
            public void onResponse(Call<CatagoryResponse> call, Response<CatagoryResponse> response) {
                if(response.code() == 200){

                    content = response.body().getContents();
                    txv_app_cat4.setText(response.body().getCatName());

                    mRecyclerViewCat4.setAdapter(new HorizontalListAdapter(getContext(),content));
                }
            }

            @Override
            public void onFailure(Call<CatagoryResponse> call, Throwable t) {
                Log.i("Retro","Fail");
            }
        });
    }

    private void getContentCat5() {

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<CatagoryResponse> call = apiInterface.getContentByID(5);
        call.enqueue(new Callback<CatagoryResponse>() {
            @Override
            public void onResponse(Call<CatagoryResponse> call, Response<CatagoryResponse> response) {
                if(response.code() == 200){

                    content = response.body().getContents();
                    txv_app_cat5.setText(response.body().getCatName());

                    mRecyclerViewCat5.setAdapter(new HorizontalListAdapter(getContext(),content));
                }
            }

            @Override
            public void onFailure(Call<CatagoryResponse> call, Throwable t) {
                Log.i("Retro","Fail");
            }
        });
    }

    public void onBackStackChanged() {
        if (getActivity().getSupportFragmentManager().getBackStackEntryCount() < 1) {
            ((MainActivity) getActivity()).hideUpButton();
        }
    }
}
