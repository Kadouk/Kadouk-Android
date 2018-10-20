package com.kadouk.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;
import com.roughike.bottombar.OnTabSelectListener;

import static android.support.v4.app.FragmentManager.POP_BACK_STACK_INCLUSIVE;

public class MainActivity extends FragmentActivity {
    Globals global = new Globals();

    final Fragment game = new GameFragment(), download = new DownloadsFragment(),
            search = new SearchFragment(), account = new AccountFragment();
    String FRAGMENT_OTHER = "3";
    BottomBar bottomBar;
    public static FragmentManager fragmentManager;
    BottomBarTab downloadTab;
    Intent intent;
    SharedPreferences SharedPreferences;
    public static final String MyShPref = "MyPrefers", FirstRun = "run";
    final FragmentManager fragmentManagerr = getSupportFragmentManager();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Globals.setActive(game);

        SharedPreferences = getSharedPreferences(MyShPref, Context.MODE_PRIVATE);
        if (SharedPreferences.getString(FirstRun,null) == null) {

            Log.i("token", "token = " + SharedPreferences.getString(FirstRun,null));
            intent = new Intent(MainActivity.this, SignUpActivity.class);
            finish();
            startActivity(intent);
        }
        fragmentManager = getSupportFragmentManager();
//        final Fragment fragment = fragmentManager.findFragmentById(R.id.contentContainer);
//        if (fragment == null) {
//
//            FragmentTransaction ft = fragmentManager.beginTransaction();
//            ft.add(R.id.contentContainer, new GameFragment());
//            ft.commit();
//        }

        bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        downloadTab = bottomBar.getTabWithId(R.id.download);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.account) {
                    Log.i("tabs","1");
//                    FragmentTransaction ft = fragmentManager.beginTransaction();
//                    ft.replace(R.id.contentContainer ,new AccountFragment());
//                    ft.addToBackStack("Main_App");
//                    ft.commit();
                    // The tab with id R.id.tab_favorites was selected,
                    // change your content accordingly.
                    viewFragment(new AccountFragment(), FRAGMENT_OTHER);
                    downloadTab.setBadgeCount(5);
                }
                if (tabId == R.id.download) {
//                    FragmentTransaction ft = fragmentManager.beginTransaction();
//                    ft.replace(R.id.contentContainer ,new SearchFragment());
//                    ft.addToBackStack("Main_App");
//                    ft.commit();
                    viewFragment(new DownloadsFragment(), FRAGMENT_OTHER);

                    Log.i("tabs","2");
                    // The tab with id R.id.tab_favorites was selected,
                    // change your content accordingly.
                    downloadTab.removeBadge();
                }
                if (tabId == R.id.search) {
                    Log.i("tabs","3");
                    viewFragment(new SearchFragment(), "");

//                    FragmentTransaction ft = fragmentManager.beginTransaction();
//                    ft.replace(R.id.contentContainer ,game);
//                    ft.addToBackStack("Main_App");
//                    ft.commit();
                    // The tab with id R.id.tab_favorites was selected,
                    // change your content accordingly.
                }
                if (tabId == R.id.game) {
                    Log.i("tabs","3");
                    viewFragment(new GameFragment(), "");

//                    FragmentTransaction ft = fragmentManager.beginTransaction();
//                    ft.replace(R.id.contentContainer ,game);
//                    ft.addToBackStack("Main_App");
//                    ft.commit();
                    // The tab with id R.id.tab_favorites was selected,
                    // change your content accordingly.
                }
            }
        });
    }
    private void viewFragment(Fragment fragment, String name){

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.contentContainer, fragment);
        // 1. Know how many fragments there are in the stack
        final int count = fragmentManager.getBackStackEntryCount();
        // 2. If the fragment is **not** "home type", save it to the stack
        if( name.equals( FRAGMENT_OTHER) ) {
            fragmentTransaction.addToBackStack(name);
            Log.i("backs", String.valueOf(count));
        }
        // Commit !
        fragmentTransaction.commit();
        // 3. After the commit, if the fragment is not an "home type" the back stack is changed, triggering the
        // OnBackStackChanged callback
        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                // If the stack decreases it means I clicked the back button
                if( fragmentManager.getBackStackEntryCount() <= count){
                    // pop all the fragment and remove the listener
                    fragmentManager.popBackStack(FRAGMENT_OTHER, POP_BACK_STACK_INCLUSIVE);
                    fragmentManager.removeOnBackStackChangedListener(this);
                    // set the home button selected
                   // bottomBar.getMenu().getItem(0).setChecked(true);
                    bottomBar.selectTabAtPosition(2, false);
                }
            }
        });
    }
}
