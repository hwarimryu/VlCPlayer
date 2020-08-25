package com.example.vlcfullscreen.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.vlcfullscreen.cctv.CctvServer;
import com.example.vlcfullscreen.LayoutManager;
import com.example.vlcfullscreen.PrefsManager;
import com.example.vlcfullscreen.R;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class MainActivity extends AppCompatActivity{
    @Inject PrefsManager prefsManager;
    @Inject LayoutManager layoutManager;

    @Inject Context context;

    private DrawerLayout mDrawerLayout;
    private TextView txt_preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);

        super.onCreate(savedInstanceState);

        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
//         Remember that you should never show the action bar if the
//         status bar is hidden, so
//        CctvServer.getInstance(this);
//        LayoutManager.

        setContentView(R.layout.activity_main);

        prefsManager.put("SCREENTYPE","AUTO");
        CctvServer.getInstance().set(context,prefsManager,layoutManager);

//        mDrawerLayout = (DrawerLayout) findViewById(R.id.dr);

//        Toolbar myToolBar = (Toolbar) findViewById(R.id.my_toolbar);
        Intent settingIntent = new Intent(this,SettingActivity.class);
        startActivity(settingIntent);

    }


}