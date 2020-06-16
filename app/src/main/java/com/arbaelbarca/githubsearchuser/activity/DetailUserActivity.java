package com.arbaelbarca.githubsearchuser.activity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.arbaelbarca.githubsearchuser.MyApplication;
import com.arbaelbarca.githubsearchuser.R;
import com.arbaelbarca.githubsearchuser.di.component.ApplicationComponent;
import com.arbaelbarca.githubsearchuser.di.component.DaggerDetailListsUserComponent;
import com.arbaelbarca.githubsearchuser.di.component.DaggerMainActivityComponent;
import com.arbaelbarca.githubsearchuser.di.component.DetailListsUserComponent;
import com.arbaelbarca.githubsearchuser.di.module.MainActivityContextModule;
import com.arbaelbarca.githubsearchuser.di.qulifier.ApplicationContext;
import com.arbaelbarca.githubsearchuser.model.ItemsItem;
import com.arbaelbarca.githubsearchuser.utils.Constants;
import com.bumptech.glide.Glide;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailUserActivity extends AppCompatActivity {

    @BindView(R.id.user_avatar)
    ImageView userAvatar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.user_name_details)
    TextView userNameDetails;
    @BindView(R.id.github_profile_url)
    TextView githubProfileUrl;

    ItemsItem itemsItem;


    @Inject
    @ApplicationContext
    Context context;

    DetailListsUserComponent detailListsUserComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_user);
        ButterKnife.bind(this);

        ApplicationComponent applicationComponent = MyApplication.get(this).getApplicationComponent();
        detailListsUserComponent = DaggerDetailListsUserComponent.builder()
                .applicationComponent(applicationComponent)
                .build();

        detailListsUserComponent.inject(this);

        initial();
    }

    private void initial() {
        itemsItem = getIntent().getParcelableExtra(Constants.DATA_ITEMS);

        if (itemsItem != null) {
            userNameDetails.setText(itemsItem.getLogin());
        }
        githubProfileUrl.setText(itemsItem.getHtmlUrl());

        Glide.with(this)
                .load(itemsItem.getAvatarUrl())
                .into(userAvatar);

    }
}
