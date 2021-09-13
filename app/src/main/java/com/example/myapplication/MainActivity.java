package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.GeneratedTaskModel;
import com.example.myapplication.Authentication.Registration;
import com.example.myapplication.Tasks.AddTask;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final List<GeneratedTaskModel> taskList = new ArrayList<>();
    private RecyclerView recyclerView;
    String teamNameFromSetting = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

//****************************************************** Start SignUp ********************************************************

        //sign up activity(replace amail, userName, password with the ones that the user entered)
//        AuthSignUpOptions options = AuthSignUpOptions.builder()
//                .userAttribute(AuthUserAttributeKey.email(), "ibrahimalhamshari742@gmail.com")
//                .build();
//        Amplify.Auth.signUp("Ibrahim", "12345678", options,
//                result -> Log.i("AuthQuickStart", "Result: " + result.toString()),
//                error -> Log.e("AuthQuickStart", "Sign up failed", error)
//        );

        //ask the user for the code that be sent to the email
//        Amplify.Auth.confirmSignUp(
//                "Ibrahim",
//                "867198",
//                result -> Log.i("AuthQuickstart", result.isSignUpComplete() ? "Confirm signUp succeeded" : "Confirm sign up not complete"),
//                error -> Log.e("AuthQuickstart", error.toString())
//        );

//****************************************************** End SignUp ********************************************************

//******************************************************Start Registration *******************************************************

// Implement a UI to get the username and password from the user. After the user enters the username
// and password you can start the sign in flow by calling the following method:
//        Amplify.Auth.signIn(
//                "Ibrahim",
//                "12345678",
//                result -> Log.i("AuthQuickstart", result.isSignInComplete() ? "Sign in succeeded" : "Sign in not complete"),
//                error -> Log.e("AuthQuickstart", error.toString())
//        );

//*********************************************************End Registration *******************************************************

//*********************************************************Start Registration WithWebUI *******************************************************


//        Amplify.Auth.signInWithWebUI(
//                this,
//                result -> Log.i("AuthQuickStart", result.toString()),
//                error -> Log.e("AuthQuickStart", error.toString())
//        );

//*********************************************************Start Registration WithWebUI *******************************************************

//*********************************************************Start LogOut *******************************************************

        Button button= findViewById(R.id.logOutButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Amplify.Auth.signOut(
                        () -> Log.i("AuthQuickstart", "Signed out successfully"),
                        error -> Log.e("AuthQuickstart", error.toString())
                );

                Intent intent = new Intent(MainActivity.this , Registration.class);
                startActivity(intent);
            }
        });

//*********************************************************End LogOut *******************************************************


        Amplify.DataStore.observe(GeneratedTaskModel.class,
                started -> Log.i("Tutorial", "Observation began."),
                change -> Log.i("Tutorial", change.item().toString()),
                failure -> Log.e("Tutorial", "Observation failed.", failure),
                () -> Log.i("Tutorial", "Observation complete.")
        );


        setAdapter();

        Button addTaskButton = findViewById(R.id.addTaskButton);
        Button settingsSaveButton = findViewById(R.id.homePageSettingsButton);

        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent goToTaskDetail = new Intent(MainActivity.this , AddTask.class);
                startActivity(goToTaskDetail);
            }
        });

        settingsSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Settings.class);
                startActivity(intent);

            }
        });

    }

    private void setAdapter(){
        recyclerView= findViewById(R.id.mainPageRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        Handler handler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message message) {  //It will notify the recyclerview that there are a data changed
                recyclerView.getAdapter().notifyDataSetChanged();
                return false;
            }
        });

        Amplify.API.query(
                ModelQuery.list(com.amplifyframework.datastore.generated.model.GeneratedTaskModel.class),
                response -> {

                    for (GeneratedTaskModel todo : response.getData()) {
                        Log.i("MyAmplifyApp", todo.getTaskName());
                        System.out.println("===================================================+: " + teamNameFromSetting.getClass().getSimpleName());
                        if(todo.getTeam() != null){
                            if(teamNameFromSetting.equals(todo.getTeam().getName())){
                                taskList.add(todo);
                                System.out.println(" ======================================+: " + teamNameFromSetting +" 00 "+ todo.getTeam().getName());
                            }
                        }

                    }
                    handler.sendEmptyMessage(1); // send to the handler
                },
                error -> Log.e("MyAmplifyApp", "Query failure", error)
        );

        Adapter adapter= new Adapter(taskList);
        recyclerView.setAdapter(adapter);
    }

    public void getUserAndTeam(){

        TextView userNameText = findViewById(R.id.userNameHomePage);
        TextView teamNameText = findViewById(R.id.textForTeamInMainPage);

        String welcomeMessage = "’s tasks";
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        String userName = sharedPreferences.getString("userName" , "User" );
        String teamName= sharedPreferences.getString("teamName" , "Team");

        teamNameFromSetting=teamName;
        System.out.println("===================================================: " + teamNameFromSetting);
        userNameText.setText(userName + welcomeMessage);
        teamNameText.setText(teamName+ "'s Tasks");
    }


    @Override
    protected void onStart() {
        super.onStart();
        getUserAndTeam();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AWSCognitoAuthPlugin.WEB_UI_SIGN_IN_ACTIVITY_CODE) {
            Amplify.Auth.handleWebUISignInResponse(data);
        }
    }
}