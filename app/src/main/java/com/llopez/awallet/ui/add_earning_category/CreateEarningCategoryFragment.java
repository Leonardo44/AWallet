package com.llopez.awallet.ui.add_earning_category;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.llopez.awallet.R;
import com.llopez.awallet.utilities.Color;
import com.llopez.awallet.utilities.ColorCategoryAdapter;
import com.llopez.awallet.utilities.Validations;
import com.llopez.awallet.utilities.SendDataStatus;

public class CreateEarningCategoryFragment extends Fragment {
    private EditText categoryName;
    private Spinner spinnerColor;
    private Button btnCreateCategory;

    private CreateEarningCategoryViewModel viewModel;

    private String hexColor;

    public CreateEarningCategoryFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, new CreateEarningCategoryViewModelFactory(FirebaseAuth.getInstance().getCurrentUser(), FirebaseFirestore.getInstance())).get(CreateEarningCategoryViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_create_earning_category, container, false);

        categoryName = layout.findViewById(R.id.editTextEarningCategoryName);
        spinnerColor = layout.findViewById(R.id.spinnerCategoryEarningColor);
        btnCreateCategory = layout.findViewById(R.id.btnCreateEarningCategory);

        btnCreateCategory.setOnClickListener(v -> {
            createOrUpdateEarningCategory();
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

    private void createOrUpdateEarningCategory() {
        String categoryNameText = categoryName.getText().toString();
        if(Validations.IsValidString(categoryNameText)){
            viewModel.createCategory(categoryNameText, hexColor);
        }else{
            Toast.makeText(getActivity(), R.string.fragment_add_earning_category_name_error, Toast.LENGTH_LONG).show();
        }
    }

    private void successCreateToast() {
        Toast.makeText(getActivity(), R.string.success_add_data_message, Toast.LENGTH_LONG).show();
        NavHostFragment.findNavController(this).navigate(R.id.earningCategoryListFragment);
    }
}