package com.example.janez.prevoz.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.janez.prevoz.Data.Carshare;
import com.example.janez.prevoz.Data.CarshareSearchData;
import com.example.janez.prevoz.R;

import java.util.List;


public class CarshareSearchAdapter extends ArrayAdapter<CarshareSearchData> {
    public CarshareSearchAdapter(Context context, List<CarshareSearchData> carshares) {
        super(context, 0, carshares);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CarshareSearchData carshare_search = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.adapter_search_listview_row, parent, false);
        }
        TextView tvFromCity = (TextView) convertView.findViewById(R.id.tvCarshareSearchFromCity);
        TextView tvToCity = (TextView) convertView.findViewById(R.id.tvCarshareSearchToCity);
        TextView tvDate = (TextView) convertView.findViewById(R.id.tvCarshareSearchDate);

        tvFromCity.setText(carshare_search.fromCity);
        tvToCity.setText(carshare_search.toCity);
        tvDate.setText(carshare_search.date);

        return convertView;
    }
}
