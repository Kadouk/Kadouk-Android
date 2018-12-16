package com.kadouk.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.kadouk.app.model.Response;
import com.kadouk.app.webService.APIClient;
import com.kadouk.app.webService.APIInterface;

import retrofit2.Call;
import retrofit2.Callback;

public class SignUpNameGenderBirthdayActivity extends AppCompatActivity {
    String name, kidGender = "boy", day, month, year;
    EditText EditTextName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_name_gender_birthday);
        EditTextName = findViewById(R.id.signup_edt_name);

        Spinner SpinnerDay = findViewById(R.id.signup_name_gender_spinner_day);
        Spinner SpinnerMonth = findViewById(R.id.signup_name_gender_spinner_month);
        Spinner SpinnerYear = findViewById(R.id.signup_name_gender_spinner_year);


        ArrayAdapter<CharSequence> dayAdapter = ArrayAdapter.createFromResource(this,
                R.array.day, android.R.layout.simple_spinner_item);
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerDay.setAdapter(dayAdapter);
        SpinnerDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                day = adapterView.getItemAtPosition(i).toString();
                Log.i("SPINNER",adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        ArrayAdapter<CharSequence> monthAdapter = ArrayAdapter.createFromResource(this,
                R.array.month, android.R.layout.simple_spinner_item);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerMonth.setAdapter(monthAdapter);
        SpinnerMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                month = adapterView.getItemAtPosition(i).toString();
                Log.i("SPINNER",adapterView.getItemAtPosition(i).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<CharSequence> yearAdapter = ArrayAdapter.createFromResource(this,
                R.array.year, android.R.layout.simple_spinner_item);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinnerYear.setAdapter(yearAdapter);
        SpinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                year = adapterView.getItemAtPosition(i).toString();
                Log.i("SPINNER",adapterView.getItemAtPosition(i).toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

   // public void next(View view) {
        //setContentView(R.layout.term_and_condition);
   // }

    public void back(View view) {
        Intent intent = new Intent(SignUpNameGenderBirthdayActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    public void onGenderItemClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.signup_name_gender_radio_boy:
                if (checked)
                    kidGender = "boy";
                break;
            case R.id.signup_name_gender_radio_girl:
                if (checked)
                    kidGender = "girl";
                break;
        }
    }

    public void next(View view) {
        name = EditTextName.getText().toString();
        String birth = year + "/" + month + "/" + day;
        Log.i("data", name +"/"+ kidGender + "/" + birth);
        String number = Globals.getNumber();
        Log.i("data", name +"/"+ kidGender +"/"+ number+ "/" + birth);
        sendUserInformation(name, kidGender, number, birth);
    }

    public void backOfTerm(View view) {
        Intent intent = new Intent(SignUpNameGenderBirthdayActivity.this, SignUpNameGenderBirthdayActivity.class);
        finish();
        startActivity(intent);
    }

    private void sendUserInformation(String name, String kidGender, String number, String birth){

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        Call<Response> call = apiInterface.register(name, kidGender, number, birth);
        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if(response.isSuccessful()){
                    Log.i("RETROFIT", "successful");
                    Log.i("RETROFIT", String.valueOf(response.body().getName()));
                    Log.i("RETROFIT", String.valueOf(response.body().getToken()));

                    String Token = String.valueOf(response.body().getToken());
                    final String MyShPref = "MyPrefers",FirstRun = "run",
                            authenticationToken = "Token";
                    SharedPreferences SharedPreferences;
                    SharedPreferences = getSharedPreferences(MyShPref, Context.MODE_PRIVATE);
                    SharedPreferences.Editor sEdit = SharedPreferences.edit();
                    sEdit.putString(FirstRun, String.valueOf("run"));
                    sEdit.putString(authenticationToken, String.valueOf(Token));
                    Globals.setToken(Token);
                    sEdit.apply();

                    Intent intent = new Intent(SignUpNameGenderBirthdayActivity.this, MainActivity.class);
                    startActivity(intent);

                }else{
                    Log.i("RETROFIT", "not response");
                }

            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

            }
        });
    }
}