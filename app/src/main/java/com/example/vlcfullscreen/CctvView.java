package com.example.vlcfullscreen;

import android.content.Context;
import android.graphics.Color;

import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.SurfaceView;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.jetbrains.annotations.Nullable;

public class CctvView extends FrameLayout {
    final static String TAG="CctvView";

    private Context context;

    private String cctvId;

    private TextView cctvID;
    private String url;

    private CctvPlayer cctvPlayer;
    private SurfaceView surfaceView;


    public CctvView(Context context, String url,String cctvId) {
        super(context);
        this.context = context;
        this.url = url;
        this.cctvId = cctvId;
        setLayout();
    }

    public CctvView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CctvView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void setLayout() {
        Log.i(TAG,"setLayout");

        surfaceView = new SurfaceView(context);

        addView(surfaceView);
        surfaceView.setZOrderOnTop(false);
        setVisibility(VISIBLE);

        cctvID = new TextView(context);
        cctvID.setText(this.cctvId);
        cctvID.setTextSize(20);
        cctvID.setTextColor(Color.RED);
        cctvID.setGravity(Gravity.CENTER_HORIZONTAL);
        cctvID.setBackgroundColor(Color.TRANSPARENT);
        cctvID.setVisibility(VISIBLE);

        addView(cctvID);
        play();
    }


    public void play() {

        if (cctvPlayer != null) {
            Log.i(TAG,"Already another CCTV is playing");
            cctvPlayer.stop();
        }

        cctvPlayer = new CctvPlayer(surfaceView, url,context);
        cctvPlayer.start();
        Log.e(TAG,"play");


    }

    public void stop() {
        if (cctvPlayer == null) return;

        cctvPlayer.stop();
        cctvPlayer = null;
        setVisibility(INVISIBLE);
    }


    @Override
    protected void onDetachedFromWindow() {
        //레이아웃 remove 할 때 스트리밍 하던 거 꺼줘야함.
        Log.i(TAG,"onDetachedFromWindow");
        super.onDetachedFromWindow();
        stop();
    }

    public String getUrl() {
        return url;
    }

    public String getCctvId() {return cctvId;    }
}

