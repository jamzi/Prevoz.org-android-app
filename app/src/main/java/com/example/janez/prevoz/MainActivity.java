package com.example.janez.prevoz;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.janez.prevoz.Data.CarshareSearchData;
import com.example.janez.prevoz.Data.DatabaseConnector;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private CursorAdapter contactAdapter;
    private EditText etFromCity, etToCity;
    private Button btnSearchCarshare;
    private DatePicker dpDate;
    private TextView tvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //zadnja iskanja
        new getCarsharesTask().execute();

        btnSearchCarshare = (Button)findViewById(R.id.btnSearchCarshare);
        btnSearchCarshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etFromCity = (EditText)findViewById(R.id.etFromCity);
                etToCity = (EditText)findViewById(R.id.etToCity);

                String fromCity = etFromCity.getText().toString();
                String toCity = etToCity.getText().toString();

                tvInfo = (TextView)findViewById(R.id.tvInfoSearchCarshare);
                dpDate = (DatePicker)findViewById(R.id.datePicker);

                int year = dpDate.getYear();
                int month = dpDate.getMonth();
                int day = dpDate.getDayOfMonth();

                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day);

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String strDate = format.format(calendar.getTime());

                if (fromCity.equals("")|| toCity.equals("")){
                    tvInfo.setText("Manjkajoči podatki o prevozu!");
                }
                else {
                    tvInfo.setText("");
                    CarshareSearchData data = new CarshareSearchData(fromCity,toCity,strDate);

                    //dodamo v SQLite bazo za pregled zadnjih iskanj
                    DatabaseConnector databaseConnector = new DatabaseConnector(getApplicationContext());
                    databaseConnector.insertCarshare(data.fromCity, data.toCity,data.date);

                    Intent intent = new Intent(MainActivity.this, CarshareMain.class);
                    intent.putExtra("search_data", data);
                    startActivity(intent);
                }
            }
        });

    }

    private class getCarsharesTask extends AsyncTask<Object, Object, Cursor> {
        DatabaseConnector databaseConnector = new DatabaseConnector(getApplicationContext());

        // perform the database access
        @Override
        protected Cursor doInBackground(Object... params) {
            databaseConnector.open();
            return databaseConnector.getAllCarshares();
        }

        // use the Cursor returned from the doInBackground method
        @Override
        protected void onPostExecute(Cursor result) {
            databaseConnector.close();
        }
    }

}

