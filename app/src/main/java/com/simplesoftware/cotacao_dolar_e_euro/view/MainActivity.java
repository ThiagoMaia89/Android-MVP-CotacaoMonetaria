package com.simplesoftware.cotacao_dolar_e_euro.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.simplesoftware.cotacao_dolar_e_euro.R;
import com.simplesoftware.cotacao_dolar_e_euro.model.requests.BitCoin;
import com.simplesoftware.cotacao_dolar_e_euro.model.requests.Dolar;
import com.simplesoftware.cotacao_dolar_e_euro.model.requests.DolarTurismo;
import com.simplesoftware.cotacao_dolar_e_euro.model.requests.Euro;
import com.simplesoftware.cotacao_dolar_e_euro.model.util.RetrofitConfig;
import com.simplesoftware.cotacao_dolar_e_euro.presenter.MainActivityContract;
import com.simplesoftware.cotacao_dolar_e_euro.presenter.MainActivityPresenter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements MainActivityContract.MvpView {

    MainActivityPresenter mPresenter;

    private EditText et_dolar, et_euro, et_dolarTurismo, et_btc;
    private TextView tv_data, pct_dolar, pct_dolarTurismo, pct_euro, pct_BitCoin;
    private ConstraintLayout layoutDolar, layoutDolarTurismo, layoutEuro, layoutBitCoin;
    private ImageView dolar_arrow, dolarTurismo_arrow, euro_arrow, bitCoin_arrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        instanciarComponentes();
        calls();
        handleData();
        handleClicks();

    }

    public void instanciarComponentes() {

        mPresenter = new MainActivityPresenter(this);

        et_dolar = findViewById(R.id.et_dolar);
        et_euro = findViewById(R.id.et_euro);
        tv_data = findViewById(R.id.tv_data);
        et_dolarTurismo = findViewById(R.id.et_dolarTurismo);
        et_btc = findViewById(R.id.et_btc);
        pct_dolar = findViewById(R.id.pct_dolar);
        pct_dolarTurismo = findViewById(R.id.pct_dolarTurismo);
        pct_euro = findViewById(R.id.pct_euro);
        pct_BitCoin = findViewById(R.id.pct_BitCoin);
        dolar_arrow = findViewById(R.id.dolar_arrow);
        dolarTurismo_arrow = findViewById(R.id.dolarTurismo_arrow);
        euro_arrow = findViewById(R.id.euro_arrow);
        bitCoin_arrow = findViewById(R.id.bitCoin_arrow);
        layoutDolar = findViewById(R.id.layout_dolar);
        layoutDolarTurismo = findViewById(R.id.layout_dolar_turismo);
        layoutEuro = findViewById(R.id.layout_euro);
        layoutBitCoin = findViewById(R.id.layout_bitcoin);
    }

    public void calls() {
        mPresenter.handleDolarComercialCall(et_dolar, pct_dolar, dolar_arrow);
        mPresenter.handleDolarTurismoCall(et_dolarTurismo, pct_dolarTurismo, dolarTurismo_arrow);
        mPresenter.handleEuroCall(et_euro, pct_euro, euro_arrow);
        mPresenter.handleBitCoinCall(et_btc, pct_BitCoin, bitCoin_arrow);
    }

    @Override
    public void handleData() {
        Date data = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        String dataFormatada = sdf.format(data);
        tv_data.setText(dataFormatada);
    }

    @Override
    public void handleClicks() {
        layoutDolar.setOnClickListener(v -> startActivity(new Intent(this, DolarActivity.class)));
        layoutDolarTurismo.setOnClickListener(v -> startActivity(new Intent(this, DolarTurismoActivity.class)));
        layoutEuro.setOnClickListener(v -> startActivity(new Intent(this, EuroActivity.class)));
        layoutBitCoin.setOnClickListener(v -> startActivity(new Intent(this, BitCoinActivity.class)));
    }

    @Override
    public void onFailureMessage(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }

}