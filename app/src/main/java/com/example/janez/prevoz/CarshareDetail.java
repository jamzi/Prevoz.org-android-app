package com.example.janez.prevoz;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.janez.prevoz.Data.Carshare;
import com.example.janez.prevoz.Data.CarshareSearchData;
import com.example.janez.prevoz.Notifications.MyReceiver;

import org.w3c.dom.Text;

import java.util.Calendar;

public class CarshareDetail extends AppCompatActivity {

    private TextView tvCarshareDetailFrom,tvCarshareDetailTo,
            tvCarshareDetailDate,tvCarshareDetailPrice,tvCarshareDetailPeople,
            tvCarshareDetailInsurance,tvCarshareDetailComment,tvCarshareDetailAuthor;

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
            tvCarshareDetailPrice.setText("Cena prevoza: " + Integer.toString(carshare.price) + " €");
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
            tvCarshareDetailComment.setText(carshare.comment);
        }

        if (carshare.author != null) {
            tvCarshareDetailAuthor = (TextView) findViewById(R.id.tvCarshareDetailAuthor);
            tvCarshareDetailAuthor.setText(carshare.author);
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


        Button btnCarshareDetailNotification = (Button)findViewById(R.id.btnCarshareDetailNotification);
        btnCarshareDetailNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notification();
            }
        });
    }
    private void notification() {

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.MONTH, 1);
        calendar.set(Calendar.YEAR, 2016);
        calendar.set(Calendar.DAY_OF_MONTH, 13);

        calendar.set(Calendar.HOUR_OF_DAY, 14);
        calendar.set(Calendar.MINUTE, 20);
        calendar.set(Calendar.SECOND, 10);
        calendar.set(Calendar.AM_PM,Calendar.PM);

        Intent myIntent = new Intent(CarshareDetail.this, MyReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, myIntent, 0);

        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC, calendar.getTimeInMillis(), pendingIntent);

        /*Intent intent = new Intent(CarshareDetail.this, CarshareDetail.class);
        intent.putExtra("carshare", carshare);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, 0);

        Notification notification = new NotificationCompat.Builder(this)
                .setCategory(Notification.CATEGORY_PROMO)
                .setContentTitle("Prevoz")
                .setContentText(carshare.from + "->" + carshare.to + ": " + carshare.date)
                .setSmallIcon(R.drawable.notification_icon)
                .setAutoCancel(true)
                .addAction(android.R.drawable.ic_menu_view, "Več o prevozu", contentIntent)
                .setContentIntent(contentIntent)
                .setPriority(Notification.PRIORITY_HIGH)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000}).build();
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);*/

    }
}
