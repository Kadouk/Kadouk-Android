package com.kadouk.app;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.kadouk.app.model.AllCategoryResponse;
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
}
