package com.example.myapplication.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

public class Registration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        try {
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.addPlugin(new AWSDataStorePlugin());
            // Add this line, to include the Auth plugin.
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.configure(getApplicationContext());
            Log.i("Tutorial", "Initialized Amplify");
        } catch (AmplifyException failure) {
            Log.e("Tutorial", "Could not initialize Amplify", failure);
        }



        Button signUp = findViewById(R.id.signUpButton);

        Button signInButton = findViewById(R.id.signInButtonInSignPage);

        EditText userName= findViewById(R.id.userNameRegester);
        EditText passWord=findViewById(R.id.editTextTextPassword);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            signInMethod(userName.getText().toString() , passWord.getText().toString());
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    public void signInMethod(String userName, String passWord){
        Intent intent= new Intent(Registration.this, MainActivity.class);//
        Amplify.Auth.signIn(
                userName,
                passWord,
                result -> {
                    if (result.isSignInComplete()){
                       startActivity(intent);
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Incorrect username or password!" , Toast.LENGTH_LONG).show();
                        System.out.println("ERROR!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    }
                },
                error -> {
                    Log.e("AuthQuickstart", error.toString());
                }
        );
    }


}