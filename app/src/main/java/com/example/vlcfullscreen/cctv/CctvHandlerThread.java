package com.example.vlcfullscreen.cctv;

import android.os.Handler;
import android.os.HandlerThread;

public class CctvHandlerThread {
    final static String TAG="CctvHandlerThread";

    private HandlerThread handlerThread;
    private Handler handler;

    private static class Holder {
        private static final CctvHandlerThread instance = new CctvHandlerThread();
    }

    public static CctvHandlerThread getInstance() {
        return Holder.instance;
    }

    private CctvHandlerThread() {
        handlerThread = new HandlerThread("CctvPlayer");
        handlerThread.start();
        handler = new Handler(handlerThread.getLooper());
    }

    public Handler getHandler() {
        return handler;
    }
}
