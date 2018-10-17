package com.kadouk.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.kadouk.app.model.ContentRespons;
import com.kadouk.app.webService.APIClient;
import com.kadouk.app.webService.APIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    final Fragment fragment1 = new GameFragment();
    final Fragment fragment2 = new DownloadsFragment();
    final Fragment fragment3 = new SearchFragment();
    final Fragment fragment4 = new AccountFragment();
    final FragmentManager fragmentManager = getSupportFragmentManager();
    Fragment active = fragment1;

    Intent intent;
    SharedPreferences SharedPreferences;
    public static final String MyShPref = "MyPrefers", FirstRun = "run";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences = getSharedPreferences(MyShPref, Context.MODE_PRIVATE);
        if (SharedPreferences.getString(FirstRun,null) == null) {
            sendAPI();

            Log.i("token", "token = " + SharedPreferences.getString(FirstRun,null));
            intent = new Intent(MainActivity.this, SignUpActivity.class);
            finish();
            startActivity(intent);
        }

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fragmentManager.beginTransaction().add(R.id.main_container, fragment4, "4").hide(fragment4).commit();
        fragmentManager.beginTransaction().add(R.id.main_container, fragment3, "3").hide(fragment3).commit();
        fragmentManager.beginTransaction().add(R.id.main_container, fragment2, "2").hide(fragment2).commit();
        fragmentManager.beginTransaction().add(R.id.main_container,fragment1, "1").commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_game:
                    fragmentManager.beginTransaction().hide(active).show(fragment1).commit();
                    active = fragment1;
                    return true;

                case R.id.navigation_download:
                    fragmentManager.beginTransaction().hide(active).show(fragment2).commit();
                    active = fragment2;
                    return true;

                case R.id.navigation_search:
                    fragmentManager.beginTransaction().hide(active).show(fragment3).commit();
                    active = fragment3;
                    return true;

                case R.id.navigation_account:
                    fragmentManager.beginTransaction().hide(active).show(fragment4).commit();
                    active = fragment4;
                    return true;
            }
            return false;
        }
    };

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
}
