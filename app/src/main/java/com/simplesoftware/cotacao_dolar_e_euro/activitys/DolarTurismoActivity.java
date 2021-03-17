package com.simplesoftware.cotacao_dolar_e_euro.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.button.MaterialButton;
import com.simplesoftware.cotacao_dolar_e_euro.R;
import com.simplesoftware.cotacao_dolar_e_euro.classes.DolarTurismo;
import com.simplesoftware.cotacao_dolar_e_euro.util.RetrofitConfig;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DolarTurismoActivity extends AppCompatActivity {

    private TextView tv_high, tv_low, tv_varBid, tv_pctChange, tv_bid, tv_ask, tv_data;
    private String copiarCotacao;
    private EditText et_conversor1, et_conversor2;
    private ImageView img_inverterConversor;
    private LocalDate dataAtual;
    private AdView adView;
    private AdRequest adRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dolar_turismo);

        instanciarComponentes();
        buscarInfo();
        googleAds();

        img_inverterConversor.setImageResource(R.drawable.ic_arrow);
        et_conversor2.setEnabled(false);
        et_conversor1.requestFocus();

        Date data = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        String dataFormatada = sdf.format(data);

        tv_data.setText(dataFormatada);

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
        tv_data = findViewById(R.id.tv_data);
        et_conversor1 = findViewById(R.id.et_conversor1);
        et_conversor2 = findViewById(R.id.et_conversor2);
        img_inverterConversor = findViewById(R.id.img_inverterConversor);
    }

    public void buscarInfo() {
        Call<DolarTurismo> dolarTurismoCall = new RetrofitConfig().getServiceConfig().buscarDolarTurismo();
        dolarTurismoCall.enqueue(new Callback<DolarTurismo>() {
            @Override
            public void onResponse(Call<DolarTurismo> call, Response<DolarTurismo> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(DolarTurismoActivity.this, "Erro: " + response.code(), Toast.LENGTH_SHORT).show();
                } else {
                    DolarTurismo dolarTurismo = response.body();
                    tv_high.setText(dolarTurismo.USDT.getHigh());
                    tv_low.setText(dolarTurismo.USDT.getLow());
                    tv_varBid.setText(dolarTurismo.USDT.getVarBid());
                    tv_pctChange.setText(dolarTurismo.USDT.getPctChange());
                    tv_bid.setText(dolarTurismo.USDT.getBid());
                    tv_ask.setText(dolarTurismo.USDT.getAsk());

                    copiarCotacao = dolarTurismo.USDT.toString();

                }
            }

            @Override
            public void onFailure(Call<DolarTurismo> call, Throwable t) {
                Toast.makeText(DolarTurismoActivity.this, "Erro: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void copiarCotacao(View view) {
        try {
            Toast.makeText(DolarTurismoActivity.this, "Cotação copiada para a Área de Transferência", Toast.LENGTH_SHORT).show();

            ClipboardManager clipboard = (ClipboardManager)
                    getSystemService(Context.CLIPBOARD_SERVICE);

            ClipData cpy_all = ClipData.newPlainText("text", "Dólar Turismo:\n" + dataAtual + "\n\n" + copiarCotacao);
            clipboard.setPrimaryClip(cpy_all);
        } catch (Exception e) {
            Toast.makeText(this, "Tente novamente" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void HOME(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void googleAds() {

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        adRequest = new AdRequest.Builder().build();
        adView = findViewById(R.id.adView);
        adView.loadAd(adRequest);

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void inverterSeta(View v) {
        if (img_inverterConversor.getDrawable().getConstantState().equals(img_inverterConversor.getContext()
                .getDrawable(R.drawable.ic_arrow).getConstantState())) {
            img_inverterConversor.setImageResource(R.drawable.ic_arrow_left);
            et_conversor1.setEnabled(false);
            et_conversor2.setEnabled(true);
            et_conversor2.requestFocus();
        } else {
            img_inverterConversor.setImageResource(R.drawable.ic_arrow);
            et_conversor2.setEnabled(false);
            et_conversor1.setEnabled(true);
            et_conversor1.requestFocus();
        }

        et_conversor1.setText("");
        et_conversor2.setText("");
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void converter(View v) {

        try {
            if (img_inverterConversor.getDrawable().getConstantState().equals(img_inverterConversor.getContext()
                    .getDrawable(R.drawable.ic_arrow).getConstantState())) {
                String sValorDolarTurismo = String.valueOf(tv_ask.getText());
                double dValorDolarTurismo = Double.parseDouble(sValorDolarTurismo);
                String sValorReal = String.valueOf(et_conversor1.getText());
                double dValorReal = Double.parseDouble(sValorReal);
                double total = dValorReal / dValorDolarTurismo;
                et_conversor2.setText(String.format("%.2f", total).replace(".", ","));
            } else {
                String sValorDolarTurismo = String.valueOf(tv_ask.getText());
                double dValorDolarTurismo = Double.parseDouble(sValorDolarTurismo);
                String sValorDigitado = String.valueOf(et_conversor2.getText());
                double dValorDigitado = Double.parseDouble(sValorDigitado);
                double total = dValorDigitado * dValorDolarTurismo;
                et_conversor1.setText(String.format("%.2f", total).replace(".", ","));
            }
        } catch (Exception e) {
            Toast.makeText(this, "Erro: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}