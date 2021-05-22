package com.simplesoftware.cotacao_dolar_e_euro.conversor;

import androidx.appcompat.app.AppCompatActivity;

import com.simplesoftware.cotacao_dolar_e_euro.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Conversor extends AppCompatActivity {

    private EditText et_conversor1, et_conversor2;
    private ImageView img_inverterConversor;
    private TextView tv_otherCoin;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversor);

        WindowManager.LayoutParams wlp = getWindow().getAttributes();
        wlp.dimAmount = 0;
        wlp.flags = WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;
        getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        getWindow().setAttributes(wlp);

        instanciarComponentes();

        SharedPreferences spGetTitle = getSharedPreferences("getString", Context.MODE_PRIVATE);
        String title = spGetTitle.getString("title", "");

        if (title.equals("Dólar Comercial")){
            tv_otherCoin.setText("USD");
        } else if (title.equals("Dólar Turismo")){
            tv_otherCoin.setText("USDT");
        } else if (title.equals("Euro")){
            tv_otherCoin.setText("EUR");
        } else {
            tv_otherCoin.setText("BTC");
        }


        img_inverterConversor.setImageResource(R.drawable.ic_arrow);
        et_conversor2.setEnabled(false);
        et_conversor1.requestFocus();




    }

    public void instanciarComponentes() {
        et_conversor1 = findViewById(R.id.et_conversor1);
        et_conversor2 = findViewById(R.id.et_conversor2);
        img_inverterConversor = findViewById(R.id.img_inverterConversor);
        tv_otherCoin = findViewById(R.id.tv_otherCoin);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void inverterSeta(View v) {
        if (img_inverterConversor.getDrawable().getConstantState().equals(img_inverterConversor.getContext()
                .getDrawable(R.drawable.ic_arrow).getConstantState())) {
            img_inverterConversor.setImageResource(R.drawable.ic_arrow_left);
            et_conversor1.setEnabled(false);
            et_conversor2.setEnabled(true);
            et_conversor2.requestFocus();
        } else {
            img_inverterConversor.setImageResource(R.drawable.ic_arrow);
            et_conversor2.setEnabled(false);
            et_conversor1.setEnabled(true);
            et_conversor1.requestFocus();
        }
        et_conversor1.setText("");
        et_conversor2.setText("");
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "DefaultLocale"})
    public void converter(View v) {

        try {
            SharedPreferences spGetString = getSharedPreferences("getString", Context.MODE_PRIVATE);

            if (img_inverterConversor.getDrawable().getConstantState().equals(img_inverterConversor.getContext()
                    .getDrawable(R.drawable.ic_arrow).getConstantState())) {
                String sValorDolar = String.valueOf(spGetString.getString("ask", ""));
                double dValorDolar = Double.parseDouble(sValorDolar);
                String sValorReal = String.valueOf(et_conversor1.getText());
                double dValorReal = Double.parseDouble(sValorReal);
                double total = dValorReal / dValorDolar;
                et_conversor2.setText(String.format("%.2f", total).replace(".", ","));
            } else {
                String sValorDolar = String.valueOf(spGetString.getString("ask", ""));
                double dValorDolar = Double.parseDouble(sValorDolar);
                String sValorDigitado = String.valueOf(et_conversor2.getText());
                double dValorDigitado = Double.parseDouble(sValorDigitado);
                double total = dValorDigitado * dValorDolar;
                et_conversor1.setText(String.format("%.2f", total).replace(".", ","));
            }
        } catch (Exception e) {
            Toast.makeText(this, "Erro: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void fechar(View v) {
        super.onBackPressed();
    }

}