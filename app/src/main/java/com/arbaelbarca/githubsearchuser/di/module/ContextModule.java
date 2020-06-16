package com.arbaelbarca.githubsearchuser.di.module;

import android.content.Context;

import com.arbaelbarca.githubsearchuser.di.qulifier.ApplicationContext;
import com.arbaelbarca.githubsearchuser.di.scope.ApplicationScope;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {
    Context context;


    public ContextModule(Context context) {
        this.context = context;
    }

    @Provides
    @ApplicationScope
    @ApplicationContext
    public Context getContext(){
        return context;
    }
}
