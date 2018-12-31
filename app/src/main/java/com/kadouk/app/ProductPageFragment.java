package com.kadouk.app;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
    List<com.kadouk.app.model.Media> Media;

    public ProductPageFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_page, container, false);

        String name = getArguments().getString("name");
        String id = getArguments().getString("appId");
        TextView appName = view.findViewById(R.id.product_page_txv_app_name);
        appName.setText(name);

        mRecyclerViewProduct = view.findViewById(R.id.recycler_screenshots);
        mRecyclerViewProduct.setHasFixedSize(true);
        mLayoutManagerProduct = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewProduct.setLayoutManager(mLayoutManagerProduct);

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
                mRecyclerViewProduct.setAdapter(new ProductScreenshotsAdapter(getContext(), response.body().getMedia()));
            }

            @Override
            public void onFailure(Call<Content> call, Throwable t) {
                Log.i("passs", "faild");
            }
        });
    }
}
