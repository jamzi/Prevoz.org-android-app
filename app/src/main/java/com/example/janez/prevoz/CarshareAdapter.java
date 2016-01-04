package com.example.janez.prevoz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.janez.prevoz.Data.Carshare;

import java.util.List;


public class CarshareAdapter extends ArrayAdapter<Carshare> {
    public CarshareAdapter(Context context, List<Carshare> carshares) {
        super(context, 0, carshares);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Carshare carshare = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_listview_row, parent, false);
        }
        TextView tvHour = (TextView) convertView.findViewById(R.id.tvCarshareListHour);
        TextView tvPrice = (TextView) convertView.findViewById(R.id.tvCarshareListPrice);

        tvHour.setText(carshare.date);

        if(carshare.price != null)
            tvPrice.setText("Cena: " + Integer.toString(carshare.price) + " €");

        return convertView;
    }
}
