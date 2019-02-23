package com.kadouk.app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

public class ProductPageFragment extends Fragment  {

    RecyclerView mRecyclerViewProduct;
    RecyclerView.LayoutManager mLayoutManagerProduct;
    List<com.kadouk.app.model.Media> Media;
    ImageView ImageViewApp;
    TextView TextViewAppName, TextViewAppDesc, TextViewAppSize, TextViewAppCost;

    Button BtnDownload;

    public ProductPageFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_product_page, container,
                false);

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
        mLayoutManagerProduct = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,
                false);
        mRecyclerViewProduct.setLayoutManager(mLayoutManagerProduct);

       // BtnDownload = (Button) view.findViewById(R.id.download_btn);

        Toolbar toolbar = view.findViewById(R.id.product_toolbar);
        ((MainActivity)getActivity()).setSupportActionBar(toolbar);
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        //((MainActivity)getActivity()).showUpButton();
        ActionBar actionBar = ((MainActivity)getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        getAppData(Integer.parseInt(id));

        Intent i;
        PackageManager manager = getActivity().getPackageManager();
        try {
            i = manager.getLaunchIntentForPackage("com.mycompany.mygame");
            if (i == null)
                throw new PackageManager.NameNotFoundException();
            i.addCategory(Intent.CATEGORY_LAUNCHER);
            startActivity(i);
        } catch (PackageManager.NameNotFoundException e) {
            InstallAPK downloadAndInstall = new InstallAPK();
            ProgressBar progressBar = view.findViewById(R.id.pbProcessing);
            ProgressDialog progress = new ProgressDialog(getActivity());
            progress.setCancelable(false);
            progress.setMessage("Downloading...");
            downloadAndInstall.setContext(getActivity(), progressBar);
            downloadAndInstall.execute("http://kadouk.com/kadouk/public/api/download/apk/1/app/43/test.apk");
        }

        //Button btnDownload = view.findViewById(R.id.download_btn);
//        btnDownload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i;
//                PackageManager manager = getActivity().getPackageManager();
//                try {
//                    i = manager.getLaunchIntentForPackage("com.mycompany.mygame");
//                    if (i == null)
//                        throw new PackageManager.NameNotFoundException();
//                    i.addCategory(Intent.CATEGORY_LAUNCHER);
//                    startActivity(i);
//                } catch (PackageManager.NameNotFoundException e) {
//                    InstallAPK downloadAndInstall = new InstallAPK();
//                    ProgressDialog progress = new ProgressDialog(getActivity());
//                    progress.setCancelable(false);
//                    progress.setMessage("Downloading...");
//                    downloadAndInstall.setContext(getActivity(), progress);
//                    downloadAndInstall.execute("http://kadouk.com/kaouk/public/api/download/app/1/app/43/test.apk");
//                }
//            }
//        });


//        BtnDownload.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                Intent i;
//                PackageManager manager = getActivity().getPackageManager();
//                try {
//                    i = manager.getLaunchIntentForPackage("com.mycompany.mygame");
//                    if (i == null)
//                        throw new PackageManager.NameNotFoundException();
//                    i.addCategory(Intent.CATEGORY_LAUNCHER);
//                    startActivity(i);
//                } catch (PackageManager.NameNotFoundException e) {
//                    InstallAPK downloadAndInstall = new InstallAPK();
//                    ProgressDialog progress = new ProgressDialog(getActivity());
//                    progress.setCancelable(false);
//                    progress.setMessage("Downloading...");
//                    downloadAndInstall.setContext(getActivity(), progress);
//                    downloadAndInstall.execute("http://kadouk.com/kaouk/public/api/download/app/1/app/43/test.apk");
//                }
//            }
//        });


        return view;
    }


    private void getAppData(int id) {

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<Content> call = apiInterface.getAppDataByID(id);
        call.enqueue(new Callback<Content>() {
            @Override
            public void onResponse(Call<Content> call, Response<Content> response) {
                String appImageUrl ="http://kadouk.com/kadouk/public/api/download/image/"
                        + response.body().getImage();
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
                mRecyclerViewProduct.setAdapter(new ProductScreenshotsAdapter(getContext(),
                        response.body().getMedia()));
            }

            @Override
            public void onFailure(Call<Content> call, Throwable t) {
                Log.i("passs", "faild");
            }
        });
    }
}
