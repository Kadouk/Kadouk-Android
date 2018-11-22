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
import android.widget.TextView;

import com.kadouk.app.model.Response;
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

    EditText EditTextCode;
    public static final String MyShPref = "MyPrefers", FirstRun = "run",
            authenticationToken = "Token";
    SharedPreferences SharedPreferences;
    Intent intent;

    Timer timer;
    TimerTask TimerTask;
    int  time = 1000, timeLeft = 0;
    TextView textViewResendCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_enter_code);
        textViewResendCode = (TextView) findViewById(R.id.signup_txv_receive_code);
        EditTextCode = findViewById(R.id.signup_edt_code);
        FloatingActionButton fab = findViewById(R.id.fab_code);
        startTimer(time);
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

        @Override
        public void onClick(View v) {

            startTimer(time);
            textViewResendCode.setTextColor(BLACK);
            textViewResendCode.setOnClickListener(null);

        }
    };

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

    private class timerTask extends TimerTask {

        int timeCounter = 0, lastPeriod = 10;

        @Override
        public void run() {

            timeCounter ++;

            runOnUiThread (new Runnable() {

                @Override
                public void run() {

                    timeLeft = lastPeriod - timeCounter;
                    textViewResendCode.setText( getString(R.string.wait_to_receive_code) + "  " +String.format("%02d",00) + ":"+ String.format("%02d",timeLeft));

                    if(timeLeft == 0){
                        timeCounter = 0;
                        timer.cancel();
                        timer = null;
                        textViewResendCode.setTextColor(getResources().getColor(R.color.colorAccent));
                        textViewResendCode.setText(getString(R.string.resend_code));
                        textViewResendCode.setOnClickListener(onclicklistener);
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
}
