package com.example.janez.prevoz;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.janez.prevoz.Data.Carshare;
import com.example.janez.prevoz.Data.CarshareSearchData;
import com.example.janez.prevoz.Data.PrevozResponse;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class CarshareMain extends AppCompatActivity {

    PrevozResponse carshares;
    ListView lvCarshares;
    TextView tvCarshareMainFromTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carshare_main);

        tvCarshareMainFromTo = (TextView)findViewById(R.id.tvCarshareMainFromTo);

        CarshareSearchData search_data = (CarshareSearchData) getIntent().getSerializableExtra("search_data");

        tvCarshareMainFromTo.setText(search_data.fromCity + " -> " + search_data.toCity);

        String url = "https://prevoz.org/api/search/shares/?f=" + search_data.fromCity +
                "&t=" + search_data.toCity + "&d=" + search_data.date;
        new JSONTaskGet().execute(url);

        lvCarshares = (ListView) findViewById(R.id.lvCarshares);
        lvCarshares.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CarshareMain.this, CarshareDetail.class);
                Carshare cs = (Carshare) parent.getItemAtPosition(position);
                intent.putExtra("carshare", cs);
                startActivity(intent);
            }
        });
    }

    public class JSONTaskGet extends AsyncTask<String, Void, PrevozResponse> {

        @Override
        protected PrevozResponse doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                reader = new BufferedReader(
                        new InputStreamReader(connection.getInputStream()));

                String line = "";
                StringBuffer buffer = new StringBuffer();
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                String finalJson = buffer.toString();

                final Gson gson = new Gson();
                carshares = gson.fromJson(finalJson, PrevozResponse.class);

                return carshares;

            } catch (IOException e) {
                Log.w("", "Exception: " + e.getLocalizedMessage());
                return null;
            } finally {
                if (connection != null) connection.disconnect();
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                    }
                }
            }
        }


        @Override
        protected void onPostExecute(PrevozResponse result) {
            super.onPostExecute(result);

            if (result.carshareList.size() == 0){
                tvCarshareMainFromTo.setText("Za to relacijo ni prevoza");
            }
            else {
                List<Carshare> carshares = result.carshareList;
                lvCarshares = (ListView) findViewById(R.id.lvCarshares);
                CarshareAdapter adapter = new CarshareAdapter(CarshareMain.this,carshares);
                lvCarshares.setAdapter(adapter);
            }
        }
    }
}
