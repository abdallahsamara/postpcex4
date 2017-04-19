package com.example.abdallahsamara.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomListAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private ArrayList<String> mArray;

    public CustomListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mArray = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return mArray.size();
    }

    @Override
    public String getItem(int i) {
        return mArray.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void addItem(String value) {
        mArray.add(value);
        notifyDataSetChanged();
    }

    public void removeItemAt(int position) {
        mArray.remove(position);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            v = mInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        TextView textView = (TextView) v.findViewById(android.R.id.text1);
        textView.setText(getItem(position));
        textView.setTextColor(position % 2 == 0 ? Color.RED : Color.BLUE);
        return v;
    }
}
