package com.simplesoftware.cotacao_dolar_e_euro.model.util;

import com.simplesoftware.cotacao_dolar_e_euro.model.requests.BitCoin;
import com.simplesoftware.cotacao_dolar_e_euro.model.requests.Dolar;
import com.simplesoftware.cotacao_dolar_e_euro.model.requests.DolarTurismo;
import com.simplesoftware.cotacao_dolar_e_euro.model.requests.Euro;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ServiceConfig {


    @GET("all/USD/")
    Call<Dolar> buscarDolar();

    @GET("all/USDT/")
    Call<DolarTurismo> buscarDolarTurismo();

    @GET("all/EUR")
    Call<Euro> buscarEuro();

    @GET("all/BTC")
    Call<BitCoin> buscarBitCoin();
}
