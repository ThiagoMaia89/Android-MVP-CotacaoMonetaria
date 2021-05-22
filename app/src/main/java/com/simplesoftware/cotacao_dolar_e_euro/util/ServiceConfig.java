package com.simplesoftware.cotacao_dolar_e_euro.util;

import com.simplesoftware.cotacao_dolar_e_euro.requests.BitCoin;
import com.simplesoftware.cotacao_dolar_e_euro.requests.Dolar;
import com.simplesoftware.cotacao_dolar_e_euro.requests.DolarTurismo;
import com.simplesoftware.cotacao_dolar_e_euro.requests.Euro;

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
