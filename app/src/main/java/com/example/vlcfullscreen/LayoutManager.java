package com.example.vlcfullscreen;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class LayoutManager {

    private SharedPreferences layoutPrefs;
//    //    keys
//    public static final String PREFERENCES_NAME="default";
//    public static final String ISFULLSCREEN="ISFULLSCREEN";
//    public static final String ISCCTV="ISCCTV";

    @Inject
    public LayoutManager(SharedPreferences layoutPrefs) {
        this.layoutPrefs = layoutPrefs;
    }


    public void put(String key, String value) {
        layoutPrefs.edit().putString(key, value).apply();
    }

    public void put(String key, int value) {
        layoutPrefs.edit().putInt(key, value).apply();
    }

    public void put(String key, boolean value) {
        layoutPrefs.edit().putBoolean(key, value).apply();
    }

    public String getString(String key) {
        return layoutPrefs.getString(key, "");
    }

    public Boolean getBoolean(String key) {
        return layoutPrefs.getBoolean(key, true);
    }

    public int getInt(String key) {
        return layoutPrefs.getInt(key, 0);
    }

    void remove(String key) {
        layoutPrefs.edit().remove(key).apply();
    }

    void reset() {
        layoutPrefs.getAll().clear();
    }


}

