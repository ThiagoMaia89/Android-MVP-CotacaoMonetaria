package com.simplesoftware.cotacao_dolar_e_euro.presenter;

public class MainActivityPresenter implements MainActivityContract.Presenter {

    private MainActivityContract.MvpView mView;

    public MainActivityPresenter(MainActivityContract.MvpView mView){
        this.mView = mView;
    }

}
