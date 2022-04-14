package com.llopez.awallet.ui.add_bill;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
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

    public AddBillFragment() { }

    public void createOrUpdateBill(){
        String billName = editTextBillName.getText().toString();
        String billPrice = editTextBillPrice.getText().toString();
        String billDescription = editTextBillDescription.getText().toString();

        if(!(Validations.IsValidString(billName))){
            Toast.makeText(getActivity(), R.string.fragment_add_bill_title_error, Toast.LENGTH_LONG).show();
        }else{
            if(!(Validations.IsValidString(billPrice)) || !(Validations.IsNumeric(billPrice)) || !(Validations.IsNumberGreaterThan(billPrice, 0))){
                Toast.makeText(getActivity(), R.string.fragment_add_bill_price_error, Toast.LENGTH_LONG).show();
            } else{

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

        editTextBillName = layout.findViewById(R.id.editTextBillName);
        editTextBillDescription = layout.findViewById(R.id.editTextBillDescription);
        editTextBillPrice = layout.findViewById(R.id.editTextBillPrice);
        btnAddBill = layout.findViewById(R.id.btnAddBill);
        categorySpinner = layout.findViewById(R.id.spinnerAddBill);

        btnAddBill.setOnClickListener(v -> {
            createOrUpdateBill();
        });

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
}