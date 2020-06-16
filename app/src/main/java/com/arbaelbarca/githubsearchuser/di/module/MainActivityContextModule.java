package com.arbaelbarca.githubsearchuser.di.module;

import android.content.Context;

import com.arbaelbarca.githubsearchuser.MainActivity;
import com.arbaelbarca.githubsearchuser.di.qulifier.ActivityContext;
import com.arbaelbarca.githubsearchuser.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityContextModule {
    MainActivity mainActivity;
    Context context;

    public MainActivityContextModule(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        context = mainActivity;
    }

    @Provides
    @ActivityScope
    public MainActivity providesMainActivity() {
        return mainActivity;
    }

    @Provides
    @ActivityScope
    @ActivityContext
    public Context provideContext() {
        return context;
    }
}
