package com.example.vlcfullscreen.dagger;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.vlcfullscreen.App;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjectionModule;

@Module(includes = AndroidInjectionModule.class)
public class ContextModule {
    @Provides
    @Singleton
    SharedPreferences provideSharedPreferense(App app){
        return app.getSharedPreferences("pref",Context.MODE_PRIVATE);
    };
}
