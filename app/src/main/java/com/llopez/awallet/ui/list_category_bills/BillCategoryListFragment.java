package com.llopez.awallet.ui.list_category_bills;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.llopez.awallet.R;

public class BillCategoryListFragment extends Fragment {
    private RecyclerView rvBillCategory;
    private FloatingActionButton btnAdd;

    public BillCategoryListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_bill_category_list, container, false);

        rvBillCategory = layout.findViewById(R.id.rvBillCategory);
        btnAdd = layout.findViewById(R.id.btnAddBillCategory);

        btnAdd.setOnClickListener(v -> moveToAddCreateBillFragment());

        return layout;
    }

    private void moveToAddCreateBillFragment() {
        NavHostFragment.findNavController(this).navigate(R.id.action_billCategoryListFragment_to_createBillCategoryFragment);
    }
}