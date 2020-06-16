package com.arbaelbarca.githubsearchuser.adapter;

import com.arbaelbarca.githubsearchuser.MainActivity;
import com.arbaelbarca.githubsearchuser.di.module.MainActivityContextModule;
import com.arbaelbarca.githubsearchuser.di.qulifier.ApplicationContext;
import com.arbaelbarca.githubsearchuser.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

@Module(includes = {MainActivityContextModule.class})
public class AdapterModule {


    @Provides
    @ActivityScope
    @ApplicationContext
    public AdapterListUserNew getStarWarsPeopleLIst(AdapterListUserNew.ClickListener clickListener) {
        return new AdapterListUserNew(clickListener);
    }

    @Provides
    @ActivityScope
    public AdapterListUserNew.ClickListener getClickListener(MainActivity mainActivity) {
        return mainActivity;
    }

}