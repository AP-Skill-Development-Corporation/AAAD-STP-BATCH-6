package com.example.examplepay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.EdgeEffectCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements PaymentResultListener {
    EditText name,amount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = findViewById(R.id.name);
        amount = findViewById(R.id.amount);
        Checkout.preload(this);
    }

    public void pay(View view) {
        Checkout ch = new Checkout();
        ch.setKeyID("rzp_test_l7dZQCgdAOpoag");
        try {
            JSONObject obj = new JSONObject();
            obj.put("name",name.getText().toString());
            obj.put("currency","INR");
            obj.put("amount",amount.getText().toString());
            ch.open(this,obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this, "Payment Successful "+s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, ""+s, Toast.LENGTH_SHORT).show();
    }
}