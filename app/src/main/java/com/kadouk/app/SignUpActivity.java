package com.kadouk.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;

import com.kadouk.app.webService.APIClient;
import com.kadouk.app.webService.APIInterface;

import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;

public class SignUpActivity extends AppCompatActivity {

    Short EditTextMaxLength = 11;
    FloatingActionButton fab;
    EditText EditTextNumber;
    public static final String MyShPref = "MyPrefers",FirstRun = "run";
    SharedPreferences SharedPreferences;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_enter_number);
        final Animation animation, animation1, animation2 ;
        fab = findViewById(R.id.fab);
        EditTextNumber = findViewById(R.id.signup_edt_number);
        EditTextNumber.addTextChangedListener(mTextEditorWatcher);
        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move);
        animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move1);
        animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move2);
        final ImageView image = (ImageView)findViewById(R.id.imageView);
        final ImageView image1 = (ImageView)findViewById(R.id.login_blue_circle);
        final ImageView image2= (ImageView)findViewById(R.id.login_white_circle);

        SharedPreferences = getSharedPreferences(MyShPref, Context.MODE_PRIVATE);

        image.startAnimation(animation);
        // animation1.setAnimationListener

        animation.setAnimationListener(new Animation.AnimationListener(){
        @Override
        public void onAnimationStart(Animation arg0) {
            image1.startAnimation(animation1);
        }
        @Override
        public void onAnimationRepeat(Animation arg0) {

        }
        @Override
        public void onAnimationEnd(Animation arg0) {

        }
        });

        animation1.setAnimationListener(new Animation.AnimationListener(){
            @Override
            public void onAnimationStart(Animation arg0) {
                image2.startAnimation(animation2);
            }
            @Override
            public void onAnimationRepeat(Animation arg0) {

            }
            @Override
            public void onAnimationEnd(Animation arg0) {

            }
        });
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

    private final TextWatcher mTextEditorWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if(s.length() == EditTextMaxLength){
                fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.olivine)));
                fab.setClickable(true);
                fab.setVisibility(View.VISIBLE);
            }else {
                fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorAccent)));
                fab.setClickable(false);
                fab.setVisibility(View.GONE);
            }
        }

        public void afterTextChanged(Editable s) {
        }
    };
}