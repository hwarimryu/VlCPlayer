package com.example.vlcfullscreen;
import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.LinearLayout;

import fi.iki.elonen.NanoHTTPD;

public class CctvActivity extends AppCompatActivity implements NanoHTTPD.AsyncRunner {
    final static String TAG="CctvActivity";
    final static int FULLSCREEN=1;
    final static int SOS=2;



    //  multiscreen 인가
    private Boolean isFullScreen = true;

    //fullscreen인 경우에 사용
    private CctvView cctvView_full;
    private String url_full="rtsp://172.16.28.1:8554/live";
    private String cctvId_full="";

//    multiscreen인 경우에 사용
    private CctvData[] cctvDatas = new CctvData[4];
    private CctvView cctvView_leftTop, cctvView_rightTop, cctvView_leftBottom, cctvView_rightBottom;

    private View.OnClickListener cctvViewClicked = new View.OnClickListener() {
//    multiscreen에서 화면 한 개 클릭하면 그 화면 fullscreen으로
//    fullscreen에서 화면 클릭하면 다시 multiscreen으로 변경

        @Override
        public void onClick(View view) {
            Log.i(TAG,"cctvView Clicked");
            ViewManager vm = (ViewManager) view.getParent();
            if(isFullScreen) {
                isFullScreen=false;

                vm.removeView(cctvView_full);
            }else{
                isFullScreen=true;
                url_full = ((CctvView)view).getUrl();
                cctvId_full = ((CctvView)view).getCctvId();
                Log.i(TAG,url_full);

                vm.removeView(cctvView_leftTop);
                vm.removeView(cctvView_rightTop);
                vm.removeView(cctvView_leftBottom);
                vm.removeView(cctvView_rightBottom);
            }
            setScreenLayout();
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
    }

    private ConstraintLayout cctvLayout ;

    static CctvServer cctvServer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cctv);
        cctvLayout= findViewById( R.id.cctv_layout);
        if(cctvServer==null){
        try {
            cctvServer = new CctvServer(this);
        } catch (IOException e) {
            e.printStackTrace();
        }}
//      새로 생성될 때만 받은 data를 가져온다.
        receivedCctvData();
    }
    private void receivedCctvData() {
        isFullScreen = cctvServer.getIsFullscreen();
//        받은 data가 없으면 제일 처음 로딩 된 경우임
        if(isFullScreen==null)  initCctvData();
        else {
            cctvDatas = cctvServer.getCctvDatas();
            cctvId_full = cctvServer.getCctvId_full();
            url_full= cctvServer.getUrl_full();
        }
    }

    private void initCctvData() {
//        처음 앱 시작할 때 일단 전체화면으로 시작
        isFullScreen=false;
        cctvId_full = "onCreate";
        url_full= "rtsp://172.16.28.1:8554/live";
        cctvDatas[0]=new CctvData("놀이터1","rtsp://172.16.28.1:8552/live");
        cctvDatas[1]=new CctvData("놀이터 정문","rtsp://172.16.28.1:8553/live");
        cctvDatas[2]=new CctvData("놀이터 후문","rtsp://172.16.28.1:8554/live");
        cctvDatas[3]=new CctvData("놀이터2","rtsp://172.16.28.1:8555/live");
    }

    private void setScreenLayout(){


        if(isFullScreen){
            //fullscreen인 경우
            cctvView_full = new CctvView(this,url_full,cctvId_full);
//            cctvView_full.setZ
            addContentView(cctvView_full,new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
            cctvView_full.setOnClickListener(cctvViewClicked);
        }else{
            //multiscreen인 경우

//          액션 바 height
            TypedValue tv = new TypedValue();
            getTheme().resolveAttribute(R.attr.actionBarSize, tv, true);
            int actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data,getResources().getDisplayMetrics());

//          전체화면 사이즈
            DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
            int width = (int) (dm.widthPixels * 0.5); // Display 사이즈의 50%
            int height = (int) ((dm.heightPixels+actionBarHeight) * 0.5); // Display 사이즈(화면 + action bar)의 50%

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(width,height);

            cctvView_leftTop = new CctvView(this,cctvDatas[0].url,cctvDatas[0].cctvId);
            addContentView(cctvView_leftTop,layoutParams);
            cctvView_leftTop.setOnClickListener(cctvViewClicked);

            cctvView_rightTop  = new CctvView(this,cctvDatas[1].url,cctvDatas[1].cctvId);
            layoutParams.setMargins(width,0,0,0);
            addContentView(cctvView_rightTop,layoutParams);
            cctvView_rightTop.setOnClickListener(cctvViewClicked);

            cctvView_leftBottom  = new CctvView(this,cctvDatas[2].url,cctvDatas[2].cctvId);
            layoutParams.setMargins(0,height,0,0);
            addContentView(cctvView_leftBottom,layoutParams);
            cctvView_leftBottom.setOnClickListener(cctvViewClicked);

            cctvView_rightBottom = new CctvView(this,cctvDatas[3].url,cctvDatas[3].cctvId);
            layoutParams.setMargins(width,height,0,0);
            addContentView(cctvView_rightBottom,layoutParams);
            cctvView_rightBottom.setOnClickListener(cctvViewClicked);

        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        setScreenLayout();
    }


    @Override
    public void closeAll() {
        cctvServer.closeAllConnections();
    }

    @Override
    public void closed(NanoHTTPD.ClientHandler clientHandler) {

    }

    @Override
    public void exec(NanoHTTPD.ClientHandler code) {

    }
}