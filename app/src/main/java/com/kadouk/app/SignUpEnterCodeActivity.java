package com.kadouk.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.kadouk.app.model.Response;
import com.kadouk.app.webService.APIClient;
import com.kadouk.app.webService.APIInterface;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by zoli on 08/28/2018.
 */

public class SignUpEnterCodeActivity extends AppCompatActivity {
    EditText EditTextCode;
    public static final String MyShPref = "MyPrefers", FirstRun = "run",
            authenticationToken = "Token";
    SharedPreferences SharedPreferences;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_enter_code);

        EditTextCode = findViewById(R.id.signup_edt_code);
        FloatingActionButton fab = findViewById(R.id.fab_code);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//                Log.i("FABB",":)");
//            }
//        });
    }

    public void sendCode(View view) {

        if(EditTextCode.getText().length() < 11){
            EditTextCode.setError("There must be 11 numbers");
        }else{
            String code =  String.valueOf(EditTextCode.getText());
            String number = Globals.getNumber();
            sendCode(code,number);
        }
    }

    private void sendCode(String code, String number) {

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<Response> call = apiInterface.sendVerificationCode(code,number);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if(response.code() == 200){
                    Log.i("LOGIN", String.valueOf(response.body().getError()));
                    if(String.valueOf(response.body().getError()).equals("Wrong Code") ){
                        EditTextCode.setError("wrong code");
                        Log.i("LOGIN", "umad inja");
                    }else if(String.valueOf(response.body().getError()).equals("Not Register")) {
                        Log.i("LOGIN", "sabt nashode");
                        intent = new Intent(SignUpEnterCodeActivity.this, SignUpNameGenderBirthdayActivity.class);
                        startActivity(intent);
                    }else if(String.valueOf(response.body().getToken()).length() > 1) {
                        Log.i("LOGIN", "sabt shode");
                        String Token = String.valueOf(response.body().getToken());
                        intent = new Intent(SignUpEnterCodeActivity.this, MainActivity.class);
                        SharedPreferences = getSharedPreferences(MyShPref, Context.MODE_PRIVATE);
                        SharedPreferences.Editor sEdit = SharedPreferences.edit();
                        sEdit.putString(FirstRun, String.valueOf("run"));
                        sEdit.putString(authenticationToken, Token);
                        Globals.setToken(Token);
                        sEdit.apply();
                        finish();
                        startActivity(intent);
                    }
//                    Log.i("LOGIN", String.valueOf(response.body().getToken()));
//
//                    SharedPreferences = getSharedPreferences(MyShPref, Context.MODE_PRIVATE);
//                    SharedPreferences.Editor sEdit = SharedPreferences.edit();
//                    sEdit.putString(FirstRun, String.valueOf("run"));
//                    sEdit.apply();
//
//                    SharedPreferences = getSharedPreferences(MyShPref, Context.MODE_PRIVATE);
//                    Intent intent = new Intent(SignUpEnterCodeActivity.this, MainActivity.class);
//                    finish();
//                    startActivity(intent);

                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

            }
        });
    }

    public void backToEnterNumber(View view) {
        Intent intent = new Intent(SignUpEnterCodeActivity.this, SignUpActivity.class);
        finish();
        startActivity(intent);
    }
}
