package com.example.myapplication.Authentication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import java.util.concurrent.atomic.AtomicReference;

public class Registration extends AppCompatActivity {

    final String[] test = {null};

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

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Registration.this , SignUp.class);
                startActivity(intent);
            }
        });

        EditText userName= findViewById(R.id.userNameRegester);
        EditText passWord=findViewById(R.id.editTextTextPassword);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    signInMethod(userName.getText().toString() , passWord.getText().toString());

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (test[0] == "error") {
                    Toast.makeText(getApplicationContext() ,"ERROR, Incorrect Password or Username!" ,Toast.LENGTH_LONG );
                }else if(test[0]== "success"){
                    Toast.makeText(getApplicationContext() ,"Happy Register" ,Toast.LENGTH_LONG );
                }
                    Toast.makeText(getApplicationContext() ,"null Register" ,Toast.LENGTH_LONG );
            }
        });


    }
    public void signInMethod(String userName, String passWord){
        Intent intent= new Intent(Registration.this, MainActivity.class);

        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(Registration.this);
        SharedPreferences.Editor sharedPreferenceEditor= sharedPreferences.edit();

        sharedPreferenceEditor.putString("userRegester" , userName);
        sharedPreferenceEditor.apply();

        Amplify.Auth.signIn(
                userName,
                passWord,
                result -> {
                    if (result.isSignInComplete()){
                        test[0] ="success";
                        System.out.println("*************************************");
                       startActivity(intent);
                    }
                    else{
                        System.out.println("*************************************");
                        test[0] ="faild";
                        System.out.println("ERROR!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    }
                },
                error -> {
                    test[0] ="error";
                    System.out.println("*************************************");
                    Log.e("AuthQuickstart", error.toString());
                }
        );
    }

}