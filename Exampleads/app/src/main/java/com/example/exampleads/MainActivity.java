package com.example.exampleads;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class MainActivity extends AppCompatActivity {
    AdView ad;
    InterstitialAd iad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {

            }
        });
        ad = findViewById(R.id.ad);
        AdRequest adRequest = new AdRequest.Builder().build();
        ad.loadAd(adRequest);

        iad = new InterstitialAd(this);
        iad.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        iad.loadAd(new AdRequest.Builder().build());
    }

    public void showad(View view) {
        if (iad.isLoaded()){
            iad.show();
        }else{
            Toast.makeText(this, "No ad to display", Toast.LENGTH_SHORT).show();
        }
    }
}