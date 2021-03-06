package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
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

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.pinpoint.PinpointConfiguration;
import com.amazonaws.mobileconnectors.pinpoint.PinpointManager;
import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.generated.model.GeneratedTaskModel;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;
import com.example.myapplication.Authentication.Registration;
import com.example.myapplication.Tasks.AddTask;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    private static PinpointManager pinpointManager;

    public static PinpointManager getPinpointManager(final Context applicationContext) {
        if (pinpointManager == null) {
            final AWSConfiguration awsConfig = new AWSConfiguration(applicationContext);
            AWSMobileClient.getInstance().initialize(applicationContext, awsConfig, new Callback<UserStateDetails>() {
                @Override
                public void onResult(UserStateDetails userStateDetails) {
                    Log.i("INIT", String.valueOf(userStateDetails.getUserState()));
                }

                @Override
                public void onError(Exception e) {
                    Log.e("INIT", "Initialization error.", e);
                }
            });

            PinpointConfiguration pinpointConfig = new PinpointConfiguration(
                    applicationContext,
                    AWSMobileClient.getInstance(),
                    awsConfig);

            pinpointManager = new PinpointManager(pinpointConfig);

            FirebaseMessaging.getInstance().getToken()
                    .addOnCompleteListener(new OnCompleteListener<String>() {
                        @Override
                        public void onComplete(@NonNull com.google.android.gms.tasks.Task<String> task) {
                            if (!task.isSuccessful()) {
                                Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                                return;
                            }
                            final String token = task.getResult();
                            Log.d(TAG, "Registering push notifications token: " + token);
                            pinpointManager.getNotificationClient().registerDeviceToken(token);
                        }
                    });
        }
        return pinpointManager;
    }


    private final List<GeneratedTaskModel> taskList = new ArrayList<>();
    private RecyclerView recyclerView;
    String teamNameFromSetting = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//*********************************************************Start LogOut *******************************************************
        Button button= findViewById(R.id.logOutButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Amplify.Auth.signOut(
                        () ->{
                            Log.i("AuthQuickstart", "Signed out successfully");
                        } ,
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


        renderTheTasks();

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



    private void renderTheTasks(){
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
                        if(todo.getTeam() != null){
                            if(teamNameFromSetting.equals(todo.getTeam().getName())){
                                taskList.add(todo);
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
        TextView userNameFromRegester= findViewById(R.id.textView20);

        String welcomeMessage = "???s tasks";
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        String userName = sharedPreferences.getString("userName" , "User" );
        String teamName= sharedPreferences.getString("teamName" , "Team");
        String userNameFromRegesterPage = sharedPreferences.getString("userRegester" , "UserName");

        teamNameFromSetting=teamName;
        System.out.println("===================================================: " + teamNameFromSetting);
        userNameText.setText(userName + welcomeMessage);
        teamNameText.setText(teamName+ "'s Tasks");
        userNameFromRegester.setText(userNameFromRegesterPage);
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