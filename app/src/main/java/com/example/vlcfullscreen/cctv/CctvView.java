package com.example.vlcfullscreen.cctv;

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
    private CctvData cctvData;
    private String title;
    private String url;
    private boolean isCctiv;

    private TextView title_textView;

    private CctvPlayer cctvPlayer;
    private SurfaceView surfaceView;


    public CctvView(Context context,CctvData cctvData) {
        super(context);
        this.context = context;
        this.cctvData = cctvData;
        this.isCctiv = cctvData.isCctv;
        this.url = cctvData.url;
        this.title = cctvData.cctvTitle;
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

        if(!isCctiv){
            title_textView = new TextView(context);
            title_textView.setText("선택된 CCTV가 없습니다.");
            title_textView.setTextSize(20);
            title_textView.setTextColor(Color.WHITE);
            title_textView.setGravity(Gravity.CENTER);
            title_textView.setBackgroundColor(Color.TRANSPARENT);
            title_textView.setVisibility(VISIBLE);
            addView(title_textView);
            return;
        }


        surfaceView = new SurfaceView(context);
        addView(surfaceView);
        surfaceView.setZOrderOnTop(false);
        setVisibility(VISIBLE);
        title_textView = new TextView(context);
        title_textView.setText(this.title);
        title_textView.setTextSize(20);
        title_textView.setTextColor(Color.RED);
        title_textView.setGravity(Gravity.CENTER_HORIZONTAL);
        title_textView.setBackgroundColor(Color.TRANSPARENT);
        title_textView.setVisibility(VISIBLE);
        addView(title_textView);
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

    public String getCctvId() {return title;    }

    public CctvData getCctvData(){
        return cctvData;
    }
}

