package com.example.vlcfullscreen.dagger;

import android.content.Context;

import com.example.vlcfullscreen.App;

import javax.inject.Singleton;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton //scope
@Component(modules ={
        AndroidSupportInjectionModule.class,
        AppModule.class,
        ActivityBuilder.class,
        ContextModule.class
})
interface AppComponent extends AndroidInjector<App> {
    //Application과의 연결을 도울 AndroidInjector를 상속받고, 제네릭으로 BaseApplication 클래스(App)를 정의

    ///application과의 연결을 도울 Builder를 정의
    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<App> {
    }

}
