package com.example.janez.prevoz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.janez.prevoz.Data.Carshare;
import com.example.janez.prevoz.Data.CarshareSearchData;

public class CarshareDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carshare_detail);

        Carshare carshare = (Carshare) getIntent().getSerializableExtra("carshare");


    }
}
