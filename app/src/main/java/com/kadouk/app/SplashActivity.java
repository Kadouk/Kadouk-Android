package com.kadouk.app;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.kadouk.app.model.AllCategoryResponse;
import com.kadouk.app.model.AppUpdate;
import com.kadouk.app.model.ContentRespons;
import com.kadouk.app.webService.APIClient;
import com.kadouk.app.webService.APIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    Intent intent;
    android.content.SharedPreferences SharedPreferences;
    public static final String MyShPref = "MyPrefers", FirstRun = "run",
            authenticationToken = "Token";
    AlertDialog.Builder alertBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkUpdate();
    }

    protected int getAPI(){
        int currentAPI = android.os.Build.VERSION.SDK_INT;
        Log.i("API", currentAPI + "");

        return currentAPI;
    }

    private void sendAPI() {

        int API = getAPI();
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<ContentRespons> call = apiInterface.sendAPICode(String.valueOf(API));
        call.enqueue(new Callback<ContentRespons>() {
            @Override
            public void onResponse(Call<ContentRespons> call, Response<ContentRespons> response) {
                if(response.code() == 200){

                }
            }

            @Override
            public void onFailure(Call<ContentRespons> call, Throwable t) {
                Log.i("Retro","Fail");
            }
        });

    }

    protected boolean isOnline() {

        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnectedOrConnecting()) {

            return true;

        } else {

            return false;

        }

    }

    public void checkConnection(){

        if(isOnline()){
            SharedPreferences = getSharedPreferences(MyShPref, Context.MODE_PRIVATE);

            if (SharedPreferences.getString(FirstRun,null) == null) {
                sendAPI();

                Log.i("token", "token = " + SharedPreferences.getString(FirstRun,null));
                intent = new Intent(SplashActivity.this, SignUpActivity.class);
                finish();
                startActivity(intent);
            }

            if (SharedPreferences.getString(authenticationToken,null) != null) {
                String Token = SharedPreferences.getString(authenticationToken,null);
                Globals.setToken(Token);
                Log.i("token", "eshtebah = " + SharedPreferences.getString(authenticationToken,null));
            }
            if (SharedPreferences.getString(FirstRun,null) != null) {
                getCategories();
            }

        }else{

            Toast.makeText(this, "no internet connection",
                    Toast.LENGTH_LONG).show();

        }
    }

    private void getCategories() {

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<AllCategoryResponse> call = apiInterface.getAllCategory(-1);
        call.enqueue(new Callback<AllCategoryResponse>() {
            @Override
            public void onResponse(Call<AllCategoryResponse> call, Response<AllCategoryResponse> response) {
                if(response.code() == 200){
                    Globals.setAllCategories(response.body().getCategoryRespons());
                     Log.i("allCat",Globals.allCategories.get(0).getCatName());
                    Log.i("token", "eshteba = " + SharedPreferences.getString(FirstRun,null));
                    finish();
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }
            }

            @Override
            public void onFailure(Call<AllCategoryResponse> call, Throwable t) {
                Log.i("Retro", "Fail = " + t.getMessage());
            }
        });
    }


    private void checkUpdate(){

        String version = "error";
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            version = pInfo.versionName;
            Log.i("version", version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<AppUpdate> call = apiInterface.checkUpdate(version);
        call.enqueue(new Callback<AppUpdate>() {
            @Override
            public void onResponse(Call<AppUpdate> call, Response<AppUpdate> response) {
                if(response.code() == 200) {
                    Log.i("checkUpdate", response.body().getStatus());
                    if(response.body().getStatus().equals("No"))
                    {
                        checkConnection();
                    }else if(response.body().getStatus().equals("option")){
                        buildNotification();
                        checkConnection();
                    }else{
                        buildAlert();
                        Log.i("checkUpdate", "force dad");
                    }


                }
            }

            @Override
            public void onFailure(Call<AppUpdate> call, Throwable t) {
                Log.i("checkUpdate", "failed");
            }
        });



    }

    public void buildNotification(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_notification)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(getString(R.string.download_update_text_optional))
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(getString(R.string.download_update_bigtext_optional)))
                .setPriority(NotificationCompat.PRIORITY_HIGH);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        Intent resultIntent = new Intent(this, SplashActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(SplashActivity.class);

        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(resultPendingIntent);

        mNotificationManager.notify(1, builder.build());
    }

    public void buildAlert(){

        alertBuilder = new AlertDialog.Builder(this);
        //alertBuilder.setTitle("بروزرسانی !");
        alertBuilder.setMessage(R.string.download_update_alert_force);

        alertBuilder.setPositiveButton(R.string.dwonload_update_alert_yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Toast.makeText(SplashActivity.this, "Yes Button Clicked!", Toast.LENGTH_SHORT).show();

            }
        });

        alertBuilder.setNegativeButton(R.string.dwonload_update_alert_no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                Toast.makeText(SplashActivity.this, "No Button Clicked!", Toast.LENGTH_SHORT).show();

            }
        });

        //alertBuilder.setNegativeButton("بعدا", null);

        AlertDialog alert = alertBuilder.create();
        alert.show();
    }
}
