package com.arbaelbarca.githubsearchuser.di.component;

import android.content.Context;

import com.arbaelbarca.githubsearchuser.MyApplication;
import com.arbaelbarca.githubsearchuser.di.module.ContextModule;
import com.arbaelbarca.githubsearchuser.di.module.RetrofitModule;
import com.arbaelbarca.githubsearchuser.network.ApiServices;
import com.arbaelbarca.githubsearchuser.di.qulifier.ApplicationContext;
import com.arbaelbarca.githubsearchuser.di.scope.ApplicationScope;

import dagger.Component;

@ApplicationScope
@Component(modules = {ContextModule.class, RetrofitModule.class})
public interface ApplicationComponent {

    public ApiServices getApiInterface();

    @ApplicationContext
    public Context getContext();

    public void injectApplication(MyApplication myApplication);
}
