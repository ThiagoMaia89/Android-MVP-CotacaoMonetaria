package com.simplesoftware.cotacao_dolar_e_euro.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.simplesoftware.cotacao_dolar_e_euro.R;
import com.simplesoftware.cotacao_dolar_e_euro.classes.BitCoin;
import com.simplesoftware.cotacao_dolar_e_euro.classes.Dolar;
import com.simplesoftware.cotacao_dolar_e_euro.classes.DolarTurismo;
import com.simplesoftware.cotacao_dolar_e_euro.classes.Euro;
import com.simplesoftware.cotacao_dolar_e_euro.classes.Moedas;
import com.simplesoftware.cotacao_dolar_e_euro.util.RetrofitConfig;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private EditText et_dolar, et_euro, et_dolarTurismo, et_btc;
    private TextView tv_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        instanciarComponentes();
        buscarInfoDolar();
        buscarInfoDolarTurismo();
        buscarInfoEuro();
        buscarInfoBitCoin();


        Date data = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dataFormatada = sdf.format(data);

        tv_data.setText(dataFormatada);

    }

    public void instanciarComponentes() {
        et_dolar = findViewById(R.id.et_dolar);
        et_euro = findViewById(R.id.et_euro);
        tv_data = findViewById(R.id.tv_data);
        et_dolarTurismo = findViewById(R.id.et_dolarTurismo);
        et_btc = findViewById(R.id.et_btc);
    }

    public void buscarInfoDolar() {
        Call<Dolar> callDolar = new RetrofitConfig().getServiceConfig().buscarDolar();
        callDolar.enqueue(new Callback<Dolar>() {
            @Override
            public void onResponse(Call<Dolar> call, Response<Dolar> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Erro: " + response.code(), Toast.LENGTH_SHORT).show();
                } else {
                    Dolar dolar = response.body();
                    String showDolar = "R$ " + dolar.USD.getHigh().replace(".", ",");
                    et_dolar.setText(showDolar);
                }
            }

            @Override
            public void onFailure(Call<Dolar> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Erro: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void buscarInfoDolarTurismo(){
        Call<DolarTurismo> dolarTurismoCall = new RetrofitConfig().getServiceConfig().buscarDolarTurismo();
        dolarTurismoCall.enqueue(new Callback<DolarTurismo>() {
            @Override
            public void onResponse(Call<DolarTurismo> call, Response<DolarTurismo> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Erro: " + response.code(), Toast.LENGTH_SHORT).show();
                } else {
                    DolarTurismo dolarTurismo = response.body();
                    String showDolarTurismo = "R$ " + dolarTurismo.USDT.getHigh().replace(".", ",");
                    et_dolarTurismo.setText(showDolarTurismo);
                }
            }

            @Override
            public void onFailure(Call<DolarTurismo> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Erro: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void buscarInfoEuro() {
        Call<Euro> callEuro = new RetrofitConfig().getServiceConfig().buscarEuro();
        callEuro.enqueue(new Callback<Euro>() {
            @Override
            public void onResponse(Call<Euro> call, Response<Euro> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Erro: " + response.code(), Toast.LENGTH_SHORT).show();
                } else {
                    Euro euro = response.body();
                    String showEuro = "R$ " + euro.EUR.getHigh().replace(".", ",");
                    et_euro.setText(showEuro);
                }
            }

            @Override
            public void onFailure(Call<Euro> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Erro: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void buscarInfoBitCoin(){
        Call<BitCoin> bitCoinCall = new RetrofitConfig().getServiceConfig().buscarBitCoin();
        bitCoinCall.enqueue(new Callback<BitCoin>() {
            @Override
            public void onResponse(Call<BitCoin> call, Response<BitCoin> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Erro: " + response.code(), Toast.LENGTH_SHORT).show();
                } else{
                    BitCoin bitCoin = response.body();
                    String showBitCoin = "R$ " + bitCoin.BTC.getHigh().replace(".", ",");
                    et_btc.setText(showBitCoin);
                }
            }

            @Override
            public void onFailure(Call<BitCoin> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Erro: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void irDolar(View v) {
        startActivity(new Intent(this, DolarActivity.class));
    }

    public void irDolarTurismo(View view) {
        startActivity(new Intent(this, DolarTurismoActivity.class));
    }

    public void irEuro(View view) {
        startActivity(new Intent(this, EuroActivity.class));
    }

    public void irBtc(View view) {
        startActivity(new Intent(this, BitCoinActivity.class));
    }


    public void HOME(View v) {
        startActivity(new Intent(this, MainActivity.class));
    }


}