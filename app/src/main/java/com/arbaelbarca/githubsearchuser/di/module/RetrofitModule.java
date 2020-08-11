package com.arbaelbarca.githubsearchuser.di.module;

import com.arbaelbarca.githubsearchuser.network.ApiServices;
import com.arbaelbarca.githubsearchuser.di.scope.ApplicationScope;
import com.arbaelbarca.githubsearchuser.utils.Constants;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import dagger.Module;
import dagger.Provides;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class RetrofitModule implements Interceptor {
    private String credentials;

    @Provides
    @ApplicationScope
    ApiServices getApiInterface(Retrofit retroFit) {
        return retroFit.create(ApiServices.class);
    }


    @Provides
    @ApplicationScope
    Retrofit retrofitModule() {
        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(provideOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

    }


    @Provides
    @ApplicationScope
     OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(getLoggingInterceptor())
                .addInterceptor(chain -> {
                    Request request = chain.request();
                    credentials = Credentials.basic("","");//disi dengan username dan password github
                    Request authenticatedRequest = request.newBuilder()
                            .header("Authorization", credentials).build();
                    return chain.proceed(authenticatedRequest);
                })
                .readTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    @ApplicationScope
     HttpLoggingInterceptor getLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        return interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    }


    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        Request authenticatedRequest = request.newBuilder()
                .header("Authorization", credentials).build();
        return chain.proceed(authenticatedRequest);
    }
}
