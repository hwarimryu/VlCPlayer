package com.example.vlcfullscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.LinearLayout;

public class CctvActivity extends AppCompatActivity {
    final static String TAG="CctvActivity";


    //  multiscreen 인가
    private Boolean isFullScreen = false;

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
                vm.removeView(cctvView_leftTop);
                vm.removeView(cctvView_rightTop);
                vm.removeView(cctvView_leftBottom);
                vm.removeView(cctvView_rightBottom);

            }

            setScreenLayout();

        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cctv);

        //AptUI console에서 cctv 하나 클릭한 경우 or sos event 발생
        receivedCctvData();
        setScreenLayout();
    }
    private void receivedCctvData(){
        //data 받아오는 거 만들면 지우기!
        this.cctvDatas[0]=new CctvData("놀이터1","rtsp://172.16.28.1:8552/live");
        this.cctvDatas[1]=new CctvData("놀이터 정문","rtsp://172.16.28.1:8553/live");
        this.cctvDatas[2]=new CctvData("놀이터 후문","rtsp://172.16.28.1:8554/live");
        this.cctvDatas[3]=new CctvData("놀이터2","rtsp://172.16.28.1:8555/live");


    }
    private void setScreenLayout(){
        if(isFullScreen){
            //fullscreen인 경우
            cctvView_full = new CctvView(this,url_full,cctvId_full);
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
        View decorView = getWindow().getDecorView();
        // Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        // Remember that you should never show the action bar if the
        // status bar is hidden, so hide that too if necessary.

    }

}