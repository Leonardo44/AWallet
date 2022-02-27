package com.llopez.awallet.ui.list_earnings;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.llopez.awallet.R;
import com.llopez.awallet.ui.list_bills.BillsAdapter;

public class ListEarningsFragment extends Fragment {
    private RecyclerView rvEarnings;
    private FloatingActionButton btnAdd;
    private BillsAdapter billsAdapter;

    public ListEarningsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_list_earnings, container, false);
        rvEarnings = layout.findViewById(R.id.rvEarnings);
        btnAdd = layout.findViewById(R.id.floatingActionButtonEarnings);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToAddEarningFragment();
            }
        });

        rvEarnings.addItemDecoration(new DividerItemDecoration(rvEarnings.getContext(), DividerItemDecoration.VERTICAL));

        return layout;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void moveToAddEarningFragment() {
        NavHostFragment.findNavController(this).navigate(R.id.action_listEarningsFragment_to_addEarningFragment);
    }
}