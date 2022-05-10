package com.llopez.awallet.ui.add_bill_category;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.llopez.awallet.R;
import com.llopez.awallet.utilities.Color;
import com.llopez.awallet.utilities.ColorCategoryAdapter;
import com.llopez.awallet.utilities.SendDataStatus;

import com.llopez.awallet.utilities.Validations;

public class CreateBillCategoryFragment extends Fragment {
    private EditText categoryName;
    private Spinner spinnerColor;
    private Button btnCreateCategory;

    private CreateBillCategoryViewModel viewModel;

    private String hexColor;
    private String colorSelected;

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
        boolean isEdit = getArguments().getBoolean("is_edit");

        categoryName = layout.findViewById(R.id.editTextBillCategoryName);
        spinnerColor = layout.findViewById(R.id.spinnerBillCategoryColor);
        btnCreateCategory = layout.findViewById(R.id.btnCreateBillCategory);

        btnCreateCategory.setOnClickListener(v -> {
            if (isEdit) {
                updateBillCategory();
            } else {
                createBillCategory();
            }
        });

        ColorCategoryAdapter adapter = new ColorCategoryAdapter(getContext(), Color.CategoryColorList);
        spinnerColor.setAdapter(adapter);

        spinnerColor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Color colorItem = (Color) parent.getItemAtPosition(position);
                hexColor = getResources().getString(colorItem.color);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        if (isEdit) {
            categoryName.setText(getArguments().getString("category_name"));
            colorSelected = getArguments().getString("category_color");
        }

        if (colorSelected != null) {
            Color _color = Color.CategoryColorList.stream()
                    .filter(e -> colorSelected.equals(getResources().getString(e.color)))
                    .findAny()
                    .orElse(null);
            hexColor = getResources().getString(_color.color);

            spinnerColor.setSelection(Color.CategoryColorList.indexOf(_color));
        }

        final Observer<SendDataStatus> dataStatusObserver = newValue -> {
            switch (newValue) {
                case INITIALIZE:
                    break;
                case LOADING:
                    btnCreateCategory.setEnabled(false);
                case ERROR:
                    btnCreateCategory.setEnabled(true);
                case SUCCESS:
                    successCreateToast();
                    btnCreateCategory.setEnabled(true);
                case NOT_INTERNET:
                    btnCreateCategory.setEnabled(true);
            }
        };
        viewModel.getCurrentDataStatus().observe(this.getActivity(), dataStatusObserver);

        return layout;
    }

    private void createBillCategory() {
        String categoryNameText = categoryName.getText().toString();
        if(Validations.IsValidString(categoryNameText)){
            viewModel.createCategory(categoryNameText, hexColor);
        }else{
            Toast.makeText(getActivity(), R.string.fragment_create_bill_category_name_error, Toast.LENGTH_LONG).show();
        }
    }

    private void updateBillCategory() {
        String categoryNameText = categoryName.getText().toString();
        if(Validations.IsValidString(categoryNameText)){
            viewModel.updateCategory(getArguments().getString("category_name"), categoryNameText, hexColor);
        }else{
            Toast.makeText(getActivity(), R.string.fragment_create_bill_category_name_error, Toast.LENGTH_LONG).show();
        }
    }

    private void successCreateToast() {
        Toast.makeText(getActivity(), R.string.success_add_data_message, Toast.LENGTH_LONG).show();
        NavHostFragment.findNavController(this).navigate(R.id.billCategoryListFragment);
    }
}