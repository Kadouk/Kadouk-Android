package com.kadouk.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameFragment extends Fragment {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    ArrayList<String> itemName;
    ArrayList<Integer> itemImage;

    View viewList;
    public GameFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game, container, false);

        mRecyclerView = view.findViewById(R.id.main_recycler_list);

        preparingList();
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new HorizontalListAdapter(getContext(), itemName, itemImage);
        mRecyclerView.setAdapter(mAdapter);
        return view;
    }

    public void preparingList(){

        itemName = new ArrayList<>(Arrays.asList("Cheesy...", "Crispy... ", "Fizzy...", "Cool...", "Softy..."
                , "Fruity...", "Fresh...", "Sticky...","Softy...", "Fruity...",
                "Fresh...", "Sticky...","Softy...", "Fruity...", "Fresh...",
                "Sticky...","Softy...", "Fruity...", "Fresh...", "Sticky...",
                "Sticky...","Softy...", "Fruity...", "Fresh...", "Sticky...",
                "Sticky...","Softy...", "Fruity...", "Fresh...", "Sticky..."));

        itemImage = new ArrayList<>(Arrays.asList(R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background,
                R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background,
                R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background,
                R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background,
                R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background,
                R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background, R.drawable.ic_launcher_background));
    }


}
