package com.example.janez.prevoz;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.janez.prevoz.Adapter.CarshareSearchAdapter;
import com.example.janez.prevoz.Data.CarshareSearchData;
import com.example.janez.prevoz.Data.DatabaseConnector;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private CursorAdapter contactAdapter;
    private EditText etFromCity, etToCity;
    private Button btnSearchCarshare;
    private DatePicker dpDate;
    private TextView tvInfo;

    private ListView lvLastSearch;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //zadnja iskanja
        new getCarsharesTask().execute();

        lvLastSearch = (ListView) findViewById(R.id.lvLastSearch);
        lvLastSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                CarshareSearchData data = (CarshareSearchData) parent.getItemAtPosition(position);

                Intent intent = new Intent(MainActivity.this, CarshareMain.class);
                intent.putExtra("search_data", data);
                startActivity(intent);
            }
        });

        btnSearchCarshare = (Button)findViewById(R.id.btnSearchCarshare);
        btnSearchCarshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                etFromCity = (EditText) findViewById(R.id.etFromCity);
                etToCity = (EditText) findViewById(R.id.etToCity);

                String fromCity = etFromCity.getText().toString();
                String toCity = etToCity.getText().toString();

                tvInfo = (TextView) findViewById(R.id.tvInfoSearchCarshare);
                dpDate = (DatePicker) findViewById(R.id.datePicker);

                int year = dpDate.getYear();
                int month = dpDate.getMonth();
                int day = dpDate.getDayOfMonth();

                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day);

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                String strDate = format.format(calendar.getTime());

                if (fromCity.equals("") || toCity.equals("")) {
                    tvInfo.setText("Manjkajoči podatki o prevozu!");
                } else {
                    tvInfo.setText("");
                    CarshareSearchData data = new CarshareSearchData(fromCity, toCity, strDate);

                    //dodamo v SQLite bazo za pregled zadnjih iskanj
                    DatabaseConnector databaseConnector = new DatabaseConnector(getApplicationContext());
                    databaseConnector.insertCarshare(data.fromCity, data.toCity, data.date);

                    Intent intent = new Intent(MainActivity.this, CarshareMain.class);
                    intent.putExtra("search_data", data);
                    startActivity(intent);
                }

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        //zadnja iskanja
        new getCarsharesTask().execute();
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
            lvLastSearch = (ListView) findViewById(R.id.lvLastSearch);
            ArrayList<CarshareSearchData> searches = new ArrayList<>();

            while(result.moveToNext()){
                searches.add(new CarshareSearchData(result.getString(1), result.getString(2), result.getString(3)));
            }
            if(searches.size() > 0){
                CarshareSearchAdapter adapter = new CarshareSearchAdapter(MainActivity.this,searches);
                lvLastSearch.setAdapter(adapter);
            }
            databaseConnector.close();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.clearDB) {
            new clearCarsharesTask().execute();
        }
        return super.onOptionsItemSelected(item);
    }



    private class clearCarsharesTask extends AsyncTask<Object, Void, String> {
        DatabaseConnector databaseConnector = new DatabaseConnector(getApplicationContext());

        // perform the database access
        @Override
        protected String doInBackground(Object... params) {
            databaseConnector.open();

            return databaseConnector.deleteAllCarshares();
        }

        @Override
        protected void onPostExecute(String result) {
            
            coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                    .coordinatorLayout);
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "Vnosi uspešno pobrisani", Snackbar.LENGTH_LONG);
            snackbar.show();;

            databaseConnector.close();

            lvLastSearch.setAdapter(null);
        }

    }


}

