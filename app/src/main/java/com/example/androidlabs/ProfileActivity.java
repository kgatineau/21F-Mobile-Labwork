package com.example.androidlabs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;

public class ProfileActivity extends AppCompatActivity {
    // class variables
    static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final String ACTIVITY_NAME = "PROFILE_ACTIVITY";
    ImageButton mImageButton;
    Button buttonL04;
    Button buttonL06;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mImageButton = findViewById(R.id.imageButton_L03);
        mImageButton.setOnClickListener(v -> dispatchTakePictureIntent());
        /* calls the dispatchTakePictureIntent() function when the ImageButton is clicked
        which contains the Intent statement that opens the Android camera */

        buttonL04 = findViewById(R.id.button1_L04);
        buttonL04.setOnClickListener(v -> {
            Intent toProfile = new Intent(ProfileActivity.this, ChatRoomActivity.class);
            ProfileActivity.this.startActivity(toProfile);});

        buttonL06 = findViewById(R.id.button1_L06);
        buttonL06.setOnClickListener(v -> {
            Intent toProfile = new Intent(ProfileActivity.this, WeatherForecast.class);
            ProfileActivity.this.startActivity(toProfile);});

        // Log.e(ACTIVITY_NAME, "In function: " + "onCreate()");
    }

    protected void onStart() {
        super.onStart();
        // Log.e(ACTIVITY_NAME, "In function: " + "onStart()");
    }

    protected void onResume() {
        super.onResume();
        // Log.e(ACTIVITY_NAME, "In function: " + "onResume()");
    }

    protected void onPause() {
        super.onPause();
        // Log.e(ACTIVITY_NAME, "In function: " + "onPause()");
    }

    protected void onDestroy() {
        super.onDestroy();
        // Log.e(ACTIVITY_NAME, "In function: " + "onDestroy()");
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
        // called in the OnClickListener() interface
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageButton.setImageBitmap(imageBitmap);
        }
        // saves the image taken with the android camera and replaces the ImageButton default image
        // with the photo
    }

}