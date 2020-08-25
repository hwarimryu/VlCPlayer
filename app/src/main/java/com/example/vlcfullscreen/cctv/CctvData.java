package com.example.vlcfullscreen.cctv;


import org.json.JSONException;
import org.json.JSONObject;

public class CctvData {

    String cctvTitle, url,id, pwd;
    boolean isCctv;

    public CctvData(String cctvTitle, String url,String id, String pwd, boolean isCctv) {
        this.cctvTitle = cctvTitle;
        this.url = url;
        this.id = id;
        this.pwd = pwd;
        this.isCctv = isCctv;
    }

    public CctvData(boolean isCctv){
        this.isCctv = isCctv;
    }

    public CctvData(JSONObject data) throws JSONException {
        this.cctvTitle = data.getString("cctvTitle");
        this.url = data.getString("url");
        this.id = data.getString("id");
        this.pwd = data.getString("pwd");
        this.isCctv = true;
    }

//    public get
}
