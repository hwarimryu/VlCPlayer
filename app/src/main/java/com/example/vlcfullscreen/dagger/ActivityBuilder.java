package com.example.vlcfullscreen.dagger;

import com.example.vlcfullscreen.activity.CctvActivity;
import com.example.vlcfullscreen.activity.MainActivity;
import com.example.vlcfullscreen.activity.SettingActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {
//
    @ContributesAndroidInjector()
    abstract CctvActivity bindCctvActivity();

    @ContributesAndroidInjector()
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector()
    abstract SettingActivity bindSettingActivity();
}
