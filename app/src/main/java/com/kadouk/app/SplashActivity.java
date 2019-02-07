package com.kadouk.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.kadouk.app.model.ContentRespons;
import com.kadouk.app.webService.APIClient;
import com.kadouk.app.webService.APIInterface;

import java.net.InetAddress;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
//1
//avalin activity hast ke baz mishe
// bad az inke ino kamel check kardi CategoryResponse ro az tu pushe model baz kon,
// ke aval model ha va get o post ro bebini chejurie bad edame bedi

public class SplashActivity extends AppCompatActivity {

    Intent intent;
    android.content.SharedPreferences SharedPreferences;
    public static final String MyShPref = "MyPrefers", FirstRun = "run",
            authenticationToken = "Token";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkConnection();
    }

    protected int getAPI(){
        int currentAPI = android.os.Build.VERSION.SDK_INT;
        Log.i("API", currentAPI + "");

        return currentAPI;
    }
    // sendAPI marbut mishe be ersal API be server,
    // felan behesh deghat nakon,
    // in ersalo daryaft ro jolotar bekhun, ke hamrah ba model ha bashe,
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

    // inja check mikonim bebinim device online hast ya na
    public void checkConnection(){

        if(isOnline()){

            SharedPreferences = getSharedPreferences(MyShPref, Context.MODE_PRIVATE);

            //age avalin bar bud ke app run mishod(sabtenam nakarde bud), SignUpActivity ro baz mikone
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
             //age sabtenam karde bud MainActivity ro baz mikone
            if (SharedPreferences.getString(FirstRun,null) != null) {
                Log.i("token", "eshteba = " + SharedPreferences.getString(FirstRun,null));
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            }

        }else{

            Toast.makeText(this, "no internet connection",
                    Toast.LENGTH_LONG).show();

        }
    }
}
