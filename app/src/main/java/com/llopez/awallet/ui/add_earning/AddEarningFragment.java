package com.llopez.awallet.ui.add_earning;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.llopez.awallet.utilities.Validations;

import com.llopez.awallet.R;

public class AddEarningFragment extends Fragment {

    private EditText editTextEarningName, editTextEarningDescription, editTextEarningPrice;
    private Button btnAddEarning;

    public AddEarningFragment() { }

    public static AddEarningFragment newInstance(String param1, String param2) {
        AddEarningFragment fragment = new AddEarningFragment();
        return fragment;
    }

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_add_earning, container, false);

        editTextEarningName = layout.findViewById(R.id.editTextEarningName);
        editTextEarningDescription = layout.findViewById(R.id.editTextEarningDescription);
        editTextEarningPrice = layout.findViewById(R.id.editTextEarningPrice);
        btnAddEarning = layout.findViewById(R.id.btnAddEarning);

        btnAddEarning.setOnClickListener(v -> {
            createOrUpdateEarning();
        });

        return layout;
    }
}