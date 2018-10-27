package com.kadouk.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.kadouk.app.model.ContentRespons;
import com.kadouk.app.webService.APIClient;
import com.kadouk.app.webService.APIInterface;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;
import com.roughike.bottombar.OnTabSelectListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.v4.app.FragmentManager.POP_BACK_STACK_INCLUSIVE;

public class MainActivity extends AppCompatActivity {

    Globals global = new Globals();

    final Fragment fragment1 = new GameFragment();
    final Fragment fragment2 = new DownloadsFragment();
    final Fragment fragment3 = new SearchFragment();
    final Fragment fragment4 = new AccountFragment();
    Fragment fGame1;
    Fragment active = fragment1;
    int fragGameLevel = 1, fragDownloadLevel= 1;
    final Fragment game = new GameFragment(), download = new DownloadsFragment(),
            search = new SearchFragment(), account = new AccountFragment();
    String FRAGMENT_OTHER = "other", backStackGame = "HOME", backStackDownload = "DOWNLOAD";
    public BottomBar bottomBar;
    public FragmentManager fragmentManager;
    BottomBarTab downloadTab;
    Intent intent;
    SharedPreferences SharedPreferences;
    public static final String MyShPref = "MyPrefers", FirstRun = "run";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Globals.setActive(game);

        SharedPreferences = getSharedPreferences(MyShPref, Context.MODE_PRIVATE);
        if (SharedPreferences.getString(FirstRun,null) == null) {
            sendAPI();

            Log.i("token", "token = " + SharedPreferences.getString(FirstRun,null));
            intent = new Intent(MainActivity.this, SignUpActivity.class);
            finish();
            startActivity(intent);
        }
        fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction().add(R.id.contentContainer, fragment4, "4").hide(fragment4).commit();
            fragmentManager.beginTransaction().add(R.id.contentContainer, fragment3, "3").hide(fragment3).commit();
            fragmentManager.beginTransaction().add(R.id.contentContainer, fragment2, "2").hide(fragment2).commit();
            fragmentManager.beginTransaction().add(R.id.contentContainer,fragment1, "1").commit();

        bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        downloadTab = bottomBar.getTabWithId(R.id.download);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.account) {

                    if(backStackGame.equals("HOME"))
                    viewFragment(fragment1, backStackGame);
                    else if(backStackGame.equals("game1"))
                        viewFragment(fGame1, "game1");
                    downloadTab.setBadgeCount(5);
                }
                if (tabId == R.id.download) {

                    if(backStackDownload.equals("DOWNLOAD"))
                        viewFragment(fragment2, backStackDownload);
                    else if(backStackDownload.equals("download1"))
                        viewFragment(fGame1, "download1");
                    //viewFragment(fragment2, FRAGMENT_OTHER);
//                    else if(backStack.equals("Game1"))
//                        viewFragment(f1, backStack);
//                    else if(backStack.equals("Game2"))
//                        viewFragment(fragment4, backStack);

                    Log.i("tabs","2");
                    downloadTab.removeBadge();
                }
                if (tabId == R.id.search) {
                    Log.i("tabs","3");
                    viewFragment(fragment3, FRAGMENT_OTHER);
                }
                if (tabId == R.id.game) {

                    Log.i("tabs", FRAGMENT_OTHER);
                    viewFragment(fragment4, FRAGMENT_OTHER);
                }
            }
        });
    }
    private void viewFragment(final Fragment fragment, String name){
        Log.i("backs", String.valueOf(active));
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.hide(active).show(fragment).commit();
        active = fragment;
        Log.i("backs", String.valueOf(active));
        final int count = fragmentManager.getBackStackEntryCount();
       // if( name.equals(FRAGMENT_OTHER)|| name.equals("game1") ) {
            fragmentTransaction.addToBackStack(name);
            Log.i("backs", String.valueOf(count)+"   "+name);
        Log.i("backs", String.valueOf(fragmentManager.getBackStackEntryCount()));
       // }
        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if( fragmentManager.getBackStackEntryCount() <= count){
                    if(backStackDownload.equals("DOWNLOAD")){
                        fragmentManager.beginTransaction().hide(fragment2).hide(fragment3).hide(fragment4).commit();
                        fragmentManager.popBackStack(backStackGame, POP_BACK_STACK_INCLUSIVE);
                        fragmentManager.removeOnBackStackChangedListener(this);
                        bottomBar.selectTabAtPosition(0, true);
                    }else if(backStackDownload.equals("download1")) {
                        fragmentManager.beginTransaction().hide(fragment1).hide(fragment3).hide(fragment4).commit();
                        fragmentManager.popBackStack(backStackDownload, POP_BACK_STACK_INCLUSIVE);
                        fragmentManager.removeOnBackStackChangedListener(this);
                        backStackDownload = "DOWNLOAD";
                        active = fragment2;
                    }

                    if(backStackGame.equals("HOME"))
                        active = fragment1;
                    if(backStackGame.equals("game1"))
                        active = fGame1;
                }
            }
        });
    }

    public void addFragmentOnTop(Fragment fragment) {
        fGame1 = fragment;
        fragmentManager.beginTransaction().add(R.id.contentContainer,fGame1,"game1").commit();
        viewFragment(fGame1, "game1");
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
}
