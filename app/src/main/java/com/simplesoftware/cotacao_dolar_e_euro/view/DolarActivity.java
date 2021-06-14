package com.simplesoftware.cotacao_dolar_e_euro.view;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.simplesoftware.cotacao_dolar_e_euro.R;
import com.simplesoftware.cotacao_dolar_e_euro.conversor.Conversor;
import com.simplesoftware.cotacao_dolar_e_euro.presenter.DolarComercialContract;
import com.simplesoftware.cotacao_dolar_e_euro.presenter.DolarComercialPresenter;


import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DolarActivity extends AppCompatActivity implements DolarComercialContract.MvpView {

    DolarComercialPresenter mPresenter;

    private TextView tv_high, tv_low, tv_varBid, tv_pctChange, tv_bid, tv_ask, tv_data, tv_titulo;
    private LinearLayout layoutCopy;
    private FloatingActionButton floatingHome, floatingConversor;
    private AdView adView;
    private AdRequest adRequest;
    private String dataFormatada;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dolar);

        instanciarComponentes();
        handleDate();
        handleDataFromPresenter();
        loadAds();
        handleClicks();

    }

    public void instanciarComponentes() {
        mPresenter = new DolarComercialPresenter(this);
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
        floatingHome.setOnClickListener(v-> startActivity(new Intent(this, MainActivity.class)));
        floatingConversor.setOnClickListener(v-> startActivity(new Intent(this, Conversor.class)));
    }

    @Override
    public void loadAds() {
        MobileAds.initialize(this, initializationStatus -> {
        });
        adRequest = new AdRequest.Builder().build();
        adView = findViewById(R.id.adView);
        adView.loadAd(adRequest);
    }

    @Override
    public void onFailureMessage(String message) {
        Toast.makeText(DolarActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccessMessage(String message) {
        Toast.makeText(DolarActivity.this, message, Toast.LENGTH_SHORT).show();
    }


}