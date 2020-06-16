package com.arbaelbarca.githubsearchuser.di.component;

import com.arbaelbarca.githubsearchuser.activity.DetailUserActivity;
import com.arbaelbarca.githubsearchuser.adapter.AdapterModule;
import com.arbaelbarca.githubsearchuser.di.scope.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(dependencies = ApplicationComponent.class)
public interface DetailListsUserComponent {

    void inject(DetailUserActivity detailUserActivity);
}
