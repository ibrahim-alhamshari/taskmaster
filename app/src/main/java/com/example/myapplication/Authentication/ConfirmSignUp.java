package com.example.myapplication.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.amplifyframework.core.Amplify;
import com.example.myapplication.R;

import java.util.concurrent.atomic.AtomicReference;

public class ConfirmSignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_sign_up);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        EditText userName= findViewById(R.id.userNameConfirmPage);
        EditText code = findViewById(R.id.codeConfirmPage);

        Button button= findViewById(R.id.buttonConfirmPage);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           confirmTheCode(userName.getText().toString() , code.getText().toString());
            }
        });
    }

    public void confirmTheCode(String userName , String code){

        Amplify.Auth.confirmSignUp(
                userName,
                code,
                result -> {
                    Log.i("AuthQuickstart", result.isSignUpComplete() ? "Confirm signUp succeeded" : "Confirm sign up not complete");
                },
                error -> Log.e("AuthQuickstart", error.toString())
        );


    }
}