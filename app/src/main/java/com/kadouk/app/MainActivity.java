package com.kadouk.app;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.kadouk.app.model.Details;
import com.kadouk.app.webService.APIClient;
import com.kadouk.app.webService.APIInterface;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    final Fragment gameFragment = new GameFragment();
    final Fragment downloadsFragment = new DownloadsFragment();
    final Fragment searchFragment = new SearchFragment();
    final Fragment accountFragment = new AccountFragment();
    Fragment fGame1;
    final Fragment game = new GameFragment();
    String FRAGMENT_OTHER = "other", backStackGame = "HOME";
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Globals.setActive(game);

        fragmentManager = getSupportFragmentManager();

//        fragmentManager.beginTransaction().add(R.id.fragment_container, accountFragment, "4")
//                .hide(accountFragment).commit();
//        fragmentManager.beginTransaction().add(R.id.fragment_container, searchFragment, "3")
//                .hide(searchFragment).commit();
//        fragmentManager.beginTransaction().add(R.id.fragment_container, downloadsFragment, "2")
//                .hide(downloadsFragment).commit();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container,gameFragment, "0").commit();
        fragmentTransaction.addToBackStack(gameFragment.toString());

       bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.navigation);
        bottomNavigationView.setSelectedItemId(R.id.game);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {

                            case R.id.account:
                                viewFragment(accountFragment);
                                break;

                            case R.id.download:
                                viewFragment(downloadsFragment);
                                break;

                            case R.id.search:
                                viewFragment(searchFragment);
                                break;

                            case R.id.game:
                                viewFragment(gameFragment);
                                break;
                        }
                        return true;
                    }
                });
    }

    private void viewFragment(Fragment fragment){
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_out_right, android.R.anim.slide_out_right);
        fragmentTransaction.replace(R.id.fragment_container,fragment).commit();

//        if(addToBackStack) {
//            Log.i("backstack", fragment.toString());
//            fragmentTransaction.addToBackStack(null);
//        }
//        fragmentTransaction.hide(active).show(fragment).commit();
//        active = fragment;

//        Log.i("backs", String.valueOf(active));
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.hide(active).show(fragment).commit();
//        active = fragment;
//        Log.i("backs", String.valueOf(active));
//        final int BackstackCount = fragmentManager.getBackStackEntryCount();
//        // if( name.equals(FRAGMENT_OTHER)|| name.equals("game1") ) {
//        fragmentTransaction.addToBackStack(name);
//        Log.i("backs", String.valueOf(BackstackCount)+"   "+name);
//        Log.i("backs", String.valueOf(fragmentManager.getBackStackEntryCount()));
//        // }
//        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
//            @Override
//            public void onBackStackChanged() {
//                if( fragmentManager.getBackStackEntryCount() <= BackstackCount){
//                    if(backStackDownload.equals("DOWNLOAD")){
//
//                        fragmentManager.beginTransaction().hide(downloadsFragment)
//                                .hide(searchFragment).hide(accountFragment).commit();
//                        fragmentManager.popBackStack(backStackGame, POP_BACK_STACK_INCLUSIVE);
//                        fragmentManager.removeOnBackStackChangedListener(this);
//
//                    }else if(backStackDownload.equals("download1")) {
//
//                        fragmentManager.beginTransaction().hide(gameFragment).hide(searchFragment)
//                                .hide(accountFragment).commit();
//                        fragmentManager.popBackStack(backStackDownload, POP_BACK_STACK_INCLUSIVE);
//                        backStackDownload = "DOWNLOAD";//                        fragmentManager.removeOnBackStackChangedListener(this);
//                        active = downloadsFragment;
//                    }
//
//                    if(backStackGame.equals("HOME"))
//                        active = gameFragment;
//                    if(backStackGame.equals("game1"))
//                        active = fGame1;
//                }
//            }
//        });
    }

    public void addFragmentOnTop(Fragment fragment) {
        fGame1 = fragment;
        viewFragment(fGame1);
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


    //in hamun tabeyi hast ke vase font bayad be har activity ezafe she
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragments = getSupportFragmentManager();
       // Fragment homeFrag = fragments.findFragmentByTag("0");
        Fragment currentFragment = fragmentManager.findFragmentById(R.id.fragment_container);
        int tab = bottomNavigationView.getSelectedItemId();
        Log.i("backstackcunter", String.valueOf(fragments.getBackStackEntryCount()+
                currentFragment.toString())+"id==="+tab);
        if (fragments.getBackStackEntryCount() >= 1 && currentFragment!=gameFragment) {
            // We have fragments on the backstack that are poppable
            //fragmentManager.popBackStack(gameFragment.toString(), POP_BACK_STACK_INCLUSIVE);
            //fragments.popBackStackImmediate();
            bottomNavigationView.setSelectedItemId(R.id.game);
        } else {
            Log.i("backstackcunter", "backssss");
            supportFinishAfterTransition();
        }
    }
}