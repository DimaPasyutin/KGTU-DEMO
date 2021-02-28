package com.example.kgtu.data.api;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiNews {

    private static ApiNews apiNews;
    private static Retrofit retrofit;
    public static final String BASE_URL ="http://api.vk.com/";

    private ApiNews() {
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
    }

    public static ApiNews getInstance() {
        if (apiNews == null) {
            apiNews = new ApiNews();
        }
        return apiNews;
    }

    public ApiService getApiNews() {
        return retrofit.create(ApiService.class);
    }

}
