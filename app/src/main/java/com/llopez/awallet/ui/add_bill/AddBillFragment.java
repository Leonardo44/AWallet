package com.llopez.awallet.ui.add_bill;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.llopez.awallet.model.BillCategory;
import com.llopez.awallet.model.BillObject;
import com.llopez.awallet.model.EarningCategory;
import com.llopez.awallet.utilities.BillCategoryAdapter;
import com.llopez.awallet.utilities.GetDataStatus;
import com.llopez.awallet.utilities.SendDataStatus;
import com.llopez.awallet.utilities.Validations;

import com.llopez.awallet.R;

public class AddBillFragment extends Fragment {
    private EditText editTextBillName, editTextBillDescription, editTextBillPrice;
    private Button btnAddBill;
    private Spinner categorySpinner;

    private AddBillViewModel viewModel;
    private BillCategory categorySelected;

    private String categoryName;
    private String billDocumentName;

    public AddBillFragment() { }

    public void createBill(){
        String billName = editTextBillName.getText().toString();
        String billPrice = editTextBillPrice.getText().toString();
        String billDescription = editTextBillDescription.getText().toString();

        if(!(Validations.IsValidString(billName))){
            Toast.makeText(getActivity(), R.string.fragment_add_bill_title_error, Toast.LENGTH_LONG).show();
        }else{
            try {
                Double billAmount = Double.parseDouble(billPrice);

                if (billAmount > 0) {
                    viewModel.createBill(categorySelected, billName, billAmount, billDescription);
                } else {
                    Toast.makeText(getActivity(), R.string.fragment_add_earning_price_error, Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Toast.makeText(getActivity(), R.string.fragment_add_earning_price_error, Toast.LENGTH_LONG).show();
            }
        }
    }

    public void updateBill(){
        String billName = editTextBillName.getText().toString();
        String billPrice = editTextBillPrice.getText().toString();
        String billDescription = editTextBillDescription.getText().toString();

        if(!(Validations.IsValidString(billName))){
            Toast.makeText(getActivity(), R.string.fragment_add_bill_title_error, Toast.LENGTH_LONG).show();
        }else{
            try {
                Double billAmount = Double.parseDouble(billPrice);

                if (billAmount > 0) {
                    viewModel.updateBill(categorySelected, billDocumentName, billName, billAmount, billDescription);
                } else {
                    Toast.makeText(getActivity(), R.string.fragment_add_earning_price_error, Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Toast.makeText(getActivity(), R.string.fragment_add_earning_price_error, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, new AddBillViewModelFactory(FirebaseAuth.getInstance().getCurrentUser(), FirebaseFirestore.getInstance())).get(AddBillViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_add_bill, container, false);
        boolean isEdit = getArguments().getBoolean("is_edit");

        editTextBillName = layout.findViewById(R.id.editTextBillName);
        editTextBillDescription = layout.findViewById(R.id.editTextBillDescription);
        editTextBillPrice = layout.findViewById(R.id.editTextBillPrice);
        btnAddBill = layout.findViewById(R.id.btnAddBill);
        categorySpinner = layout.findViewById(R.id.spinnerAddBill);

        btnAddBill.setOnClickListener(v -> {
            if (isEdit) {
                updateBill();
            } else {
                createBill();
            }
        });

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                BillCategory category = (BillCategory) parent.getItemAtPosition(position);
                categorySelected = category;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        categorySpinner.setEnabled(true);
        if (isEdit) {
            categoryName = getArguments().getString("category_name");
            billDocumentName = getArguments().getString("bill_document_name");
            Double amount = getArguments().getDouble("bill_amount");

            editTextBillName.setText(getArguments().getString("bill_name"));
            editTextBillPrice.setText(amount.toString());
            editTextBillDescription.setText(getArguments().getString("bill_description"));
            categorySpinner.setEnabled(false);
        }

        final Observer<GetDataStatus> getDataStatusObserver = newValue -> {
            switch (newValue) {
                case INITIALIZE:
                    break;
                case LOADING:
                    btnAddBill.setEnabled(false);
                case ERROR:
                    btnAddBill.setEnabled(true);
                case SUCCESS:
                    if (!viewModel.categorieList.isEmpty()) {
                        BillCategoryAdapter adapter = new BillCategoryAdapter(getContext(), viewModel.categorieList);
                        categorySpinner.setAdapter(adapter);

                        if (categoryName != null) {
                            BillCategory _category = viewModel.categorieList.stream()
                                    .filter(e -> categoryName.equals(e.getName()))
                                    .findAny()
                                    .orElse(null);

                            categorySpinner.setSelection(viewModel.categorieList.indexOf(_category));
                        }

                        btnAddBill.setEnabled(true);
                    }
                case NOT_INTERNET:
                    btnAddBill.setEnabled(true);
            }
        };
        final Observer<SendDataStatus> sendDataStatusObserver = newValue -> {
            switch (newValue) {
                case INITIALIZE:
                    break;
                case LOADING:
                    btnAddBill.setEnabled(false);
                case ERROR:
                    btnAddBill.setEnabled(true);
                case SUCCESS:
                    successCreateToast();
                    btnAddBill.setEnabled(true);
                case NOT_INTERNET:
                    btnAddBill.setEnabled(true);
            }
        };

        viewModel.getCurrentDataStatus().observe(this.getActivity(), getDataStatusObserver);
        viewModel.getSendCurrentDataStatus().observe(this.getActivity(), sendDataStatusObserver);

        return layout;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.loadData();
    }

    private void successCreateToast() {
        Toast.makeText(getActivity(), R.string.success_add_data_message, Toast.LENGTH_LONG).show();
        NavHostFragment.findNavController(this).navigate(R.id.listBillsFragment);
    }
}
