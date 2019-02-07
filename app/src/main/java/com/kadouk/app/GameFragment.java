package com.kadouk.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kadouk.app.model.AllCategoryResponse;
import com.kadouk.app.model.CategoryResponse;
import com.kadouk.app.model.Contents;
import com.kadouk.app.webService.APIClient;
import com.kadouk.app.webService.APIInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//11
// in fragment marbut be tab e GAME hast, 5 ta category tushe ke harkodum yek recyclerView daran
// bad az inke ino kamel check kardi SearchFragment ro baz kon
public class GameFragment extends Fragment implements FragmentManager.OnBackStackChangedListener {

    TextView txv_cat1_name, txv_cat2_name, txv_cat3_name, txv_cat4_name, txv_cat5_name;

    RecyclerView mRecyclerViewCat1, mRecyclerViewCat2, mRecyclerViewCat3, mRecyclerViewCat4, mRecyclerViewCat5;
    RecyclerView.LayoutManager mLayoutManagerCat1, mLayoutManagerCat2, mLayoutManagerCat3, mLayoutManagerCat4, mLayoutManagerCat5;
    List<Contents> contents;
    ShowCategoryFragment showCategoryFragment = new ShowCategoryFragment();
   // MainActivity mainActivity = (MainActivity) getContext();
    public GameFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);
        // moadele hamun setContentView tu activity hast,
        // too fragment vase set kardane layout az in dastur estefadeh mikonim.

        TextView openCat1 = view.findViewById(R.id.cat1_txv_open);
        // in textView hamun نمایش همه  joloye har category hast
        openCat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle i = new Bundle();
                i.putString("id", "1");
                showCategoryFragment.setArguments(i);
                //3 khate bala vase ferestadane data be fragment jadid estefadeh shode
                ((MainActivity) getActivity()).backStackGame = "Game1";
                ((MainActivity) getActivity()).addFragmentOnTop(showCategoryFragment);
                //inam ke moadele startActivity tuye activity hast vase load fragment jadid
            }
        });

        TextView openCat2 = view.findViewById(R.id.cat2_txv_open);
        openCat2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle i = new Bundle();
                ShowCategoryFragment showCategoryFragment = new ShowCategoryFragment();
                i.putString("id", "2");
                showCategoryFragment.setArguments(i);
                ((MainActivity) getActivity()).backStackGame = "Game1";
                ((MainActivity) getActivity()).addFragmentOnTop(showCategoryFragment);
            }
        });

        TextView openCat3 = view.findViewById(R.id.cat3_txv_open);
        openCat3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle i = new Bundle();
                ShowCategoryFragment showCategoryFragment = new ShowCategoryFragment();
                i.putString("id", "3");
                showCategoryFragment.setArguments(i);
                ((MainActivity) getActivity()).backStackGame = "Game1";
                ((MainActivity) getActivity()).addFragmentOnTop(showCategoryFragment);
            }
        });

        TextView openCat4 = view.findViewById(R.id.cat4_txv_open);
        openCat4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle i = new Bundle();
                ShowCategoryFragment showCategoryFragment = new ShowCategoryFragment();
                i.putString("id", "4");
                showCategoryFragment.setArguments(i);
                ((MainActivity) getActivity()).backStackGame = "Game1";
                ((MainActivity) getActivity()).addFragmentOnTop(showCategoryFragment);
            }
        });

        TextView openCat5 = view.findViewById(R.id.cat5_txv_open);
        openCat5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle i = new Bundle();
                ShowCategoryFragment showCategoryFragment = new ShowCategoryFragment();
                i.putString("id", "5");
                showCategoryFragment.setArguments(i);
                ((MainActivity) getActivity()).backStackGame = "Game1";
                ((MainActivity) getActivity()).addFragmentOnTop(showCategoryFragment);
            }
        });

        getActivity().getSupportFragmentManager().addOnBackStackChangedListener(this);

        mRecyclerViewCat1 = view.findViewById(R.id.main_recycler_cat1);
        mRecyclerViewCat1.setHasFixedSize(true);
        mLayoutManagerCat1 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewCat1.setLayoutManager(mLayoutManagerCat1);
        SnapHelper snapHelperCat1 = new PagerSnapHelper();
        mRecyclerViewCat1.setLayoutManager(mLayoutManagerCat1);
        snapHelperCat1.attachToRecyclerView(mRecyclerViewCat1);
        // init kardane recyclerView

        mRecyclerViewCat2 = view.findViewById(R.id.main_recycler_cat2);
        mRecyclerViewCat2.setHasFixedSize(true);
        mLayoutManagerCat2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        SnapHelper snapHelperCat2 = new PagerSnapHelper();
        mRecyclerViewCat2.setLayoutManager(mLayoutManagerCat2);
        snapHelperCat2.attachToRecyclerView(mRecyclerViewCat2);

        mRecyclerViewCat3 = view.findViewById(R.id.main_recycler_cat3);
        mRecyclerViewCat3.setHasFixedSize(true);
        mLayoutManagerCat3 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        SnapHelper snapHelperCat3 = new PagerSnapHelper();
        mRecyclerViewCat3.setLayoutManager(mLayoutManagerCat3);
        snapHelperCat3.attachToRecyclerView(mRecyclerViewCat3);

        mRecyclerViewCat4 = view.findViewById(R.id.main_recycler_cat4);
        mRecyclerViewCat4.setHasFixedSize(true);
        mLayoutManagerCat4 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        SnapHelper snapHelperCat4 = new PagerSnapHelper();
        mRecyclerViewCat4.setLayoutManager(mLayoutManagerCat4);
        snapHelperCat4.attachToRecyclerView(mRecyclerViewCat4);

        mRecyclerViewCat5 = view.findViewById(R.id.main_recycler_cat5);
        mRecyclerViewCat5.setHasFixedSize(true);
        mLayoutManagerCat5 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        SnapHelper snapHelperCat5 = new PagerSnapHelper();
        mRecyclerViewCat5.setLayoutManager(mLayoutManagerCat5);
        snapHelperCat5.attachToRecyclerView(mRecyclerViewCat5);

        txv_cat1_name = view.findViewById(R.id.cat1_txv_name);
        txv_cat2_name = view.findViewById(R.id.cat2_txv_name);
        txv_cat3_name = view.findViewById(R.id.cat3_txv_name);
        txv_cat4_name = view.findViewById(R.id.cat4_txv_name);
        txv_cat5_name = view.findViewById(R.id.cat5_txv_name);

        txv_cat1_name.setText(Globals.getAllCategories().get(0).getCatName());
        mRecyclerViewCat1.setAdapter(new HorizontalListAdapter(getContext(),
                Globals.getAllCategories().get(0).getContents()));

        txv_cat2_name.setText(Globals.allCategories.get(1).getCatName());
        mRecyclerViewCat2.setAdapter(new HorizontalListAdapter(getContext(),
                Globals.getAllCategories().get(1).getContents()));

        txv_cat3_name.setText(Globals.allCategories.get(2).getCatName());
        mRecyclerViewCat3.setAdapter(new HorizontalListAdapter(getContext(),
                Globals.getAllCategories().get(2).getContents()));

        txv_cat4_name.setText(Globals.allCategories.get(3).getCatName());
        mRecyclerViewCat4.setAdapter(new HorizontalListAdapter(getContext(),
                Globals.getAllCategories().get(3).getContents()));

        txv_cat5_name.setText(Globals.allCategories.get(4).getCatName());
        mRecyclerViewCat5.setAdapter(new HorizontalListAdapter(getContext(),
                Globals.getAllCategories().get(4).getContents()));

        return view;
    }
              /////////////////////////////////////////////////////////////////////////////////////////////////
    ///////// bzese crash mishe vaghti app ro baz mikoni va back mizani, chon upButton nadare va in mikhad hide kone. /////////
             //////////////////////////////////////////////////////////////////////////////////////////////////

    public void onBackStackChanged() {
        if (getActivity().getSupportFragmentManager().getBackStackEntryCount() < 1) {
            ((MainActivity) getActivity()).hideUpButton();
        }
    }
}
