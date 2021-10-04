package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    // class variables
    SharedPreferences preferences;
    String email = "emailKey";
    TextView emailText;
    public static final String ACTIVITY_NAME = "PROFILE_ACTIVITY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // loads activity_login page
        emailText = (TextView) findViewById(R.id.editText1_L03);
        preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
       // preferences = getSharedPreferences("mypref",
        //        Context.MODE_PRIVATE);
        if (preferences.contains(this.email)) {
            emailText.setText(preferences.getString(this.email, ""));
        }
        // retrieves saved preference (email text input) when app is launched
        Button button = (Button) findViewById(R.id.button_L03);
        button.setOnClickListener(v -> {
            Intent toProfile = new Intent(LoginActivity.this, ProfileActivity.class);
            LoginActivity.this.startActivity(toProfile);
        });
        // ensures app moves to activity_profile.xml/ProfileActivity.java when button is clicked
    }

    protected void onPause() {
        super.onPause();
        String e = emailText.getText().toString();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(this.email, e);
        editor.commit();
        // edits and commits the email text information when application is paused
    }
}