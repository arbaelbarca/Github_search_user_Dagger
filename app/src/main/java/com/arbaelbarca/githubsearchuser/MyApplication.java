package com.arbaelbarca.githubsearchuser;

import android.app.Activity;
import android.app.Application;

import com.arbaelbarca.githubsearchuser.di.component.ApplicationComponent;
import com.arbaelbarca.githubsearchuser.di.component.DaggerApplicationComponent;
import com.arbaelbarca.githubsearchuser.di.module.ContextModule;

public class MyApplication extends Application {
    ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent.builder().contextModule(new ContextModule(this)).build();
        applicationComponent.injectApplication(this);

    }

    public static MyApplication get(Activity activity) {
        return (MyApplication) activity.getApplication();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

}
