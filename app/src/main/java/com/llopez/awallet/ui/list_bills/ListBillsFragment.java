package com.llopez.awallet.ui.list_bills;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.llopez.awallet.R;

public class ListBillsFragment extends Fragment {
    private RecyclerView rvBills;
    private FloatingActionButton btnAdd;
    private BillsAdapter billsAdapter;

    public ListBillsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_list_bills, container, false);

        rvBills = layout.findViewById(R.id.rvBills);
        btnAdd = layout.findViewById(R.id.floatingActionButton);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToAddBillFragment();
            }
        });

        rvBills.addItemDecoration(new DividerItemDecoration(rvBills.getContext(), DividerItemDecoration.VERTICAL));
        return layout;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Integer[] dataSet = new Integer[10];
        dataSet[0] = 0;
        dataSet[1] = 1;
        dataSet[2] = 2;
        dataSet[3] = 3;
        dataSet[4] = 4;
        dataSet[5] = 5;
        dataSet[6] = 6;
        dataSet[7] = 7;
        dataSet[8] = 8;
        dataSet[9] = 9;

        rvBills.setLayoutManager(new LinearLayoutManager(getContext()));

        billsAdapter = new BillsAdapter(dataSet);
        rvBills.setAdapter(billsAdapter);
    }

    private void moveToAddBillFragment() {
        NavHostFragment.findNavController(this).navigate(R.id.action_listBillsFragment_to_addBillFragment);
    }
}

