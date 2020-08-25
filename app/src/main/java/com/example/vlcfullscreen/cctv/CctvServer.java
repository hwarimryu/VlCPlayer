package com.example.vlcfullscreen.cctv;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.vlcfullscreen.LayoutManager;
import com.example.vlcfullscreen.PrefsManager;
import com.example.vlcfullscreen.activity.CctvActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import javax.inject.Inject;
import javax.inject.Singleton;

import fi.iki.elonen.NanoHTTPD;

@Singleton
public class CctvServer extends NanoHTTPD {
    @Inject PrefsManager prefsManager;
    @Inject LayoutManager layoutManager;
    @Inject Context context;
    public void set(Context context,PrefsManager prefsManager,LayoutManager layoutManager) {
        this.context = context;

        this.prefsManager = prefsManager;
        this.layoutManager = layoutManager;

    }

    private final String TAG="CctvServer";
    private static int PORT = 8080;

    private CctvData cctvData_full;

    @Inject public CctvServer() {
        super(PORT);
        try {
            start(NanoHTTPD.SOCKET_READ_TIMEOUT,false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    };
    private static class InnerInstanceClass{
        public static CctvServer instance = new CctvServer();
    }
    public static CctvServer getInstance(){
//        InnerInstanceClass.instance.context = context;
        return InnerInstanceClass.instance;
    }


    private CctvData[] cctvDatas = new CctvData[4];
//    private Boolean isFullscreen;


//    private Context context;
    private String url_full;
    private String cctvId_full;



//    public CctvServer(Context context) throws IOException {
//        super(PORT);
//        this.context = context;
//        start(NanoHTTPD.SOCKET_READ_TIMEOUT,false);
//
//    }
    @Override
    public Response serve(IHTTPSession session) {
        Log.i( TAG,"serve"+ session.getMethod());
//
//        Map<String,String> data = new HashMap<>();
//        session.getParameters();
//        try {
//            session.parseBody(data);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ResponseException e) {
//            e.printStackTrace();
//        }
//        Log.i(TAG, data.toString());

//        String deviceStr = data.get("devices");
//        Log.i(TAG, session.getParameters().get("devices"));
//        JsonParser jsonParser = new JsonParser();
//        JsonArray devices = (JsonArray) jsonParser.parse(deviceStr);
        //s
        InputStream data = session.getInputStream();

        Scanner sc = new Scanner(data).useDelimiter("\\A");

        String result = sc.nextLine();
        JSONArray cctvDataJsonArr =null;
        try {
            cctvDataJsonArr = new JSONObject(result).optJSONArray("devices");
        } catch (JSONException e) {

            e.printStackTrace();
        }


        boolean isFull = prefsManager.getString("SCREENTYPE").equals("FULL");
        boolean isAuto = prefsManager.getString("SCREENTYPE").equals("AUTO");
        Log.i("server pref",prefsManager.getString("SCREENTYPE"));

//         cctvDataJsonArr =  ;

        if((cctvDataJsonArr.length() <= 1 && isAuto) || isFull){
            //auto 설정 + data 1개 or fullscreen 설정
            layoutManager.put("ISFULL",true);
            handleFullScreen(cctvDataJsonArr.optJSONObject(0));
            return newFixedLengthResponse("fullscreen");
        }
        else{
            layoutManager.put("ISFULL",false);
            handleMultiScreen(cctvDataJsonArr);
            return newFixedLengthResponse("multiscreen");

        }
    }



    public void handleFullScreen(JSONObject cctvDataJson){
        Log.i( TAG,"handleFullScreen");

        layoutManager.put("ISFULL",true);

        if(cctvDataJson==null)
            cctvData_full = new CctvData(false);
        else{
            try {
                cctvData_full = new CctvData(cctvDataJson);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        Intent intent = new Intent(context, CctvActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        context.startActivity(intent);
    }
    public void handleMultiScreen(JSONArray datas) {
        Log.i( TAG,"handleMutiScreen");
        layoutManager.put("ISFULL",false);

        for(int i=0;i<4;i++){
            JSONObject cctvData = null;
            try {
                cctvData = datas.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(cctvData==null )  cctvDatas[i]=new CctvData(false);
            else {
                try {
                    cctvDatas[i]=new CctvData(cctvData);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        Intent intent = new Intent(context, CctvActivity.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        context.startActivity(intent);

    }

    public CctvData getCctvDataFull(){
        return this.cctvData_full;
    }
    public CctvData[] getCctvDatas(){
        return  this.cctvDatas;
    }

}



//    @Override
//    public Response serve(IHTTPSession session) {
//        if (session.getMethod() == Method.POST) {
//            try {
//                session.parseBody(new HashMap<>());
//                String requestBody = session.getQueryParameterString();
//                return newFixedLengthResponse("Request body = " + requestBody);
//            } catch (IOException | ResponseException e) {
//                // handle
//            }
//        }
//        return newFixedLengthResponse(Response.Status.NOT_FOUND, MIME_PLAINTEXT,
//                "The requested resource does not exist");
//    }