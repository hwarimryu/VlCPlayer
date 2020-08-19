package com.example.vlcfullscreen;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    CctvView cctvView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent cctvIntent = new Intent(this,CctvActivity.class);
        startActivity(cctvIntent);

//        Intent intent = new Intent(this, MultiscreenActivityX.class);
//        startActivity(intent);

    }
}