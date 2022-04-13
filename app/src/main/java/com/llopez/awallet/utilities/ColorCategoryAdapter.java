package com.llopez.awallet.utilities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.llopez.awallet.R;

import java.util.ArrayList;

public class ColorCategoryAdapter extends ArrayAdapter<Color> {
    public ColorCategoryAdapter(Context context, ArrayList<Color> colorList) {
        super(context, 0, colorList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.color_spinner_row, parent, false);
        }

        View colorView = convertView.findViewById(R.id.viewSpinnerColor);
        TextView colorTextView = convertView.findViewById(R.id.textViewSpinnerColor);

        Color currentItem = getItem(position);

        if (currentItem != null) {
            colorView.setBackgroundResource(currentItem.color);
            colorTextView.setText(currentItem.name);
        }

        return convertView;
    }
}
