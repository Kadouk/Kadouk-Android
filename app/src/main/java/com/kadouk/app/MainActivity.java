package com.kadouk.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.kadouk.app.model.Details;
import com.kadouk.app.webService.APIClient;
import com.kadouk.app.webService.APIInterface;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;
import com.roughike.bottombar.OnTabSelectListener;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.v4.app.FragmentManager.POP_BACK_STACK_INCLUSIVE;

public class MainActivity extends AppCompatActivity {

    final Fragment gameFragment = new GameFragment();
    final Fragment downloadsFragment = new DownloadsFragment();
    final Fragment searchFragment = new SearchFragment();
    final Fragment accountFragment = new AccountFragment();
    Fragment fGame1;
    Fragment active = gameFragment;
    final Fragment game = new GameFragment(), download = new DownloadsFragment(),
            search = new SearchFragment(), account = new AccountFragment();
    String FRAGMENT_OTHER = "other", backStackGame = "HOME", backStackDownload = "DOWNLOAD";
    public BottomBar bottomBar;
    public FragmentManager fragmentManager;
    BottomBarTab downloadTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Globals.setActive(game);

        fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction().add(R.id.contentContainer, accountFragment, "4").hide(accountFragment).commit();
            fragmentManager.beginTransaction().add(R.id.contentContainer, searchFragment, "3").hide(searchFragment).commit();
            fragmentManager.beginTransaction().add(R.id.contentContainer, downloadsFragment, "2").hide(downloadsFragment).commit();
            fragmentManager.beginTransaction().add(R.id.contentContainer,gameFragment, "1").commit();

        bottomBar = findViewById(R.id.bottomBar);
        downloadTab = bottomBar.getTabWithId(R.id.download);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {

                if (tabId == R.id.account) {

                    Log.i("tabs", FRAGMENT_OTHER);
                    viewFragment(accountFragment, FRAGMENT_OTHER);
                }

                if (tabId == R.id.download) {

                    if(backStackDownload.equals("DOWNLOAD"))
                        viewFragment(downloadsFragment, backStackDownload);
                    else if(backStackDownload.equals("download1"))
                        viewFragment(fGame1, "download1");

                    Log.i("tabs","2");
                    downloadTab.removeBadge();
                }
                if (tabId == R.id.search) {
                    Log.i("tabs","3");
                    viewFragment(searchFragment, FRAGMENT_OTHER);
                }

                if (tabId == R.id.game) {
                    if(backStackGame.equals("HOME"))
                        viewFragment(gameFragment, backStackGame);
                    else if(backStackGame.equals("game1"))
                        viewFragment(fGame1, "game1");
                    downloadTab.setBadgeCount(5);
                }
            }
        });

        bottomBar.setDefaultTabPosition(3);
    }
    private void viewFragment(final Fragment fragment, String name){
        Log.i("backs", String.valueOf(active));
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.hide(active).show(fragment).commit();
        active = fragment;
        Log.i("backs", String.valueOf(active));
        final int BackstackCount = fragmentManager.getBackStackEntryCount();
       // if( name.equals(FRAGMENT_OTHER)|| name.equals("game1") ) {
            fragmentTransaction.addToBackStack(name);
            Log.i("backs", String.valueOf(BackstackCount)+"   "+name);
        Log.i("backs", String.valueOf(fragmentManager.getBackStackEntryCount()));
       // }
        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if( fragmentManager.getBackStackEntryCount() <= BackstackCount){
                    if(backStackDownload.equals("DOWNLOAD")){
                        fragmentManager.beginTransaction().hide(downloadsFragment).hide(searchFragment).hide(accountFragment).commit();
                        fragmentManager.popBackStack(backStackGame, POP_BACK_STACK_INCLUSIVE);
                        fragmentManager.removeOnBackStackChangedListener(this);
                        bottomBar.selectTabAtPosition(0, true);
                    }else if(backStackDownload.equals("download1")) {
                        fragmentManager.beginTransaction().hide(gameFragment).hide(searchFragment).hide(accountFragment).commit();
                        fragmentManager.popBackStack(backStackDownload, POP_BACK_STACK_INCLUSIVE);
                        fragmentManager.removeOnBackStackChangedListener(this);
                        backStackDownload = "DOWNLOAD";
                        active = downloadsFragment;
                    }

                    if(backStackGame.equals("HOME"))
                        active = gameFragment;
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


    private void details() {

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

        Call<Details> call = apiInterface.details(0 + "","Bearer " + Globals.getToken());
        call.enqueue(new Callback<Details>() {
            @Override
            public void onResponse(Call<Details> call, Response<Details> response) {
                Log.i("Details", response.code() + "");
                if(response.code() == 200){
                    Log.i("details", response.body().getName() + "");
                }
            }

            @Override
            public void onFailure(Call<Details> call, Throwable t) {
                Log.i("Retro","Fail");
            }
        });
    }

    public void showUpButton() { getSupportActionBar().setDisplayHomeAsUpEnabled(true); }
    public void hideUpButton() { getSupportActionBar().setDisplayHomeAsUpEnabled(false); }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }
}
