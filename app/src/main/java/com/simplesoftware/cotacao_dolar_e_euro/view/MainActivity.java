package com.simplesoftware.cotacao_dolar_e_euro.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
    private ImageView dolar_arrow, dolarTurismo_arrow, euro_arrow, bitCoin_arrow;

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
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        String dataFormatada = sdf.format(data);

        tv_data.setText(dataFormatada);

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
    }

    public void buscarInfoDolar() {

        Call<Dolar> callDolar = new RetrofitConfig().getServiceConfig().buscarDolar();
        callDolar.enqueue(new Callback<Dolar>() {
            @Override
            public void onResponse(Call<Dolar> call, Response<Dolar> response) {
                if (!response.isSuccessful()) {
                    onFailureMessage(response.message());
                } else {
                    Dolar dolar = response.body();
                    double dValorDolar = Double.parseDouble(dolar.USD.getAsk());
                    String sValorDolar = String.format("%.2f", dValorDolar).replace(".", ",");
                    et_dolar.setText("R$ " + sValorDolar);

                    String pctDolar = dolar.USD.getPctChange();
                    pct_dolar.setText(pctDolar + "%");
                    if (Double.parseDouble(pctDolar) < 0.0) {
                        dolar_arrow.setImageResource(R.drawable.ic_arrowdown);
                    } else if (Double.parseDouble(pctDolar) == 0.0) {
                        dolar_arrow.setVisibility(View.INVISIBLE);
                    } else {
                        dolar_arrow.setImageResource(R.drawable.ic_arrowup);
                    }
                }
            }

            @Override
            public void onFailure(Call<Dolar> call, Throwable t) {
                onFailureMessage(t.getMessage());
            }
        });
    }

    public void buscarInfoDolarTurismo() {
        Call<DolarTurismo> dolarTurismoCall = new RetrofitConfig().getServiceConfig().buscarDolarTurismo();
        dolarTurismoCall.enqueue(new Callback<DolarTurismo>() {
            @Override
            public void onResponse(Call<DolarTurismo> call, Response<DolarTurismo> response) {
                if (!response.isSuccessful()) {
                    onFailureMessage(response.message());
                } else {
                    DolarTurismo dolarTurismo = response.body();
                    double dValorDolarTurismo = Double.parseDouble(dolarTurismo.USDT.getAsk());
                    String sValorDolarTurismo = String.format("%.2f", dValorDolarTurismo).replace(".", ",");
                    et_dolarTurismo.setText("R$ " + sValorDolarTurismo);

                    String pctDolarTurismo = dolarTurismo.USDT.getPctChange();
                    pct_dolarTurismo.setText(pctDolarTurismo + "%");
                    if (Double.parseDouble(pctDolarTurismo) < 0.0) {
                        dolarTurismo_arrow.setImageResource(R.drawable.ic_arrowdown);
                    } else if (Double.parseDouble(pctDolarTurismo) == 0.0) {
                        dolarTurismo_arrow.setVisibility(View.INVISIBLE);
                    } else {
                        dolarTurismo_arrow.setImageResource(R.drawable.ic_arrowup);
                    }
                }
            }

            @Override
            public void onFailure(Call<DolarTurismo> call, Throwable t) {
                onFailureMessage(t.getMessage());
            }
        });
    }

    public void buscarInfoEuro() {
        Call<Euro> callEuro = new RetrofitConfig().getServiceConfig().buscarEuro();
        callEuro.enqueue(new Callback<Euro>() {
            @Override
            public void onResponse(Call<Euro> call, Response<Euro> response) {
                if (!response.isSuccessful()) {
                    onFailureMessage(response.message());
                } else {
                    Euro euro = response.body();
                    double dValorEuro = Double.parseDouble(euro.EUR.getAsk());
                    String sValorEuro = String.format("%.2f", dValorEuro).replace(".", ",");
                    et_euro.setText("R$ " + sValorEuro);

                    String pctEuro = euro.EUR.getPctChange();
                    pct_euro.setText(pctEuro + "%");
                    if (Double.parseDouble(pctEuro) < 0.0) {
                        euro_arrow.setImageResource(R.drawable.ic_arrowdown);
                    } else if (Double.parseDouble(pctEuro) == 0.0) {
                        euro_arrow.setVisibility(View.INVISIBLE);
                    } else {
                        euro_arrow.setImageResource(R.drawable.ic_arrowup);
                    }
                }
            }

            @Override
            public void onFailure(Call<Euro> call, Throwable t) {
                onFailureMessage(t.getMessage());
            }
        });
    }

    public void buscarInfoBitCoin() {
        Call<BitCoin> bitCoinCall = new RetrofitConfig().getServiceConfig().buscarBitCoin();
        bitCoinCall.enqueue(new Callback<BitCoin>() {
            @Override
            public void onResponse(Call<BitCoin> call, Response<BitCoin> response) {
                if (!response.isSuccessful()) {
                    onFailureMessage(response.message());
                } else {
                    BitCoin bitCoin = response.body();
                    double dValorBitCoin = Double.parseDouble(bitCoin.BTC.getAsk());
                    String sValorBitCoin = String.format("%.2f", dValorBitCoin).replace(".", ",");
                    et_btc.setText("R$ " + sValorBitCoin);

                    String pctBtc = bitCoin.BTC.getPctChange();
                    pct_BitCoin.setText(pctBtc + "%");
                    if (Double.parseDouble(pctBtc) < 0.0) {
                        bitCoin_arrow.setImageResource(R.drawable.ic_arrowdown);
                    } else if (Double.parseDouble(pctBtc) == 0.0) {
                        bitCoin_arrow.setVisibility(View.INVISIBLE);
                    } else {
                        bitCoin_arrow.setImageResource(R.drawable.ic_arrowup);
                    }
                }
            }

            @Override
            public void onFailure(Call<BitCoin> call, Throwable t) {
                onFailureMessage(t.getMessage());
            }
        });
    }

    @Override
    public void irDolar(View view) {
        startActivity(new Intent(this, DolarActivity.class));
    }

    @Override
    public void irDolarTurismo(View view) {
        startActivity(new Intent(this, DolarTurismoActivity.class));
    }

    @Override
    public void irEuro(View view) {
        startActivity(new Intent(this, EuroActivity.class));
    }

    @Override
    public void irBtc(View view) {
        startActivity(new Intent(this, BitCoinActivity.class));
    }

    @Override
    public void onFailureMessage(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}