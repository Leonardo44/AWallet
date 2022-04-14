package com.llopez.awallet.ui.add_earning;

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
import com.llopez.awallet.ui.add_bill.AddBillViewModel;
import com.llopez.awallet.ui.add_bill.AddBillViewModelFactory;
import com.llopez.awallet.utilities.BillCategoryAdapter;
import com.llopez.awallet.utilities.EarningCategoryAdapter;
import com.llopez.awallet.utilities.GetDataStatus;
import com.llopez.awallet.utilities.SendDataStatus;
import com.llopez.awallet.utilities.Validations;

import com.llopez.awallet.R;

import java.util.concurrent.atomic.DoubleAdder;

public class AddEarningFragment extends Fragment {
    private EditText editTextEarningName, editTextEarningDescription, editTextEarningPrice;
    private Button btnAddEarning;
    private Spinner categorySpinner;

    private AddEarningViewModel viewModel;

    public AddEarningFragment() { }

    public void createOrUpdateEarning(){
        String earningName = editTextEarningName.getText().toString();
        String earningDescription = editTextEarningDescription.getText().toString();
        String earningPrice = editTextEarningPrice.getText().toString();

        if(!(Validations.IsValidString(earningName))){
            Toast.makeText(getActivity(), R.string.fragment_add_earning_name_error, Toast.LENGTH_LONG).show();
        }else{
            if(!(Validations.IsValidString(earningPrice)) || !(Validations.IsNumeric(earningPrice)) || !(Validations.IsNumberGreaterThan(earningPrice, 0))){
                Toast.makeText(getActivity(), R.string.fragment_add_earning_price_error, Toast.LENGTH_LONG).show();
            }else{
                
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, new AddEarningViewModelFactory(FirebaseAuth.getInstance().getCurrentUser(), FirebaseFirestore.getInstance())).get(AddEarningViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_add_earning, container, false);

        editTextEarningName = layout.findViewById(R.id.editTextEarningName);
        editTextEarningDescription = layout.findViewById(R.id.editTextEarningDescription);
        editTextEarningPrice = layout.findViewById(R.id.editTextEarningPrice);
        btnAddEarning = layout.findViewById(R.id.btnAddEarning);
        categorySpinner = layout.findViewById(R.id.spinnerAddEarning);

        btnAddEarning.setOnClickListener(v -> {
            createOrUpdateEarning();
        });

        final Observer<GetDataStatus> getDataStatusObserver = newValue -> {
            switch (newValue) {
                case INITIALIZE:
                    break;
                case LOADING:
                    btnAddEarning.setEnabled(false);
                case ERROR:
                    btnAddEarning.setEnabled(true);
                case SUCCESS:
                    if (!viewModel.categorieList.isEmpty()) {
                        EarningCategoryAdapter adapter = new EarningCategoryAdapter(getContext(), viewModel.categorieList);
                        categorySpinner.setAdapter(adapter);
                        btnAddEarning.setEnabled(true);
                    }
                case NOT_INTERNET:
                    btnAddEarning.setEnabled(true);
            }
        };
        final Observer<SendDataStatus> sendDataStatusObserver = newValue -> {
            switch (newValue) {
                case INITIALIZE:
                    break;
                case LOADING:
                    btnAddEarning.setEnabled(false);
                case ERROR:
                    btnAddEarning.setEnabled(true);
                case SUCCESS:
                    btnAddEarning.setEnabled(true);
                case NOT_INTERNET:
                    btnAddEarning.setEnabled(true);
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