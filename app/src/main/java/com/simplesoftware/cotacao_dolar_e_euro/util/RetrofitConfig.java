package com.simplesoftware.cotacao_dolar_e_euro.util;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitConfig {

    public static final String API_BASE_URL = "https://economia.awesomeapi.com.br/json/";
    private final Retrofit retrofit;

    public RetrofitConfig() {
        this.retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public ServiceConfig getServiceConfig(){
        return this.retrofit.create(ServiceConfig.class);
    }

}
