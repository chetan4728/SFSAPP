package com.ingenioussys.microfinance.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.ingenioussys.microfinance.R;

import com.ingenioussys.microfinance.model.CommanModel;

import java.util.ArrayList;

public class DropDownAdapter extends BaseAdapter {
    Context context;
    ArrayList<CommanModel> Arraylist;
    public static View selectedView = null;
    public DropDownAdapter(Context context, ArrayList<CommanModel> arraylist) {
        this.context = context;
        Arraylist = arraylist;
    }



    @Override
    public int getCount() {
        return Arraylist.size();
    }

    @Override
    public Object getItem(int position) {
        return Arraylist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (long)position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CommanModel object = Arraylist.get(position);

        if(convertView==null) {
            LayoutInflater view = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = view.inflate(R.layout.category_item, null);
        }

        selectedView = convertView;
        TextView txt = (TextView)convertView.findViewById(R.id.value);
        TextView id_value = (TextView)convertView.findViewById(R.id.id_value);

        txt.setPadding(10, 16, 0, 16);
        txt.setTextSize(16);
        txt.setGravity(Gravity.CENTER_VERTICAL);
        //txt.setText(object.getCountry_name());
        String cvale = String.valueOf(object.getKey());
        if (position == 0) {
            txt.setHint("Select");
        } else {
            txt.setText(object.getValue());
            id_value.setText(cvale);
        }

        txt.setTextColor(Color.parseColor("#000000"));

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        CommanModel object = Arraylist.get(position);

        if(convertView==null) {
            LayoutInflater view = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = view.inflate(R.layout.category_item, null);
        }

        selectedView = convertView;
        TextView txt = (TextView)convertView.findViewById(R.id.value);
        TextView id_value = (TextView)convertView.findViewById(R.id.id_value);

        txt.setPadding(10, 16, 0, 16);
        txt.setTextSize(16);
        txt.setGravity(Gravity.CENTER_VERTICAL);
        //txt.setText(object.getCountry_name());
        String cvale = String.valueOf(object.getKey());
        if (position == 0) {
            txt.setHint("Select");
        } else {
            txt.setText(object.getValue());
            id_value.setText(cvale);
        }

        txt.setTextColor(Color.parseColor("#000000"));

        return convertView;
    }
}
