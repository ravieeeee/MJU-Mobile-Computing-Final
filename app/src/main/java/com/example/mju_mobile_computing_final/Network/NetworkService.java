package com.example.mju_mobile_computing_final.Network;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NetworkService {
    @GET("/json/color/random")
    Call<JsonObject> getRandomColor();
}
