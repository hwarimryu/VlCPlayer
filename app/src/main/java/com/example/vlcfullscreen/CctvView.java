package com.example.vlcfullscreen;

import android.content.Context;
import android.graphics.Color;
//import android.support.annotation.Nullable;
//import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.SurfaceView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.constraintlayout.widget.ConstraintLayout;

import org.jetbrains.annotations.Nullable;

public class CctvView extends LinearLayout {

    private Context context;
    private float scaleFactor;
    private int height;

    private String cctvId;

    private String url;

    private CctvPlayer cctvPlayer;
    private SurfaceView surfaceView;

    private final int CCTV_FULLSCREEN = 0;
    private final int CCTV_LOC_TOP_LEFT = 1;
    private final int CCTV_LOC_TOP_RIGHT = 2;
    private final int CCTV_LOC_BOTTOM_LEFT = 3;
    private final int CCTV_LOC_BOTTOM_RIGHT = 4;

    public CctvView(Context context,String url) {
        super(context);
        this.context = context;
        this.url = url;
//        setLayout(location);
        setLayout();

        this.cctvId = cctvId;
    }

    public CctvView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CctvView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private void setLayout() {
        Log.i("CCTV_VIEW","setLayout");
        setOrientation(VERTICAL);
        surfaceView = new SurfaceView(context);
        addView(surfaceView);

        TextView cctvId = new TextView(context);
        cctvId.setText("cctv Id");
        cctvId.setTextSize(14);
        cctvId.setTextColor(Color.BLUE);
        cctvId.setBackgroundColor(Color.TRANSPARENT);
        cctvId.setVisibility(VISIBLE);
        cctvId.setHeight(20);
//        cctvId.setGravity(Gravity.);
        addView(cctvId);
        play();
    }


    public void play() throws IllegalStateException {

        if (cctvPlayer != null) {
            Log.i("CCTV VIEW","Already another CCTV is playing");

            cctvPlayer.stop();
        }


//        String videoSrc ="rtsp://172.16.28.1:8553/live";

        cctvPlayer = new CctvPlayer(surfaceView, url,context);
        cctvPlayer.start();
    }

    public void stop() {
        if (cctvPlayer == null) return;

        cctvPlayer.stop();
        cctvPlayer = null;

//        cctvCallback.cctvViewStopped(cctvDevice);

        setVisibility(INVISIBLE);
    }

    public void setOnClickListener() {

    }
}

