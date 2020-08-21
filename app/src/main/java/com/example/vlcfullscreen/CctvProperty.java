package com.example.vlcfullscreen;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class CctvProperty {
    private boolean isFullScreen;
    private boolean isSos;

    private static class Holder{
        private static final CctvProperty instance = new CctvProperty();
    }

    public static CctvProperty getInstance() {
        return Holder.instance;
    }

    void update(JSONObject properties){
        JSONArray capsArray = properties == null? null :properties.optJSONArray("caps");
        List<String> caps = new LinkedList<>();

        if (capsArray != null) {
            for (int i = 0; i < capsArray.length(); i++) {
                caps.add(capsArray.optString(i));
            }

        }
    }
}
