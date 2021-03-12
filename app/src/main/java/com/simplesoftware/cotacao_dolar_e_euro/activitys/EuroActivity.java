package com.simplesoftware.cotacao_dolar_e_euro.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.simplesoftware.cotacao_dolar_e_euro.R;
import com.simplesoftware.cotacao_dolar_e_euro.classes.Euro;
import com.simplesoftware.cotacao_dolar_e_euro.util.RetrofitConfig;

import java.time.LocalDate;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EuroActivity extends AppCompatActivity {

    private TextView tv_high, tv_low, tv_varBid, tv_pctChange, tv_bid, tv_ask;
    private String copiarCotacao;
    private LocalDate dataAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_euro);

        instanciarComponentes();
        buscarInfo();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dataAtual = LocalDate.now();
        }

    }

    public void instanciarComponentes() {
        tv_high = findViewById(R.id.tv_high);
        tv_low = findViewById(R.id.tv_low);
        tv_varBid = findViewById(R.id.tv_varBid);
        tv_pctChange = findViewById(R.id.tv_pctChange);
        tv_bid = findViewById(R.id.tv_bid);
        tv_ask = findViewById(R.id.tv_ask);
    }

    public void buscarInfo() {
        Call<Euro> callEuro = new RetrofitConfig().getServiceConfig().buscarEuro();
        callEuro.enqueue(new Callback<Euro>() {
            @Override
            public void onResponse(Call<Euro> call, Response<Euro> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(EuroActivity.this, "Erro: " + response.code(), Toast.LENGTH_SHORT).show();
                } else {
                    Euro euro = response.body();
                    tv_high.setText(euro.EUR.getHigh());
                    tv_low.setText(euro.EUR.getLow());
                    tv_varBid.setText(euro.EUR.getVarBid());
                    tv_pctChange.setText(euro.EUR.getPctChange());
                    tv_bid.setText(euro.EUR.getBid());
                    tv_ask.setText(euro.EUR.getAsk());

                    copiarCotacao = euro.EUR.toString();
                }
            }

            @Override
            public void onFailure(Call<Euro> call, Throwable t) {
                Toast.makeText(EuroActivity.this, "Erro: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void copiarCotacao(View v) {
        try {
            Toast.makeText(EuroActivity.this, "Cotação copiada para a Área de Transferência", Toast.LENGTH_SHORT).show();

            ClipboardManager clipboard = (ClipboardManager)
                    getSystemService(Context.CLIPBOARD_SERVICE);

            ClipData cpy_all = ClipData.newPlainText("text", "Euro:\n" + dataAtual + "\n\n" + copiarCotacao);
            clipboard.setPrimaryClip(cpy_all);
        } catch (Exception e) {
            Toast.makeText(this, "Tente novamente" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void HOME(View v){
        startActivity(new Intent(this, MainActivity.class));
    }

}