package com.simplesoftware.cotacao_dolar_e_euro.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.simplesoftware.cotacao_dolar_e_euro.R;
import com.simplesoftware.cotacao_dolar_e_euro.conversor.Conversor;
import com.simplesoftware.cotacao_dolar_e_euro.model.requests.DolarTurismo;
import com.simplesoftware.cotacao_dolar_e_euro.model.util.RetrofitConfig;
import com.simplesoftware.cotacao_dolar_e_euro.presenter.DolarTurismoContract;
import com.simplesoftware.cotacao_dolar_e_euro.presenter.DolarTurismoPresenter;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DolarTurismoActivity extends AppCompatActivity implements DolarTurismoContract.MvpView {

    DolarTurismoPresenter mPresenter;

    private TextView tv_high, tv_low, tv_varBid, tv_pctChange, tv_bid, tv_ask, tv_data, tv_titulo;
    private LinearLayout layoutCopy;
    private FloatingActionButton floatingHome, floatingConversor;
    private String dataFormatada;

    private AdView adView;
    private AdRequest adRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dolar_turismo);

        instanciarComponentes();
        handleDate();
        handleDataFromPresenter();
        loadAds();
        handleClicks();
    }

    public void instanciarComponentes() {

        mPresenter = new DolarTurismoPresenter(this);

        tv_high = findViewById(R.id.tv_high);
        tv_low = findViewById(R.id.tv_low);
        tv_varBid = findViewById(R.id.tv_varBid);
        tv_pctChange = findViewById(R.id.tv_pctChange);
        tv_bid = findViewById(R.id.tv_bid);
        tv_ask = findViewById(R.id.tv_ask);
        tv_data = findViewById(R.id.tv_data);
        tv_titulo = findViewById(R.id.tv_titulo);
        layoutCopy = findViewById(R.id.layout_copy);
        floatingHome = findViewById(R.id.floatingHome);
        floatingConversor = findViewById(R.id.floatingConversor);
    }


    @Override
    public void onFailureMessage(String message) {
        Toast.makeText(DolarTurismoActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessMessage(String message) {
        Toast.makeText(DolarTurismoActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void handleDataFromPresenter() {
        mPresenter.handleDataPassed(tv_high, tv_low, tv_varBid, tv_pctChange, tv_bid, tv_ask, tv_titulo, dataFormatada, this, layoutCopy);
    }

    @Override
    public void handleDate() {
        Date data = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        dataFormatada = sdf.format(data);
        tv_data.setText(dataFormatada);
    }

    @Override
    public void handleClicks() {
        floatingHome.setOnClickListener(v -> startActivity(new Intent(this, MainActivity.class)));
        floatingConversor.setOnClickListener(v -> startActivity(new Intent(this, Conversor.class)));
    }

    @Override
    public void loadAds() {
        MobileAds.initialize(this, initializationStatus -> {
        });
        adRequest = new AdRequest.Builder().build();
        adView = findViewById(R.id.adView);
        adView.loadAd(adRequest);

    }
}