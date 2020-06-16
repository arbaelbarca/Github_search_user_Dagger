package com.arbaelbarca.githubsearchuser.di.component;

import android.content.Context;

import com.arbaelbarca.githubsearchuser.MainActivity;
import com.arbaelbarca.githubsearchuser.adapter.AdapterModule;
import com.arbaelbarca.githubsearchuser.di.qulifier.ActivityContext;
import com.arbaelbarca.githubsearchuser.di.scope.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(modules = AdapterModule.class, dependencies = ApplicationComponent.class)
public interface MainActivityComponent {

    @ActivityContext
    Context getContext();

    void injectMainActivity(MainActivity mainActivity);

}
