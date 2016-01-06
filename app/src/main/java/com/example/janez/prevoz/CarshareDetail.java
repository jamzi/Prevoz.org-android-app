package com.example.janez.prevoz;

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

import org.w3c.dom.Text;

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

        NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.notification_icon)
                        .setContentTitle("Obvestilo o prevozu")
                        .setContentText(carshare.from + "->" + carshare.to + "\n" + carshare.date);

        Intent resultIntent = new Intent(this, CarshareDetail.class);
        resultIntent.putExtra("carshare", carshare);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(CarshareDetail.class);

        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        int mId = 1;
        mNotificationManager.notify(mId, mBuilder.build());
    }
}
