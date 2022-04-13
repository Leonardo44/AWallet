package com.llopez.awallet.ui.add_bill_category;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.llopez.awallet.R;
import com.llopez.awallet.utilities.Color;
import com.llopez.awallet.utilities.ColorCategoryAdapter;
import com.llopez.awallet.utilities.SendDataStatus;

public class CreateBillCategoryFragment extends Fragment {
    private EditText categoryName;
    private Spinner spinnerColor;
    private Button btnCreateCategory;

    private CreateBillCategoryViewModel viewModel;

    private Integer indexColor;

    public CreateBillCategoryFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, new CreateBillCategoryViewModelFactory(FirebaseAuth.getInstance().getCurrentUser(), FirebaseFirestore.getInstance())).get(CreateBillCategoryViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_create_bill_category, container, false);

        categoryName = layout.findViewById(R.id.editTextBillCategoryName);
        spinnerColor = layout.findViewById(R.id.spinnerBillCategoryColor);
        btnCreateCategory = layout.findViewById(R.id.btnCreateBillCategory);

        btnCreateCategory.setOnClickListener(v -> {
            createOrUpdateBillCategory();
        });

        ColorCategoryAdapter adapter = new ColorCategoryAdapter(getContext(), Color.CategoryColorList);
        spinnerColor.setAdapter(adapter);

        spinnerColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Color colorItem = (Color) parent.getItemAtPosition(position);
                indexColor = colorItem.index;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        final Observer<SendDataStatus> dataStatusObserver = newValue -> {
            switch (newValue) {
                case INITIALIZE:
                    break;
                case LOADING:
                    btnCreateCategory.setEnabled(false);
                case ERROR:
                    btnCreateCategory.setEnabled(true);
                case SUCCESS:
                    btnCreateCategory.setEnabled(true);
                case NOT_INTERNET:
                    btnCreateCategory.setEnabled(true);
            }
        };
        viewModel.getCurrentDataStatus().observe(this.getActivity(), dataStatusObserver);

        return layout;
    }

    private void createOrUpdateBillCategory() {
        viewModel.createCategory(categoryName.getText().toString(), indexColor);
    }
}