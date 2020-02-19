package com.example.logindemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/*public class CustomAdapter extends ArrayAdapter<CustomItems> {
    public CustomAdapter(Context context, ArrayList<CustomItems> list) {
        super(context, 0, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return customView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return customView(position, convertView, parent);
    }

    public View customView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        if (convertView==null)
        {
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.spinner_item,parent,false);
        }
        CustomItems items= getItem(position);
        ImageView spinnerImage=convertView.findViewById(R.id.iconphoto);
        TextView spinnerName= convertView.findViewById(R.id.txt);
        if(items!=null)
        {
            spinnerImage.setImageResource(items.getSpinnerImage());
            spinnerName.setText(items.getSpinnerText());
        }
        return convertView;
    }
}*/
