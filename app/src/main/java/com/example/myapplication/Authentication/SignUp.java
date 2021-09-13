package com.example.myapplication.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.example.myapplication.R;

public class SignUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        EditText userName= findViewById(R.id.userNameInSignUpPage);
        EditText email = findViewById(R.id.emailInSignUpPage);
        EditText passWord = findViewById(R.id.passWordInSignUpPage);

        Button button= findViewById(R.id.signUpButtonInSignUpPage);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpMethod(userName.getText().toString() ,email.getText().toString() , passWord.getText().toString() );
            }
        });
    }

    public void signUpMethod(String userName , String email, String passWord){

        AuthSignUpOptions options = AuthSignUpOptions.builder()
                .userAttribute(AuthUserAttributeKey.email(), email)
                .build();
        Amplify.Auth.signUp(userName, passWord, options,
                result -> Log.i("AuthQuickStart", "Result: " + result.toString()),
                error -> Log.e("AuthQuickStart", "Sign up failed", error)
        );
    }
}