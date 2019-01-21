package com.kadouk.app;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
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
    List<com.kadouk.app.model.Media> Media;
    ImageView ImageViewApp;
    TextView TextViewAppName, TextViewAppDesc, TextViewAppSize, TextViewAppCost;

    public ProductPageFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_page, container, false);

        String name = getArguments().getString("name");
        String id = getArguments().getString("appId");
        TextView appName = view.findViewById(R.id.product_toolbar_title);
        appName.setText(name);

        ImageViewApp = view.findViewById(R.id.product_img_app);
        TextViewAppName = view.findViewById(R.id.product_txv_app_name);
        TextViewAppDesc = view.findViewById(R.id.product_txv_app_desc);
        TextViewAppSize = view.findViewById(R.id.product_txv_app_size);
        TextViewAppCost = view.findViewById(R.id.product_txv_app_cost);

        mRecyclerViewProduct = view.findViewById(R.id.recycler_screenshots);
        mRecyclerViewProduct.setHasFixedSize(true);
        mLayoutManagerProduct = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewProduct.setLayoutManager(mLayoutManagerProduct);

        Toolbar toolbar = view.findViewById(R.id.product_toolbar);
        ((MainActivity)getActivity()).setSupportActionBar(toolbar);
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        //((MainActivity)getActivity()).showUpButton();
        ActionBar actionBar = ((MainActivity)getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        getAppData(Integer.parseInt(id));
        return view;
    }

    private void getAppData(int id) {

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<Content> call = apiInterface.getAppDataByID(id);
        call.enqueue(new Callback<Content>() {
            @Override
            public void onResponse(Call<Content> call, Response<Content> response) {
                String appImageUrl ="http://kadouk.com/kadouk/public/api/download/image/" + response.body().getImage();
                //Log.i("appImageUrl",appImageUrl);

                Glide
                        .with(getContext())
                        .load(appImageUrl)
                        .apply(new RequestOptions()
                                .diskCacheStrategy(DiskCacheStrategy.ALL))
                        .into(ImageViewApp);

                TextViewAppName.setText(response.body().getName());
                TextViewAppDesc.setText(response.body().getDesc());
                TextViewAppSize.setText(response.body().getSize());
                TextViewAppCost.setText(response.body().getCost());

                Media = response.body().getMedia();
                mRecyclerViewProduct.setAdapter(new ProductScreenshotsAdapter(getContext(), response.body().getMedia()));
            }

            @Override
            public void onFailure(Call<Content> call, Throwable t) {
                Log.i("passs", "faild");
            }
        });
    }
}
