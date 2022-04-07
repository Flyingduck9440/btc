package com.mafiaz.linemanintern.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private final static String URL = "https://api.coinranking.com";

    private final static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static APIService getRequest() {
        return retrofit.create(APIService.class);
    }

}
