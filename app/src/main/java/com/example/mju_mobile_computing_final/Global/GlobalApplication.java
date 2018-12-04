package com.example.mju_mobile_computing_final.Global;

import android.app.Application;

import com.example.mju_mobile_computing_final.Network.NetworkService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GlobalApplication extends Application {
    public static NetworkService service;

    @Override
    public void onCreate() {
        super.onCreate();
        retrofitInit();
    }

    private static void retrofitInit() {
        Retrofit retrofit =
            new Retrofit
                .Builder()
                .baseUrl("http://www.colr.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(NetworkService.class);
    }
}
