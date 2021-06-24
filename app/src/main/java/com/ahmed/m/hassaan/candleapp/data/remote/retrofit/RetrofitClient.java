package com.ahmed.m.hassaan.candleapp.data.remote.retrofit;

import com.ahmed.m.hassaan.candleapp.data.remote.retrofit.api.ArticlesOperations;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static RetrofitClient mInstance;
    private static Retrofit retrofit;
    static final String BASE_URL = "https://aabojana09.pythonanywhere.com/";

    private RetrofitClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(
                        new OkHttpClient
                                .Builder()
                                .callTimeout(30, TimeUnit.SECONDS)
                                .connectTimeout(30, TimeUnit.SECONDS).build()
                )
                .build();
    }

    public static synchronized RetrofitClient getInstance() {
        if (mInstance == null)
            mInstance = new RetrofitClient();
        return mInstance;
    }

    public ArticlesOperations getArticlesOperation() {
        return retrofit.create(ArticlesOperations.class);
    }


}
