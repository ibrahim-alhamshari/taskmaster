package com.example.myapplication.Authentication;

import androidx.annotation.WorkerThread;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;
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
            Amplify.addPlugin(new AWSS3StoragePlugin());

            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.addPlugin(new AWSDataStorePlugin());
            // Add this line, to include the Auth plugin.
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.configure(getApplicationContext());

            Log.i("Tutorial", "Initialized Amplify");
        } catch (AmplifyException failure) {
            Log.e("Tutorial", "Could not initialize Amplify", failure);
        }

        Intent intent = new Intent(Registration.this , MainActivity.class);
        Amplify.Auth.fetchAuthSession(
                result ->{
                    Log.i("AmplifyQuickstart", "successfully signIn");
//                    try {
//                        Thread.sleep(2000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    startActivity(intent);
                } ,
                error -> Log.e("AmplifyQuickstart", "You need to signIn")
        );

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
            }
        });
    }
    public void signInMethod(String userName, String passWord){
        Intent intent= new Intent(Registration.this, MainActivity.class);

        System.out.println(userName);
        System.out.println(passWord+ "========================================");

        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(Registration.this);
        SharedPreferences.Editor sharedPreferenceEditor= sharedPreferences.edit();

        sharedPreferenceEditor.putString("userRegester" , userName);
        sharedPreferenceEditor.apply();


        Amplify.Auth.signIn(
                userName,
                passWord,
                result -> {
                    if (result.isSignInComplete()){
                        handler2();
                       startActivity(intent);
                    }
                    else{
                        System.out.println("ERROR!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    }
                },
                error -> {
                 handler();
                    Log.e("AuthQuickstart", error.toString());
                }
        );


    }

    public void  handler(){
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext() , "Incorrect username or password!" , Toast.LENGTH_LONG).show();
            }
        });
    }

    public void  handler2(){
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext() , "Success!" , Toast.LENGTH_LONG).show();
            }
        });
    }
}