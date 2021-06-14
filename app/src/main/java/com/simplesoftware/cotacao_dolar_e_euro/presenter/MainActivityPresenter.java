package com.simplesoftware.cotacao_dolar_e_euro.presenter;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.simplesoftware.cotacao_dolar_e_euro.R;
import com.simplesoftware.cotacao_dolar_e_euro.model.requests.BitCoin;
import com.simplesoftware.cotacao_dolar_e_euro.model.requests.Dolar;
import com.simplesoftware.cotacao_dolar_e_euro.model.requests.DolarTurismo;
import com.simplesoftware.cotacao_dolar_e_euro.model.requests.Euro;
import com.simplesoftware.cotacao_dolar_e_euro.model.util.RetrofitConfig;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityPresenter implements MainActivityContract.Presenter {

    private final MainActivityContract.MvpView mView;

    public MainActivityPresenter(MainActivityContract.MvpView mView) {
        this.mView = mView;
    }

    @SuppressLint("SetTextI18n, DefaultLocale")
    @Override
    public void handleDolarComercialCall(EditText et_dolar, TextView pct_dolar, ImageView dolar_arrow) {
        Call<Dolar> callDolar = new RetrofitConfig().getServiceConfig().buscarDolar();
        callDolar.enqueue(new Callback<Dolar>() {
            @Override
            public void onResponse(@NotNull Call<Dolar> call, @NotNull Response<Dolar> response) {
                if (!response.isSuccessful()) {
                    mView.onFailureMessage(response.message());
                } else {
                    Dolar dolar = response.body();
                    assert dolar != null;
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
            public void onFailure(@NotNull Call<Dolar> call, @NotNull Throwable t) {
                mView.onFailureMessage(t.getMessage());
            }
        });
    }

    @SuppressLint("SetTextI18n, DefaultLocale")
    @Override
    public void handleDolarTurismoCall(EditText et_dolarTurismo, TextView pct_dolarTurismo, ImageView dolarTurismo_arrow) {
        Call<DolarTurismo> dolarTurismoCall = new RetrofitConfig().getServiceConfig().buscarDolarTurismo();
        dolarTurismoCall.enqueue(new Callback<DolarTurismo>() {
            @Override
            public void onResponse(@NotNull Call<DolarTurismo> call, @NotNull Response<DolarTurismo> response) {
                if (!response.isSuccessful()) {
                    mView.onFailureMessage(response.message());
                } else {
                    DolarTurismo dolarTurismo = response.body();
                    assert dolarTurismo != null;
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
            public void onFailure(@NotNull Call<DolarTurismo> call, @NotNull Throwable t) {
                mView.onFailureMessage(t.getMessage());
            }
        });
    }

    @SuppressLint("SetTextI18n, DefaultLocale")
    @Override
    public void handleEuroCall(EditText et_euro, TextView pct_euro, ImageView euro_arrow) {
        Call<Euro> callEuro = new RetrofitConfig().getServiceConfig().buscarEuro();
        callEuro.enqueue(new Callback<Euro>() {
            @Override
            public void onResponse(@NotNull Call<Euro> call, @NotNull Response<Euro> response) {
                if (!response.isSuccessful()) {
                    mView.onFailureMessage(response.message());
                } else {
                    Euro euro = response.body();
                    assert euro != null;
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
            public void onFailure(@NotNull Call<Euro> call, @NotNull Throwable t) {
                mView.onFailureMessage(t.getMessage());
            }
        });
    }

    @Override
    public void handleBitCoinCall(EditText et_btc, TextView pct_BitCoin, ImageView bitCoin_arrow) {
        Call<BitCoin> bitCoinCall = new RetrofitConfig().getServiceConfig().buscarBitCoin();
        bitCoinCall.enqueue(new Callback<BitCoin>() {
            @SuppressLint("SetTextI18n, DefaultLocale")
            @Override
            public void onResponse(@NotNull Call<BitCoin> call, @NotNull Response<BitCoin> response) {
                if (!response.isSuccessful()) {
                    mView.onFailureMessage(response.message());
                } else {
                    BitCoin bitCoin = response.body();
                    assert bitCoin != null;
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
            public void onFailure(@NotNull Call<BitCoin> call, @NotNull Throwable t) {
                mView.onFailureMessage(t.getMessage());
            }
        });
    }
}
