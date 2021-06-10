package com.simplesoftware.cotacao_dolar_e_euro.presenter;

import android.view.View;

public interface MainActivityContract {

    interface MvpView{

        void irDolar(View view);

        void irDolarTurismo(View view);

        void irEuro(View view);

        void irBtc(View view);

        void onFailureMessage(String message);

    }

    interface Presenter {


    }
}
