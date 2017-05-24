package com.anandroid.dineshwedssri;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Anand  on 5/20/2017.
 */

public class Splash extends AppCompatActivity {

    private static final String CURRENT_TAG = Splash.class.getSimpleName();
    String dataString = "";

    //ImageView bg_logo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // user is not logged in redirect him to Login Activity
        Intent i = new Intent(Splash.this, MainActivity.class);

        // Closing all the Activities from stack
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        startActivity(i);
        finish();
    }
}
