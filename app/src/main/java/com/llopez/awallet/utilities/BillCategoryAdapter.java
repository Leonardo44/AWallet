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
import com.llopez.awallet.model.BillCategory;
import com.llopez.awallet.model.EarningCategory;

import java.util.ArrayList;

public class BillCategoryAdapter  extends ArrayAdapter<BillCategory>  {
    public BillCategoryAdapter(Context context, ArrayList<BillCategory> colorList) {
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.category_spinner_row, parent, false);
        }

        View colorView = convertView.findViewById(R.id.viewSpinnerCategoryColor);
        TextView colorTextView = convertView.findViewById(R.id.textViewSpinnerCategoryName);

        BillCategory currentItem = getItem(position);

        if (currentItem != null) {
            colorView. setBackgroundColor(android.graphics.Color.parseColor(currentItem.getColor()));
            colorTextView.setText(currentItem.getName());
        }

        return convertView;
    }
}
