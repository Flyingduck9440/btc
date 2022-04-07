package com.mafiaz.linemanintern.api;

import com.mafiaz.linemanintern.data.RespondData;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {

    @GET("/v2/coins")
    Call<RespondData> getCoinList();
}
