package com.llopez.awallet.ui.list_category_earnings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.llopez.awallet.R;

public class EarningCategoryListFragment extends Fragment {
    private RecyclerView rvEarningCategory;
    private FloatingActionButton btnAdd;

    public EarningCategoryListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout =  inflater.inflate(R.layout.fragment_earning_category_list, container, false);
        rvEarningCategory = layout.findViewById(R.id.rvEarningCategory);
        btnAdd = layout.findViewById(R.id.btnAddEarningCategory);

        btnAdd.setOnClickListener(v -> moveToAddCreateEarningFragment());

        return layout;
    }

    private void moveToAddCreateEarningFragment() {
        NavHostFragment.findNavController(this).navigate(R.id.action_earningCategoryListFragment_to_createEarningCategoryFragment);
    }
}