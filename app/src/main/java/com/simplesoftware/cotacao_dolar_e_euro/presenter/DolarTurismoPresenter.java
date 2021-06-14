package com.simplesoftware.cotacao_dolar_e_euro.presenter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.simplesoftware.cotacao_dolar_e_euro.model.requests.Dolar;
import com.simplesoftware.cotacao_dolar_e_euro.model.requests.DolarTurismo;
import com.simplesoftware.cotacao_dolar_e_euro.model.util.RetrofitConfig;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DolarTurismoPresenter implements DolarTurismoContract.Presenter{

    DolarTurismoContract.MvpView mView;

    public DolarTurismoPresenter(DolarTurismoContract.MvpView mView){
        this.mView = mView;
    }

    @Override
    public void handleDataPassed(TextView tv_high, TextView tv_low, TextView tv_varBid, TextView tv_pctChange, TextView tv_bid, TextView tv_ask, TextView tv_titulo, String dataFormatada, Context context, LinearLayout layoutCopy) {
        Call<DolarTurismo> callDolarTurismo = new RetrofitConfig().getServiceConfig().buscarDolarTurismo();
        callDolarTurismo.enqueue(new Callback<DolarTurismo>() {
            @Override
            public void onResponse(@NotNull Call<DolarTurismo> call, @NotNull Response<DolarTurismo> response) {
                if (!response.isSuccessful()) {
                    mView.onFailureMessage(response.message());
                } else {
                    DolarTurismo dolarTurismo = response.body();
                    tv_high.setText(dolarTurismo.USDT.getHigh());
                    tv_low.setText(dolarTurismo.USDT.getLow());
                    tv_varBid.setText(dolarTurismo.USDT.getVarBid());
                    tv_pctChange.setText(dolarTurismo.USDT.getPctChange());
                    tv_bid.setText(dolarTurismo.USDT.getBid());
                    tv_ask.setText(dolarTurismo.USDT.getAsk());
                    String copiarCotacao = dolarTurismo.USDT.toString();
                    layoutCopy.setOnClickListener(v -> handleCopy(copiarCotacao, dataFormatada, context));

                    SharedPreferences spGetString = context.getSharedPreferences("getString", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = spGetString.edit();
                    editor.putString("ask", tv_ask.getText().toString());
                    editor.putString("title", tv_titulo.getText().toString());
                    editor.apply();

                }
            }

            @Override
            public void onFailure(@NotNull Call<DolarTurismo> call, @NotNull Throwable t) {
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
