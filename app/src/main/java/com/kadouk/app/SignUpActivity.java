package com.kadouk.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    public void register(View view) {

        final String MyShPref = "MyPrefers",FirstRun = "run";
        SharedPreferences SharedPreferences;
        SharedPreferences = getSharedPreferences(MyShPref, Context.MODE_PRIVATE);
        SharedPreferences.Editor sEdit = SharedPreferences.edit();
        sEdit.putString(FirstRun, String.valueOf("run"));
        sEdit.apply();
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
