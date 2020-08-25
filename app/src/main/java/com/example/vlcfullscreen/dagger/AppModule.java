package com.example.vlcfullscreen.dagger;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.SurfaceHolder;

import androidx.preference.PreferenceManager;

import com.example.vlcfullscreen.App;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.AndroidInjectionModule;

@Module(includes = AndroidInjectionModule.class)
public abstract class AppModule {
    @Binds
    @Singleton
    abstract Application application(App app);
    @Binds
    @Singleton
    abstract Context provideContext(App app);
}
