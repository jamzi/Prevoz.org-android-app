package com.example.janez.prevoz;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.janez.prevoz.Data.Carshare;

public class CarshareDetail extends AppCompatActivity {

    private TextView tvCarshareDetailFrom;
    private TextView tvCarshareDetailTo;
    private TextView tvCarshareDetailDate;
    private TextView tvCarshareDetailPrice;
    private TextView tvCarshareDetailPeople;
    private TextView tvCarshareDetailInsurance;
    private TextView tvCarshareDetailComment;

    private Carshare carshare;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carshare_detail);

        carshare = (Carshare) getIntent().getSerializableExtra("carshare");

        tvCarshareDetailFrom = (TextView)findViewById(R.id.tvCarshareDetailFrom);
        tvCarshareDetailFrom.setText(carshare.from);

        tvCarshareDetailTo = (TextView)findViewById(R.id.tvCarshareDetailTo);
        tvCarshareDetailTo.setText(carshare.to);

        tvCarshareDetailDate = (TextView)findViewById(R.id.tvCarshareDetailDate);
        tvCarshareDetailDate.setText(carshare.date);

        if (carshare.price != null) {
            tvCarshareDetailPrice = (TextView) findViewById(R.id.tvCarshareDetailPrice);
            tvCarshareDetailPrice.setText(Integer.toString(carshare.price) + " €");
        }

        if (carshare.numPeople != null) {
            tvCarshareDetailPeople = (TextView) findViewById(R.id.tvCarshareDetailPeople);
            tvCarshareDetailPeople.setText("Št.oseb: " + Integer.toString(carshare.numPeople));
        }

        if (carshare.insured != null) {
            tvCarshareDetailInsurance = (TextView) findViewById(R.id.tvCarshareDetailInsurance);
            if (carshare.insured.equals("true")){
                tvCarshareDetailInsurance.setText("Ima zavarovanje");
            }
            else{
                tvCarshareDetailInsurance.setText("Nima zavarovanja");
            }
        }

        if (carshare.comment != null) {
            tvCarshareDetailComment = (TextView) findViewById(R.id.tvCarshareDetailComment);
            tvCarshareDetailComment.setMovementMethod(new ScrollingMovementMethod());
            if (carshare.author != null || carshare.author.equals(""))
                tvCarshareDetailComment.setText(carshare.comment + "\n Avtor: " + carshare.author);
        }

        Button btnCarshareDetailSMS = (Button)findViewById(R.id.btnCarshareDetailSMS);
        btnCarshareDetailSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", carshare.contact, null)));
            }
        });

        Button btnCarshareDetailCall = (Button)findViewById(R.id.btnCarshareDetailCall);
        btnCarshareDetailCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + carshare.contact)));
            }
        });

    }
    private void notification() {

        NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.notification_icon)
                        .setContentTitle("Prevoz - obvestilo")
                        .setContentText(carshare.from + " -> " + carshare.to + " (" + carshare.date + " )");

        Intent resultIntent = new Intent(this, CarshareDetail.class);
        resultIntent.putExtra("carshare", carshare);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(CarshareDetail.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, mBuilder.build());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.carshareNotification) {
            notification();
        }
        return super.onOptionsItemSelected(item);
    }

}
