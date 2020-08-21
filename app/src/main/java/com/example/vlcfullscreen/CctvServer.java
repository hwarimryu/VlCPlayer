package com.example.vlcfullscreen;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import fi.iki.elonen.NanoHTTPD;

public class CctvServer extends NanoHTTPD {

    final static String TAG="CctvServer";
    private static int PORT = 8080;
    private CctvData[] cctvDatas = new CctvData[4];
    private Boolean isFullscreen;

    private Context context;
    private String url_full;
    private String cctvId_full;


    public CctvServer(Context context) throws IOException {
        super(PORT);
        this.context = context;
        start(NanoHTTPD.SOCKET_READ_TIMEOUT,false);

    }
    @Override
    public Response serve(IHTTPSession session) {
        String uri = session.getUri();
        Log.i(TAG,"URI: "+uri);

        if(uri.equals("/fullscreen")){
//            Method: get
            handleFullScreenReq(session.getParameters().get("url").get(0),session.getParameters().get("cctvid").get(0));
            return newFixedLengthResponse("fullscreen");
        }
        if(uri.equals("/sos")){
//            Method: post
            InputStream param = session.getInputStream();
            Scanner sc = new Scanner(param).useDelimiter("\\A");
            String result = sc.nextLine();
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(result);
                handleSosAlert(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return newFixedLengthResponse("sos");
        }
        return newFixedLengthResponse("invalid API");
    }



    public void handleFullScreenReq(String url, String cctvId){
        Log.i( TAG,"handleFullScreenReq");

        isFullscreen= true;
        url_full = url;
        cctvId_full = cctvId;

        Intent intent = new Intent(context, CctvActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        context.startActivity(intent);

    }
    public void handleSosAlert(JSONObject data) throws JSONException {
        Log.i( TAG,"handleSosAlert");

//        sos 발생한 main 비상벨
//        String sosCctvId = data.optString("cctvid");

        JSONArray cctvDataJsonArr = data.optJSONArray("devices");
        for(int i=0;i<4;i++){
            JSONObject cctvData =  cctvDataJsonArr.optJSONObject(i);
            cctvDatas[i]=new CctvData(cctvData.getString("cctvId"),cctvData.getString("url"));
        }

        isFullscreen=false;
        Intent intent = new Intent(context, CctvActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        context.startActivity(intent);

    }

    public CctvData[] getCctvDatas(){
        return  this.cctvDatas;
    }
    
    public Boolean getIsFullscreen(){
        return  this.isFullscreen;
    }

    public String getCctvId_full() {
        return cctvId_full;
    }

    public String getUrl_full() {
        return url_full;
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