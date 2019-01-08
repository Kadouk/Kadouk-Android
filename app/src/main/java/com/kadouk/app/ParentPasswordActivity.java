package com.kadouk.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.kadouk.app.model.Details;
import com.kadouk.app.model.RegisterResponse;
import com.kadouk.app.webService.APIClient;
import com.kadouk.app.webService.APIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ParentPasswordActivity extends AppCompatActivity {


    Short EditTextMaxLength = 4;
    FloatingActionButton fab;
    EditText EditTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_password);

        fab = findViewById(R.id.signup_password_fab);
        fab.setEnabled(false);
        fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.grandis)));
        EditTextPassword = findViewById(R.id.signup_password_edt);
        EditTextPassword.addTextChangedListener(mTextEditorWatcher);
    }

    public void sendPassword(View view) {
        String password;
        password = EditTextPassword.getText().toString();
        sendParentPassword(password);
    }




    private void sendParentPassword(String password) {
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Log.i("tokennnnn", Globals.getToken());
        Call<Details> call = apiInterface.sendParentPassword(password,"Bearer " + Globals.getToken());
        call.enqueue(new Callback<Details>() {
            @Override
            public void onResponse(Call<Details> call, Response<Details> response) {
                if(response.isSuccessful()){

                    final String MyShPref = "MyPrefers",FirstRun = "run";
                    SharedPreferences SharedPreferences;
                    SharedPreferences = getSharedPreferences(MyShPref, Context.MODE_PRIVATE);
                    SharedPreferences.Editor sEdit = SharedPreferences.edit();
                    sEdit.putString(FirstRun, String.valueOf("run"));
                    sEdit.apply();

                    Intent intent = new Intent(ParentPasswordActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Details> call, Throwable t) {

            }
        });
    }


    private final TextWatcher mTextEditorWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(s.length() == EditTextMaxLength){
                fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.olivine)));
                fab.setEnabled(true);
            }else {
                fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.grandis)));
                fab.setEnabled(false);
            }
        }

        public void afterTextChanged(Editable s) {
        }
    };
}
