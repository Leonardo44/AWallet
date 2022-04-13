package com.llopez.awallet.ui.add_bill_category;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.llopez.awallet.R;
import com.llopez.awallet.utilities.Color;
import com.llopez.awallet.utilities.ColorCategoryAdapter;

public class CreateBillCategoryFragment extends Fragment {
    private EditText categoryName;
    private Spinner spinnerColor;
    private Button btnCreateCategory;

    public CreateBillCategoryFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_create_bill_category, container, false);

        categoryName = layout.findViewById(R.id.editTextBillCategoryName);
        spinnerColor = layout.findViewById(R.id.spinnerBillCategoryColor);
        btnCreateCategory = layout.findViewById(R.id.btnCreateBillCategory);

        btnCreateCategory.setOnClickListener(v -> {
            createBillCategory();
        });

        ColorCategoryAdapter adapter = new ColorCategoryAdapter(getContext(), Color.CategoryColorList);
        spinnerColor.setAdapter(adapter);

        return layout;
    }

    private void createBillCategory() {

    }
}