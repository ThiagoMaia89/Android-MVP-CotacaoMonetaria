package com.simplesoftware.cotacao_dolar_e_euro.presenter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.simplesoftware.cotacao_dolar_e_euro.model.requests.Dolar;
import com.simplesoftware.cotacao_dolar_e_euro.model.util.RetrofitConfig;
import com.simplesoftware.cotacao_dolar_e_euro.view.DolarActivity;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DolarComercialPresenter implements DolarComercialContract.Presenter {

    DolarComercialContract.MvpView mView;

    public DolarComercialPresenter(DolarComercialContract.MvpView mView){
        this.mView = mView;
    }

    @Override
    public void handleDataPassed(TextView tv_high, TextView tv_low, TextView tv_varBid, TextView tv_pctChange, TextView tv_bid,
                                 TextView tv_ask, TextView tv_titulo, String dataFormatada, Context context, LinearLayout layoutCopy) {
        Call<Dolar> callDolar = new RetrofitConfig().getServiceConfig().buscarDolar();
        callDolar.enqueue(new Callback<Dolar>() {
            @Override
            public void onResponse(@NotNull Call<Dolar> call, @NotNull Response<Dolar> response) {
                if (!response.isSuccessful()) {
                    mView.onFailureMessage(response.message());
                } else {
                    Dolar dolar = response.body();
                    tv_high.setText(dolar.USD.getHigh());
                    tv_low.setText(dolar.USD.getLow());
                    tv_varBid.setText(dolar.USD.getVarBid());
                    tv_pctChange.setText(dolar.USD.getPctChange());
                    tv_bid.setText(dolar.USD.getBid());
                    tv_ask.setText(dolar.USD.getAsk());
                    String copiarCotacao = dolar.USD.toString();
                    layoutCopy.setOnClickListener(v -> handleCopy(copiarCotacao, dataFormatada, context));

                    SharedPreferences spGetString = context.getSharedPreferences("getString", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = spGetString.edit();
                    editor.putString("ask", tv_ask.getText().toString());
                    editor.putString("title", tv_titulo.getText().toString());
                    editor.apply();

                }
            }

            @Override
            public void onFailure(@NotNull Call<Dolar> call, @NotNull Throwable t) {
                mView.onFailureMessage(t.getMessage());
            }
        });

    }

    @Override
    public void handleCopy(String copiarCotacao, String dataFormatada, Context context) {

        try {
            mView.onSuccessMessage("Copiado para a área de transferência");

            ClipboardManager clipboard = (ClipboardManager)
                    context.getSystemService(Context.CLIPBOARD_SERVICE);

            ClipData cpy_all = ClipData.newPlainText("text", "Dólar Comercial:\n" + dataFormatada + "\n\n" + copiarCotacao);
            clipboard.setPrimaryClip(cpy_all);
        } catch (Exception e) {
            mView.onFailureMessage(e.getMessage());
        }
    }
}
