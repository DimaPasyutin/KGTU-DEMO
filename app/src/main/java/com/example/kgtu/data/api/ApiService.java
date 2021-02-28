package com.example.kgtu.data.api;


import com.example.kgtu.data.pojo.Example;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("method/wall.get")
    Observable<Example> getResponse(@Query("owner_id") long owner_id,
                                    @Query("count") long count, @Query("access_token") String access_token, @Query("v") double v);

}
