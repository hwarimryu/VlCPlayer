package com.example.vlcfullscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class CctvActivity extends AppCompatActivity {

//    처음엔 muㅣtiscreen
    private Boolean isFullScreen = false;
    //fullscreen인 경우에 사용
    private CctvView cctvView_full;
    private String url_full="rtsp://172.16.28.1:8553/live";

//    muㅣtiscreen인 경우에 사용
    private CctvView[] cctvViews = new CctvView[4];
    private CctvView cctvView_leftTop, cctvView_rightTop, cctvView_leftBottom, cctvView_rightBottom;
    private View.OnClickListener cctvViewClicked = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            isFullScreen=true;
            url_full = url;
            Log.i("CctvActivity","cctvView Clicked");
        }
    };

    private String url = "rtsp://172.16.28.1:8553/live";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cctv);

        if(isFullScreen){
            //fullscreen
            cctvView_full = new CctvView(this,url_full);
            addContentView(cctvView_full,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        }else{
            //multiscreen

            DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
            int width = (int) (dm.widthPixels * 0.5); // Display 사이즈의 50%
            int height = (int) (dm.heightPixels * 0.5); // Display 사이즈의 50%

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width,height);

            cctvView_leftTop = new CctvView(this,url);
            addContentView(cctvView_leftTop,layoutParams);
            cctvView_leftTop.setOnClickListener(cctvViewClicked);

            cctvView_rightTop  = new CctvView(this,url);
            layoutParams.setMargins(width,0,0,0);
            addContentView(cctvView_rightTop,layoutParams);
            cctvView_rightTop.setOnClickListener(cctvViewClicked);

            cctvView_leftBottom  = new CctvView(this,url);
            layoutParams.setMargins(0,height,0,0);
            addContentView(cctvView_leftBottom,layoutParams);
            cctvView_leftBottom.setOnClickListener(cctvViewClicked);

            cctvView_rightBottom = new CctvView(this,url);
            layoutParams.setMargins(width,height,0,0);
            addContentView(cctvView_rightBottom,layoutParams);
            cctvView_rightBottom.setOnClickListener(cctvViewClicked);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        // Remember that you should never show the action bar if the
        // status bar is hidden, so hide that too if necessary.

    }

}