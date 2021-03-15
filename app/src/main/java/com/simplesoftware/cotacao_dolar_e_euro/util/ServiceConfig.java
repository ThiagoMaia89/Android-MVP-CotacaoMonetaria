package com.simplesoftware.cotacao_dolar_e_euro.util;

import com.simplesoftware.cotacao_dolar_e_euro.classes.BitCoin;
import com.simplesoftware.cotacao_dolar_e_euro.classes.Dolar;
import com.simplesoftware.cotacao_dolar_e_euro.classes.DolarTurismo;
import com.simplesoftware.cotacao_dolar_e_euro.classes.Euro;
import com.simplesoftware.cotacao_dolar_e_euro.classes.Moedas;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

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
