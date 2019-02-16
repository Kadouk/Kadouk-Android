package com.kadouk.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import com.kadouk.app.model.RegisterResponse;
import com.kadouk.app.webService.APIClient;
import com.kadouk.app.webService.APIInterface;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import retrofit2.Call;
import retrofit2.Callback;
// 6
// in avalin activity e hast ke bad az splash baz mishe(dar surate ke user sabtenam nakarde bashe)
// bad az inke ino kamel check kardi SignUpEnterCodeActivity ro baz kon

public class SignUpActivity extends AppCompatActivity {

    // EditTextMaxLength tedad char hayi ke mikhaym type shode bashe, chon shomarast 11 ta gozashtim
    // zire 11 ta fab fa@l nemishe
    Short EditTextMaxLength = 11;
    FloatingActionButton fab;
    EditText EditTextNumber;
    Intent intent;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_enter_number);

        fab = findViewById(R.id.signup_number_fab);
        fab.setEnabled(false);
        fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorTertiary)));

        EditTextNumber = findViewById(R.id.signup_number_edt);

        // taghirate char haye tuye EditText ro tashkhis mide
        // harbar ke text e EditText taghir mikone check mikonim chandta char type shode.
        EditTextNumber.addTextChangedListener(mTextEditorWatcher);
    }

    @SuppressLint("WrongViewCast")
    public void sendNumber(View view) {

        if (EditTextNumber.getText().length() < EditTextMaxLength) {
            EditTextNumber.setError("There must be 11 numbers");
        } else {
            Log.i("number", String.valueOf(EditTextNumber.getText()));
            String number = String.valueOf(EditTextNumber.getText());
            Globals.setNumber(number);
            sendPhoneNumber(number);
        }
    }

    // ersale number be server,
    private void sendPhoneNumber(String Number){

        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);
        // call hamun darkhastie ke tu APIInterfac ham tarif shode
        Call<RegisterResponse> call =  apiInterface.sendPhoneNumber(Number);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, retrofit2.Response<RegisterResponse> response){
                if(response.isSuccessful()) {
                    if (String.valueOf(response.body().getStatus()).equals("200") || String.valueOf(response.body().getStatus()).equals("100")){
                        Log.i("LOGIN", String.valueOf(response.body().getStatus()));
                        // vaghti ke server data ro gereft  SignUpEnterCodeActivity ro start mikonim
                        intent = new Intent(SignUpActivity.this, SignUpEnterCodeActivity.class).
                                addFlags(intent.FLAG_ACTIVITY_NO_ANIMATION); // animation nadashtane taghire activity
                       // finish();
                        startActivity(intent);
                    }
                }else {
                    Log.i("LOGIN", "not response");
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Log.i("LOGIN", "connection problem!");
            }
        });
    }

    private final TextWatcher mTextEditorWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // inja taghirate text e editText ro motevajeh mishim,
            // ba taghire har char check mikonim ke char ha 11 ta hastan ya na
            if(s.length() == EditTextMaxLength){
                fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorSecondary)));
                fab.setEnabled(true);
            }else {
                fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorTertiary)));
                fab.setEnabled(false);
            }
        }

        public void afterTextChanged(Editable s) {
        }
    };

    // tabe marbut be font hast
    // tuye har activity e ke mikhaym font hash custom ma beshe bayad ino ezafe konim,
    // class e Font ro badan bekhun motevajeh mishi
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }
}