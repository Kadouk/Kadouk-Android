package com.kadouk.app;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
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
import android.widget.ImageView;
import android.widget.Toast;

import com.kadouk.app.model.CategoryResponse;
import com.kadouk.app.model.Contents;
import com.kadouk.app.webService.APIClient;
import com.kadouk.app.webService.APIInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
//12
public class SearchFragment extends Fragment {


    EditText EditTextSearch;
    RecyclerView mRecyclerViewCat;
    RecyclerView.LayoutManager mLayoutManagerCat;
    List<Contents> contents;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    View view;

    public SearchFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_search, container, false);
        EditTextSearch = view.findViewById(R.id.search_edt);

        EditTextSearch.addTextChangedListener(mTextEditorWatcher);

        mRecyclerViewCat = view.findViewById(R.id.search_recycler_view);
        mRecyclerViewCat.setHasFixedSize(true);
        mLayoutManagerCat = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerViewCat.setLayoutManager(mLayoutManagerCat);
        mRecyclerViewCat.setLayoutManager(mLayoutManagerCat);

        ImageView voiceRecognizing = view.findViewById(R.id.search_voice_btn);
        voiceRecognizing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                        RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "fa");
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                       "برنامه ای که میخوای رو بگو");
                // title text ro gozashtam tu String nemidunam chera nemikhundesh,
                // dg majbur shodam hardCode konam code khate ghabl ro
                try {
                    startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
                } catch (ActivityNotFoundException a) {
                    Toast.makeText(getContext(), R.string.not_suport, Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    EditText EditTextSearch = view.findViewById(R.id.search_edt);
                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    EditTextSearch.setText(result.get(0));
                    // marbut be voice recognizing hast ke result ro tuye editText type mikone
                }
                break;
            }
        }
    }

    private void getSearchList(String searchText) {
        //ba tavajoh be text e ke be jahan midam behem ye list az app ha barmigardune

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<CategoryResponse> call = apiInterface.searchAppDataByDetails(searchText);
        call.enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if(response.code() == 200){
                    contents = response.body().getContents();
                    mRecyclerViewCat.setAdapter(new VerticalListAdapter(getContext(), contents));
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                Log.i("Retro","Fail");
            }
        });
    }

    private final TextWatcher mTextEditorWatcher = new TextWatcher() {

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count){
            // inja taghirate text e editText ro motevajeh mishim,
            // ba taghire har char yebar darkhaste list un text e jadid ro mikonim
           getSearchList(String.valueOf(EditTextSearch.getText()));
        }

        public void afterTextChanged(Editable s) {
        }
    };
}