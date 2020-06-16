package com.arbaelbarca.githubsearchuser;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.arbaelbarca.githubsearchuser.activity.DetailUserActivity;
import com.arbaelbarca.githubsearchuser.adapter.AdapterListUserNew;
import com.arbaelbarca.githubsearchuser.baseactivity.BaseActivity;
import com.arbaelbarca.githubsearchuser.di.component.ApplicationComponent;
import com.arbaelbarca.githubsearchuser.di.component.DaggerMainActivityComponent;
import com.arbaelbarca.githubsearchuser.di.component.MainActivityComponent;
import com.arbaelbarca.githubsearchuser.model.ItemsItem;
import com.arbaelbarca.githubsearchuser.model.ModelListUser;
import com.arbaelbarca.githubsearchuser.di.module.MainActivityContextModule;
import com.arbaelbarca.githubsearchuser.network.ApiServices;
import com.arbaelbarca.githubsearchuser.onclick.OnClickItem;
import com.arbaelbarca.githubsearchuser.di.qulifier.ActivityContext;
import com.arbaelbarca.githubsearchuser.di.qulifier.ApplicationContext;
import com.arbaelbarca.githubsearchuser.utils.LineItemDivider;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.arbaelbarca.githubsearchuser.utils.Constants.DATA_ITEMS;

public class MainActivity extends BaseActivity implements View.OnClickListener, AdapterListUserNew.ClickListener {


    @Inject
    AdapterListUserNew adapterListUser;

    @BindView(R.id.txtSearchUser)
    EditText txtSearchUser;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.rvListUser)
    RecyclerView rvListUser;

    LinearLayoutManager linearLayoutManager;
    @BindView(R.id.llNotFound)
    LinearLayout llNotFound;
    @BindView(R.id.imgSearch)
    ImageView imgClose;


    private boolean isLastPage = false;
    private int per_page = 10;
    private boolean isLoading = false;
    int itemCount = 0;

    private int PAGE_START = 1;
    private int currentPage = PAGE_START;
    String getTextSearch;
    ArrayList<ItemsItem> itemArrayList = new ArrayList<>();


    @Inject
    public ApiServices apiInterface;

    @Inject
    @ApplicationContext
    public Context mContext;

    @Inject
    @ActivityContext
    public Context activityContext;

    MainActivityComponent mainActivityComponent;

    OnClickItem onClickItem = pos -> {
        ItemsItem itemsItem = itemArrayList.get(pos);
        Intent intent = new Intent(getApplicationContext(), DetailUserActivity.class);
        intent.putExtra(DATA_ITEMS, itemsItem);
        startActivity(intent);
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ApplicationComponent applicationComponent = MyApplication.get(this).getApplicationComponent();
        mainActivityComponent = DaggerMainActivityComponent.builder()
                .mainActivityContextModule(new MainActivityContextModule(this))
                .applicationComponent(applicationComponent)
                .build();

        mainActivityComponent.injectMainActivity(this);

        initial();


        txtSearchUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                getTextSearch = charSequence.toString();
                if (getTextSearch.length() > 0) {
                    addSearchText(getTextSearch);
                    showView(rvListUser);
                    showView(imgClose);
                    hideView(llNotFound);
                } else {
                    hideView(rvListUser);
                    hideView(imgClose);
                    showView(llNotFound);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        imgClose.setOnClickListener(this);


        apiInterface.getListUser("arba", "", "1", "")
                .enqueue(new Callback<ModelListUser>() {
                    @Override
                    public void onResponse(Call<ModelListUser> call, Response<ModelListUser> response) {
                        getListUser(response.body().getItems());
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<ModelListUser> call, Throwable t) {

                    }
                });

    }

    void getListUser(ArrayList<ItemsItem> itemsItems) {
        itemArrayList = itemsItems;
        adapterListUser.setData(itemsItems);

    }


    private void addSearchText(String charSequence) {
        itemArrayList.clear();
    }

    private void initial() {
        linearLayoutManager = new LinearLayoutManager(this);
        rvListUser.setLayoutManager(linearLayoutManager);
        rvListUser.setHasFixedSize(true);
        rvListUser.addItemDecoration(new LineItemDivider(this));
        rvListUser.setAdapter(adapterListUser);

    }


    @Override
    public void onClick(View view) {
        if (view == imgClose) {
            txtSearchUser.setText("");
        }
    }

    @Override
    public void launchIntent(int pos) {
        ItemsItem itemsItem = itemArrayList.get(pos);
        Intent intent = new Intent(getApplicationContext(), DetailUserActivity.class);
        intent.putExtra(DATA_ITEMS, itemsItem);
        startActivity(intent);
    }
}
