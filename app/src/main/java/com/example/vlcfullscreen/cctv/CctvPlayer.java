package com.example.vlcfullscreen.cctv;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;

import org.videolan.libvlc.IVLCVout;
import org.videolan.libvlc.IVideoPlayer;
import org.videolan.libvlc.LibVLC;
import org.videolan.libvlc.Media;
import org.videolan.libvlc.MediaPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CctvPlayer implements IVLCVout.Callback, LibVLC.HardwareAccelerationError, IVideoPlayer {
    final static String TAG="CctvPlayer";

    private int mVideoWidth;
    private int mVideoHeight;
    private LibVLC libvlc;
    private MediaPlayer mediaPlayer;
    private Context context;

    private String url;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;

    public CctvPlayer(SurfaceView surfaceView, String url, Context context) {

        libvlc = new LibVLC(CctvPlayer.this.getRtspOptions(CctvPlayer.this.parseUrl(url)));
        this.surfaceView = surfaceView;
        this.surfaceHolder = surfaceView.getHolder();
        this.url = url;
//        this.context =context;
        System.out.println(url);
    }

    public void start() {

        CctvHandlerThread.getInstance().getHandler().post(new Runnable() {
            @Override
            public void run() {
                surfaceHolder = surfaceView.getHolder();
                Log.i(TAG, "run");


                try {
                    Map<String, String> urlInfo = CctvPlayer.this.parseUrl(url);
                    Log.i(TAG, CctvPlayer.this.getRtspOptions(CctvPlayer.this.parseUrl(url)).toString());



                    surfaceHolder.setKeepScreenOn(true);

                    mediaPlayer = new MediaPlayer(libvlc);

                    final IVLCVout vout = mediaPlayer.getVLCVout();
                    vout.setVideoView(surfaceView);
                    vout.addCallback(CctvPlayer.this);
                    vout.attachViews();

                    mediaPlayer.setMedia(new Media(libvlc, Uri.parse(CctvPlayer.this.getRtspUrl(urlInfo))));
                    mediaPlayer.play();

                } catch (Exception e) {
                    Log.e(TAG, "Error creating player: " + e.getMessage());
                }
            }
        });
    }

    public void stop() {
        CctvHandlerThread.getInstance().getHandler().post(new Runnable() {
            @Override
            public void run() {
                if (libvlc == null)
                    return;

                if (mediaPlayer != null) {
                    mediaPlayer.stop();

                    final IVLCVout vout = mediaPlayer.getVLCVout();
                    vout.detachViews();
                    vout.removeCallback(CctvPlayer.this);
                }

                surfaceHolder = null;
                libvlc.release();
                libvlc = null;

//                mVideoWidth = 0;
//                mVideoHeight = 0;
            }
        });
    }

    private void setSize(int width, int height) {
        Log.i(TAG, "set size");

        mVideoWidth = width;
        mVideoHeight = height;
        if (mVideoWidth * mVideoHeight <= 1)
            return;

        if (surfaceHolder == null || surfaceView == null){
            Log.i(TAG, "set size null");
            return;
        }

        int w = mVideoWidth;
        int h = mVideoHeight;

        surfaceHolder.setFixedSize(w, h);

        ViewGroup.LayoutParams lp = surfaceView.getLayoutParams();
        lp.width = w;
        lp.height = h;
        surfaceView.setLayoutParams(lp);
        surfaceView.invalidate();
    }

    @Override
    public void onNewLayout(IVLCVout vlcVout, int width, int height, int visibleWidth, int visibleHeight, int sarNum, int sarDen) {
        Log.d(TAG, "on new layout");
        if (width * height == 0) return;

        setSize(mVideoWidth, mVideoHeight);

    }

    @Override
    public void onSurfacesCreated(IVLCVout vlcVout) {
        Log.d(TAG, "cctv player surfaces created");

    }

    @Override
    public void onSurfacesDestroyed(IVLCVout vlcVout) {
        Log.d(TAG, "cctv player surfaces destroyed");
        stop();
    }

    @Override
    public void setSurfaceLayout(int i, int i1, int i2, int i3, int i4, int i5) {

    }

    @Override
    public int configureSurface(Surface surface, int i, int i1, int i2) {
        return 0;
    }

    @Override
    public void eventHardwareAccelerationError() {
        Log.e(TAG, "Error with hardware acceleration");
    }

    private Map<String, String> parseUrl(String url) {
        Map<String, String> urlInfo = new HashMap<>();

        String fullPath = url.substring(url.indexOf("://") + 3);

        if (fullPath.contains("@")) {
            String userInfo = fullPath.split("@")[0];
            String path = fullPath.split("@")[1];

            if (userInfo.contains(":")) {
                urlInfo.put("id", userInfo.split(":")[0]);
                urlInfo.put("pwd", userInfo.split(":")[1]);
            } else {
                urlInfo.put("id", userInfo);
            }

            urlInfo.put("path", "rtsp://" + path);
        } else {
            urlInfo.put("path", "rtsp://" + fullPath);
        }

        return urlInfo;
    }

    private ArrayList<String> getRtspOptions(Map<String, String> urlInfo) {
        ArrayList<String> args = new ArrayList<>();
        args.add("--aout=opensles");
        args.add("-vvv");

        if (urlInfo.containsKey("id")) args.add("--rtsp-user=" + urlInfo.get("id"));
        if (urlInfo.containsKey("pwd")) args.add("--rtsp-pwd=" + urlInfo.get("pwd"));

        return args;
    }

    private String getRtspUrl(Map<String, String> urlInfo) {
        return urlInfo.get("path");
    }
}
