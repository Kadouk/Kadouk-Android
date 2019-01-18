package com.kadouk.app;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.kadouk.app.model.CatagoryResponse;
import com.kadouk.app.model.Content;
import com.kadouk.app.model.Contents;
import com.kadouk.app.webService.APIClient;
import com.kadouk.app.webService.APIInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {


    EditText EditTextSearch;
    RecyclerView mRecyclerViewCat;
    RecyclerView.LayoutManager mLayoutManagerCat;
    List<Contents> contents;

    public SearchFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        EditTextSearch = view.findViewById(R.id.search_edt);

        EditTextSearch.addTextChangedListener(mTextEditorWatcher);

        mRecyclerViewCat = view.findViewById(R.id.search_recycler_view);
        mRecyclerViewCat.setHasFixedSize(true);
        mLayoutManagerCat = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerViewCat.setLayoutManager(mLayoutManagerCat);
        mRecyclerViewCat.setLayoutManager(mLayoutManagerCat);

        return view;
    }


    private void getSearchList(String searchText) {

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<CatagoryResponse> call = apiInterface.searchAppDataByDetails(searchText);
        call.enqueue(new Callback<CatagoryResponse>() {
            @Override
            public void onResponse(Call<CatagoryResponse> call, Response<CatagoryResponse> response) {
                if(response.code() == 200){
                    contents = response.body().getContents();
                    mRecyclerViewCat.setAdapter(new VerticalListAdapter(getContext(), contents));
                }
            }

            @Override
            public void onFailure(Call<CatagoryResponse> call, Throwable t) {
                Log.i("Retro","Fail");
            }
        });
    }

    private final TextWatcher mTextEditorWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count){

           getSearchList(String.valueOf(EditTextSearch.getText()));
        }

        public void afterTextChanged(Editable s) {
        }
    };
}
