package com.kadouk.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.kadouk.app.webService.APIClient;
import com.kadouk.app.webService.APIInterface;

import retrofit2.Call;
import retrofit2.Callback;

public class SignUpActivity extends AppCompatActivity {

    AppCompatEditText  EditTextNumber, EditTextName, loginEditTextNumber, loginEditTextPassword ;
    String name, kidGender = "boy", month, year, number, password, cPassword, birth, loginNumber, loginPassword;
    public static final String MyShPref = "MyPrefers",FirstRun = "run";
    SharedPreferences SharedPreferences;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_enter_number);

        SharedPreferences = getSharedPreferences(MyShPref, Context.MODE_PRIVATE);

    }

    @SuppressLint("WrongViewCast")
    public void sendNumber(View view) {

        EditTextNumber = findViewById(R.id.signup_edt_number);
        if(EditTextNumber.getText().length() < 11){
            EditTextNumber.setError("There must be 11 numbers");
        }else {
            Log.i("number",String.valueOf(EditTextNumber.getText()));
            String number = String.valueOf(EditTextNumber.getText());
            Globals.setNumber(number);
            sendPhoneNumber(number);
//            intent = new Intent(SignUpActivity.this, SignUpEnterCodeActivity.class);
//            finish();
//            startActivity(intent);
        }
        //number = EditTextNumber.getText().toString();
        // register(name, kidGender, birth, number, password, cPassword);
    }

    private void sendPhoneNumber(String Number){

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<com.kadouk.app.model.Response> call =  apiInterface.sendPhoneNumber(Number);
        call.enqueue(new Callback<com.kadouk.app.model.Response>() {
            @Override
            public void onResponse(Call<com.kadouk.app.model.Response> call, retrofit2.Response<com.kadouk.app.model.Response> response){
                if(response.isSuccessful()) {

                    if (String.valueOf(response.body().getStatus()).equals("200")){
                        Log.i("LOGIN", String.valueOf(response.body().getStatus()));
                        intent = new Intent(SignUpActivity.this, SignUpEnterCodeActivity.class);
                       // finish();
                        startActivity(intent);
                    }

//                    SharedPreferences = getSharedPreferences(MyShPref, Context.MODE_PRIVATE);
//                    SharedPreferences.Editor sEdit = SharedPreferences.edit();
//                    sEdit.putString(Token, String.valueOf(response.body().getToken()));
//                    sEdit.apply();
//
//                    intent = new Intent(SignUpActivity.this, MainActivity.class);
//                    finish();
//                    startActivity(intent);
                }else {
                    Log.i("LOGIN", "not response");
                }
            }

            @Override
            public void onFailure(Call<com.kadouk.app.model.Response> call, Throwable t) {
                Log.i("LOGIN", "connection problem!");
            }
        });
    }

    public void signUpLater(View view) {

        SharedPreferences = getSharedPreferences(MyShPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor sEdit = SharedPreferences.edit();
        sEdit.putString(FirstRun, String.valueOf("run"));
        sEdit.apply();

        intent = new Intent(SignUpActivity.this, MainActivity.class);
        //finish();
        startActivity(intent);
    }
}