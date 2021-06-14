package com.simplesoftware.cotacao_dolar_e_euro.presenter;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public interface MainActivityContract {

    interface MvpView{

        void handleClicks();

        void onFailureMessage(String message);

        void handleData();

    }

    interface Presenter {

        void handleDolarComercialCall(EditText et_dolar, TextView pct_dolar, ImageView dolar_arrow);

        void handleDolarTurismoCall(EditText et_dolarTurismo, TextView pct_dolarTurismo, ImageView dolarTurismo_arrow);

        void handleEuroCall(EditText et_euro, TextView pct_euro, ImageView euro_arrow);

        void handleBitCoinCall(EditText et_btc, TextView pct_BitCoin, ImageView bitCoin_arrow);

    }
}
