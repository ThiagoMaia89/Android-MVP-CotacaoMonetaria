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

import com.simplesoftware.cotacao_dolar_e_euro.R;
import com.simplesoftware.cotacao_dolar_e_euro.classes.Dolar;
import com.simplesoftware.cotacao_dolar_e_euro.classes.Euro;
import com.simplesoftware.cotacao_dolar_e_euro.classes.Moedas;
import com.simplesoftware.cotacao_dolar_e_euro.util.RetrofitConfig;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private EditText et_dolar, et_euro;
    private TextView tv_data;
    private LocalDate localDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        instanciarComponentes();
        buscarInfoDolar();
        buscarInfoEuro();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            localDate = LocalDate.now();
            tv_data.setText(localDate.toString());
        } else {
            tv_data.setText("");
        }

    }

    public void instanciarComponentes() {
        et_dolar = findViewById(R.id.et_dolar);
        et_euro = findViewById(R.id.et_euro);
        tv_data = findViewById(R.id.tv_data);
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

    public void irDolar(View v) {
        startActivity(new Intent(this, DolarActivity.class));
    }

    public void irEuro(View view) {
        startActivity(new Intent(this, EuroActivity.class));
    }

    public void HOME(View v){
        startActivity(new Intent (this, MainActivity.class));
    }

}