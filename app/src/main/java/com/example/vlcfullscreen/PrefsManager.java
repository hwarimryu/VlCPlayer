package com.example.vlcfullscreen;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PrefsManager {

//    @Inject Context context;
    private SharedPreferences mSharedPrefs;
//
//    private  SharedPreferences getPreferences(){
//        return context.getSharedPreferences("pref", Context.MODE_PRIVATE);
//    }
    @Inject
    public PrefsManager(SharedPreferences sharedPreferences){
        mSharedPrefs = sharedPreferences;
    }


    public void put(String key, String value) {
        mSharedPrefs.edit().putString(key,value).apply();
    }

    public void put(String key, int value) {
        mSharedPrefs.edit().putInt(key, value).apply();

    }

    public void put(String key, boolean value) {
        mSharedPrefs.edit().putBoolean(key, value).apply();
    }


    public String getString(String key) {
        return mSharedPrefs.getString(key, "");
    }

    public Boolean getBoolean(String key) {
        return mSharedPrefs.getBoolean(key, true);
    }

    public int getInt(String key) {
        return mSharedPrefs.getInt(key, 0);
    }

    void remove(String key) {
        mSharedPrefs.edit().remove(key).apply();
    }

    void reset() {
        mSharedPrefs.getAll().clear();
    }


//    keys
//    public static final String PREFERENCES_NAME="default";
//    public static final String NETWORK="NETWORK";
//    public static final String SCREENTYPE="SCREENTYPE";
//    public static final String USER="USER";
//
//    public static final String NETWORK_DEFAULT="172.16.28.1";
//    public static final String USER_DEFAULT="USER";
//    public static final String SCREENTYPE_DEFAULT="AUTO";//FULL, MULTI
//
//
//    private static SharedPreferences getPreferences(Context context){
//        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
//    }
//
//
//
//    public static void setNetwork(Context context, String value){
//        SharedPreferences prefs = getPreferences(context);
//        SharedPreferences.Editor editor = prefs.edit();
//        editor.putString(NETWORK,value);
//        editor.commit();
//    }
//
//
//
//    public static void setUser(Context context, String value){
//        SharedPreferences prefs = getPreferences(context);
//        SharedPreferences.Editor editor = prefs.edit();
//        editor.putString(USER,value);
//        editor.commit();
//    }
//
//    public static void setScreentype(Context context, String value){
//        SharedPreferences prefs = getPreferences(context);
//        SharedPreferences.Editor editor = prefs.edit();
//        editor.putString(SCREENTYPE,value);
//        editor.commit();
//    }
//
//    /**
//
//     * String 값 로드
//
//     * @param context
//
//     * @return
//
//     */
//
//    public static String getUser(Context context) {
//
//        SharedPreferences prefs = getPreferences(context);
//
//        String value = prefs.getString(USER,USER_DEFAULT);
//
//        return value;
//
//    }
//
//
//    /**
//
//     * String 값 로드
//
//     * @param context
//
//     * @return
//
//     */
//
//    public static String getScreentype(Context context) {
//
//        SharedPreferences prefs = getPreferences(context);
//
//        String value = prefs.getString(SCREENTYPE, SCREENTYPE_DEFAULT);
//
//        return value;
//
//    }
//
//
//    /**
//
//     * String 값 로드
//
//     * @param context
//
//     * @return
//
//     */
//
//    public static String getNetwork(Context context) {
//
//        SharedPreferences prefs = getPreferences(context);
//
//        String value = prefs.getString(NETWORK, NETWORK_DEFAULT);
//
//        return value;
//
//    }


}
