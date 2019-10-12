package com.example.foodapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class foodItemAdapter extends BaseAdapter {

    LayoutInflater myInflator;
    Map<String, String> map;
    List<String> foodNames;
    List<String> expirationDates;

    public foodItemAdapter(Context c, Map m){
        myInflator = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        map = m;
        foodNames = new ArrayList<String>(map.keySet());
        expirationDates = new ArrayList<String>(map.values());

    }
    @Override
    public int getCount() {
        return map.size();
    }

    @Override
    public Object getItem(int position) {
        return foodNames.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = myInflator.inflate(R.layout.list_of_food_layout, null);
        TextView foodNameTextView = (TextView) v.findViewById(R.id.foodNameTextView);
        TextView expirationDateTextView = (TextView) v.findViewById(R.id.expirationDateTextView);

        //Will set the foodNames and expirationDates Values onto the textviews
        foodNameTextView.setText(foodNames.get(position));
        expirationDateTextView.setText(expirationDates.get(position));

        return v;
    }
}
