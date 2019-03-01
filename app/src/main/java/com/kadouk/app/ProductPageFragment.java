package com.kadouk.app;

import android.app.Notification;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationManagerCompat;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.Progress;
import com.downloader.Status;
import com.kadouk.app.DownloadUtils.DownloadUtils;
import com.kadouk.app.model.Content;
import com.kadouk.app.webService.APIClient;
import com.kadouk.app.webService.APIInterface;

import java.io.File;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//import com.downloader.

public class ProductPageFragment extends Fragment {

    RecyclerView mRecyclerViewProduct;
    RecyclerView.LayoutManager mLayoutManagerProduct;
    List<com.kadouk.app.model.Media> Media;
    ImageView ImageViewApp;
    static TextView TextViewAppName, TextViewAppDesc, TextViewAppSize, TextViewAppCost;

    static Button BtnDownload;

//    final String URL1 = "http://www.appsapk.com/downloading/latest/WeChat-6.5.7.apk";
    final String URL1 = "http://192.168.183.130:8000/api/download/apk/1/app/43/anaconda.zip";


    static ProgressBar pbDownload;

    int downloadIdOne = 0;

    private static String dirPath;

    Notification.Builder builder;
    NotificationManagerCompat nmc;
    File sdcard;
    public ProductPageFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_product_page, container,
                false);

        final String name = getArguments().getString("name");
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

        BtnDownload = view.findViewById(R.id.btn_download);

        pbDownload = view.findViewById(R.id.pbProcessing);
        pbDownload.setMax(100);



        for(int i = 0; i< PRDownloader.getAll().length; i++) {
            if (PRDownloader.getAll()[i] != null) {
                Log.e("Progress", PRDownloader.getAll()[i].getFileName() + "");
                if(PRDownloader.getAll()[i].getFileName().equals(name + ".apk")){
                    downloadIdOne = PRDownloader.getAll()[i].getDownloadId();
//                        break;
                }
            }

        }
        if (Status.RUNNING == PRDownloader.getStatus(downloadIdOne)) {
            BtnDownload.setText("pause");
        }

        if (Status.PAUSED == PRDownloader.getStatus(downloadIdOne)) {
            BtnDownload.setText("Resume");
        }



        Toolbar toolbar = view.findViewById(R.id.product_toolbar);
        ((MainActivity)getActivity()).setSupportActionBar(toolbar);
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        //((MainActivity)getActivity()).showUpButton();
        ActionBar actionBar = ((MainActivity)getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        getAppData(Integer.parseInt(id));

        dirPath = DownloadUtils.getRootDirPath(getActivity().getApplicationContext());

        BtnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for(int i = 0; i< PRDownloader.getAll().length; i++) {
                    if (PRDownloader.getAll()[i] != null) {
                        Log.e("Progress", PRDownloader.getAll()[i].getFileName() + "");
                        if(PRDownloader.getAll()[i].getFileName().equals(name + ".apk")){
                            downloadIdOne = PRDownloader.getAll()[i].getDownloadId();
//                        break;
                        }
                    }

                }


                if (Status.RUNNING == PRDownloader.getStatus(downloadIdOne)) {
                    PRDownloader.pause(downloadIdOne);
                    return;
                }

//                BtnDownload.setEnabled(false);
                pbDownload.setIndeterminate(true);
                pbDownload.getIndeterminateDrawable().setColorFilter(
                        Color.BLUE, android.graphics.PorterDuff.Mode.SRC_IN);

                if (Status.PAUSED == PRDownloader.getStatus(downloadIdOne)) {
                    PRDownloader.resume(downloadIdOne);
                    return;
                }

                downloadIdOne = PRDownloader.download(URL1, dirPath, name + ".apk")
                        .build()
                        .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                            @Override
                            public void onStartOrResume() {
                                pbDownload.setIndeterminate(false);
                                BtnDownload.setEnabled(true);
                                BtnDownload.setText("pause");
//                                buttonCancelOne.setEnabled(true);
                            }
                        })
                        .setOnPauseListener(new OnPauseListener() {
                            @Override
                            public void onPause() {
                                BtnDownload.setText("resume");
                            }
                        })
                        .setOnCancelListener(new OnCancelListener() {
                            @Override
                            public void onCancel() {
                                BtnDownload.setText("start");
//                                buttonCancelOne.setEnabled(false);
                                pbDownload.setProgress(0);
                                TextViewAppName.setText("");
                                downloadIdOne = 0;
                                pbDownload.setIndeterminate(false);
                            }
                        })
                        .setOnProgressListener(new OnProgressListener() {
                            @Override
                            public void onProgress(Progress progress) {
                                long progressPercent = progress.currentBytes * 100 / progress.totalBytes;

                                pbDownload.setProgress((int) progressPercent);
                                Log.e("ee", (int) progressPercent + "++");
                                Log.e("ee", DownloadUtils.getProgressDisplayLine(progress.currentBytes, progress.totalBytes)+ "++");
                                TextViewAppName.setText(DownloadUtils.getProgressDisplayLine(progress.currentBytes, progress.totalBytes));
                                pbDownload.setIndeterminate(false);
                            }
                        })
                        .start(new OnDownloadListener() {
                            @Override
                            public void onDownloadComplete() {
                                BtnDownload.setEnabled(false);
//                                buttonCancelOne.setEnabled(false);
                                BtnDownload.setText("complete");
                            }

                            @Override
                            public void onError(Error error) {
                                BtnDownload.setText("start");
                                Toast.makeText(getActivity().getApplicationContext(), "Error" + " " + "1", Toast.LENGTH_SHORT).show();
                                TextViewAppName.setText("");
                                Log.e("error", error.toString());
                                pbDownload.setProgress(0);
                                downloadIdOne = 0;
//                                buttonCancelOne.setEnabled(false);
                                pbDownload.setIndeterminate(false);
                                BtnDownload.setEnabled(true);
                            }
                        });




            }
        });

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

//    private void showNotification(int id) {
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            NotificationChannel chanel = new NotificationChannel("chanelid1",
//                    "001", NotificationManager.IMPORTANCE_DEFAULT);
//            chanel.setDescription("description");
//
//            NotificationManager manager = (NotificationManager) getContext().
//                    getSystemService(NOTIFICATION_SERVICE);
//
//            manager.createNotificationChannel(chanel);
//
//            builder = new Notification.Builder(getContext(), "chanelid1");
//            builder.setContentTitle("Downloading");
//            builder.setAutoCancel(false);
//            builder.setProgress(100, 0, false);
//            builder.setWhen(System.currentTimeMillis());
//            builder.setPriority(Notification.PRIORITY_DEFAULT);
//
//            nmc = NotificationManagerCompat.from(getActivity().getApplicationContext());
//            nmc.notify(id, builder.build());
//        }
//        else{
//            builder = new Notification.Builder(getActivity().getApplicationContext());
//            builder.setContentTitle("Downloading");
//            builder.setAutoCancel(false);
//            builder.setProgress(100, 0, false);
//            builder.setWhen(System.currentTimeMillis());
//            builder.setPriority(Notification.PRIORITY_DEFAULT);
//
//            nmc = NotificationManagerCompat.from(getActivity().getApplicationContext());
//            nmc.notify(id, builder.build());
//        }
//
//    }

}
