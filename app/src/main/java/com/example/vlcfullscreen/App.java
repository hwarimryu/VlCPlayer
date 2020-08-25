package com.example.vlcfullscreen;

import android.app.Activity;
import android.app.Application;
import android.content.Context;


//import com.example.vlcfullscreen.dagger.DaggerAppComponent;


//import com.example.vlcfullscreen.dagger.DaggerAppComponent;

//import com.example.vlcfullscreen.dagger.AppComponent;



//import com.example.vlcfullscreen.dagger.AppComponent;
import com.example.vlcfullscreen.dagger.AppModule;
import com.example.vlcfullscreen.dagger.DaggerAppComponent;
//import com.example.vlcfullscreen.dagger.DaggerAppComponent;

import javax.inject.Inject;

import dagger.Provides;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public class App extends Application implements HasActivityInjector {
    @Inject DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Inject PrefsManager prefsManager;
    @Inject LayoutManager layoutManager;
    @Inject Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        DaggerAppComponent.builder().create(this).inject(this);
//        appComponent =DaggerAppComponent.builder().appModule(new AppModule((this))).build();

        prefsManager.put("SCREENTYPE","AUTO");
        layoutManager.put("",true);
    }
//
//    public AppComponent getAppComponent() {
//        return appComponent;
//    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }


}
