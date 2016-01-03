package com.example.janez.prevoz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.janez.prevoz.Data.CarshareSearchData;

public class MainActivity extends AppCompatActivity {

    EditText etFromCity, etToCity;
    Button btnSearchCarshare;
    DatePicker dpDate;
    TextView tvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSearchCarshare = (Button)findViewById(R.id.btnSearchCarshare);
        btnSearchCarshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etFromCity = (EditText)findViewById(R.id.etFromCity);
                etToCity = (EditText)findViewById(R.id.etFromCity);
                tvInfo = (TextView)findViewById(R.id.tvInfoSearchCarshare);

                CarshareSearchData data = new CarshareSearchData(etFromCity.getText().toString(),
                                                                 etToCity.getText().toString(),
                                                                 "2016-01-03");
                dpDate = (DatePicker)findViewById(R.id.datePicker);


               /* if (fromCity.equals("")|| toCity.equals("")){
                    tvInfo.setText("Manjkajoči podatki o prevozu!");
                }
                else {*/
                    Intent intent = new Intent(MainActivity.this, CarshareMain.class);
                    intent.putExtra("search_data", data);
                    startActivity(intent);

            }
        });

    }
}
