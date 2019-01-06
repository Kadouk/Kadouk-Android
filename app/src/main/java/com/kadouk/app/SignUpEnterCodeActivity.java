package com.kadouk.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.kadouk.app.model.RegisterResponse;
import com.kadouk.app.webService.APIClient;
import com.kadouk.app.webService.APIInterface;

import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;

import static android.graphics.Color.BLACK;

/**
 * Created by zoli on 08/28/2018.
 */

public class SignUpEnterCodeActivity extends AppCompatActivity {

    Short EditTextMaxLength = 11;
    EditText EditTextCode;
    public static final String MyShPref = "MyPrefers", FirstRun = "run",
            authenticationToken = "Token";
    SharedPreferences SharedPreferences;
    Intent intent;

    FloatingActionButton fab;
    Timer timer;
    TimerTask TimerTask;
    int  time = 1000, timeLeft = 0;
    TextView textViewResendCode,textViewTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_enter_code);

        fab = findViewById(R.id.signup_fab_code);
        fab.setEnabled(false);
        fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.grandis)));


        Toolbar toolbar = findViewById(R.id.signup_enter_code_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        EditTextCode = findViewById(R.id.signup_edt_code);
        EditTextCode.addTextChangedListener(mTextEditorWatcher);

        textViewResendCode = findViewById(R.id.signup_txv_receive_code);
        textViewTime = findViewById(R.id.signup_txv_time);
        EditTextCode = findViewById(R.id.signup_edt_code);
        startTimer(time);

        EditTextCode.setVisibility(View.VISIBLE);
        textViewResendCode.setVisibility(View.VISIBLE);
        textViewTime.setVisibility(View.VISIBLE);

//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//                Log.i("FABB",":)");
//            }
//        });
    }

    View.OnClickListener onclicklistener = new View.OnClickListener() {

        @SuppressLint("ResourceAsColor")
        @Override
        public void onClick(View v) {

            startTimer(time);
            textViewTime.setTextColor(R.color.black);
            textViewTime.setOnClickListener(null);

        }
    };

    public void sendCode(View view) {

        if(EditTextCode.getText().length() < EditTextMaxLength){
            EditTextCode.setError("There must be 11 numbers");
        }else{
            String code =  String.valueOf(EditTextCode.getText());
            String number = Globals.getNumber();
            sendCode(code,number);
        }
    }

    private void sendCode(String code, String number) {

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<RegisterResponse> call = apiInterface.sendVerificationCode(code,number);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, retrofit2.Response<RegisterResponse> response) {
                if(response.code() == 200){
                    Log.i("LOGIN", String.valueOf(response.body().getError()));
                    if(String.valueOf(response.body().getError()).equals("Wrong Code") ){
                        EditTextCode.setError("wrong code");
                        Log.i("LOGIN", "wrong code");
                    }else if(String.valueOf(response.body().getError()).equals("Not Register")) {
                        Log.i("LOGIN", "Not Register");
                        intent = new Intent(SignUpEnterCodeActivity.this, SignUpProfileActivity.class);
                        startActivity(intent);
                    }else if(String.valueOf(response.body().getToken()).length() > 1) {
                        Log.i("LOGIN", "submit");
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
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {

            }
        });
    }

    private class timerTask extends TimerTask {

        int timeCounter = 0, lastPeriod = 10;

        @Override
        public void run() {

            timeCounter ++;

            runOnUiThread (new Runnable() {

                @Override
                public void run() {

                    timeLeft = lastPeriod - timeCounter;
                    textViewResendCode.setText( getString(R.string.wait_to_receive_code));
                    textViewTime.setText(String.format("%02d",00) + ":"+ String.format("%02d",timeLeft));
                    if(timeLeft == 0){
                        timeCounter = 0;
                        timer.cancel();
                        timer = null;
                        textViewTime.setTextColor(getResources().getColor(R.color.colorAccent));
                        textViewResendCode.setText( getString(R.string.none_messages));
                        textViewTime.setText(getString(R.string.resend_code));
                        textViewTime.setOnClickListener(onclicklistener);
                    }
                }
            });
        }
    }

    private void startTimer(int time){

        if(timer != null){
            timer.cancel();
            timer = null;
        }
        timer = new Timer();
        TimerTask = new timerTask();
        timer.schedule(TimerTask, 5,time);
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
