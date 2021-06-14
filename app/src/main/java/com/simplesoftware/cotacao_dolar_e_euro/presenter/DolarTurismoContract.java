package com.simplesoftware.cotacao_dolar_e_euro.presenter;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

public interface DolarTurismoContract {

    interface MvpView{

        void onFailureMessage(String message);
        void onSuccessMessage(String message);
        void handleDataFromPresenter();
        void handleDate();
        void handleClicks();
        void loadAds();
    }

    interface Presenter{
        void handleDataPassed(TextView tv_high, TextView tv_low, TextView tv_varBid, TextView tv_pctChange,
                              TextView tv_bid, TextView tv_ask, TextView tv_titulo, String dataFormatada, Context context, LinearLayout layoutCopy);

        void handleCopy(String copiarCotacao, String dataFormatada, Context context);
    }

}
