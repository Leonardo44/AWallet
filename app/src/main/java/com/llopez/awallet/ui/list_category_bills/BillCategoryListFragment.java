package com.llopez.awallet.ui.list_category_bills;

import android.opengl.Visibility;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.llopez.awallet.R;
import com.llopez.awallet.model.BillCategory;
import com.llopez.awallet.utilities.GetDataStatus;

public class BillCategoryListFragment extends Fragment implements BillCategoryAdapter.BillCategoryAdapterListener {
    private RecyclerView rvBillCategory;
    private FloatingActionButton btnAdd;
    private TextView emptyMessage;

    private BillCategoryAdapter adapter;
    private BillCategoryListViewModel viewModel;

    public BillCategoryListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, new BillCategoryListViewModelFactory(FirebaseAuth.getInstance().getCurrentUser(), FirebaseFirestore.getInstance())).get(BillCategoryListViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_bill_category_list, container, false);

        rvBillCategory = layout.findViewById(R.id.rvBillCategory);
        btnAdd = layout.findViewById(R.id.btnAddBillCategory);
        emptyMessage = layout.findViewById(R.id.emptyMessage);

        rvBillCategory.setLayoutManager(new LinearLayoutManager(getContext()));

        btnAdd.setOnClickListener(v -> moveToAddCreateBillFragment());

        final Observer<GetDataStatus> getDataStatusObserver = newValue -> {
            switch (newValue) {
                case INITIALIZE:
                    break;
                case LOADING:
                    break;
                case ERROR:
                    break;
                case SUCCESS:
                    adapter = new BillCategoryAdapter(viewModel.firestoreRecyclerOptions, this);
                    rvBillCategory.setAdapter(adapter);
                case NOT_INTERNET:
                    break;
            }
        };
        viewModel.getCurrentDataStatus().observe(this.getActivity(), getDataStatusObserver);
        return layout;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.loadDataFromService();
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    private void moveToAddCreateBillFragment() {
        NavHostFragment.findNavController(this).navigate(R.id.action_billCategoryListFragment_to_createBillCategoryFragment);
    }

    @Override
    public void onEditBillCategory(BillCategory category) {
    }

    @Override
    public void onDeleteBillCategory(BillCategory category) {
    }

    @Override
    public void showEmptyMessage(boolean visible) {
        if (visible) {
            emptyMessage.setVisibility(View.VISIBLE);
        } else {
            emptyMessage.setVisibility(View.GONE);
        }
    }
}