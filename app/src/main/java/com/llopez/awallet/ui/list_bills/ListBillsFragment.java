package com.llopez.awallet.ui.list_bills;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
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
import com.llopez.awallet.model.BillObject;
import com.llopez.awallet.ui.list_category_bills.BillCategoryAdapter;
import com.llopez.awallet.ui.list_category_bills.BillCategoryListViewModel;
import com.llopez.awallet.ui.list_category_bills.BillCategoryListViewModelFactory;
import com.llopez.awallet.utilities.GetDataStatus;

public class ListBillsFragment extends Fragment implements BillsAdapter.BillsAdapterAdapterListener {
    private RecyclerView rvBills;
    private FloatingActionButton btnAdd;
    private TextView emptyMessage;

    private BillsAdapter adapter;
    private ListBillsViewModel viewModel;

    public ListBillsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, new ListBillsViewModelFactory(FirebaseAuth.getInstance().getCurrentUser(), FirebaseFirestore.getInstance())).get(ListBillsViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_list_bills, container, false);

        rvBills = layout.findViewById(R.id.rvBills);
        btnAdd = layout.findViewById(R.id.floatingActionButton);
        emptyMessage = layout.findViewById(R.id.emptyMessage);

        emptyMessage.setVisibility(View.VISIBLE);

        btnAdd.setOnClickListener(v -> moveToAddBillFragment());

        rvBills.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        final Observer<GetDataStatus> getDataStatusObserver = newValue -> {
            switch (newValue) {
                case INITIALIZE:
                    break;
                case LOADING:
                    break;
                case ERROR:
                    break;
                case SUCCESS:
                    adapter = new BillsAdapter(viewModel.billsList, this);

                    if (viewModel.billsList.isEmpty()) {
                        emptyMessage.setVisibility(View.VISIBLE);
                    } else {
                        emptyMessage.setVisibility(View.GONE);
                    }

                    rvBills.setAdapter(adapter);
                    break;
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
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private void moveToAddBillFragment() {
        NavHostFragment.findNavController(this).navigate(R.id.action_listBillsFragment_to_addBillFragment);
    }

    @Override
    public void onEditBill(BillObject bill) {
    }

    @Override
    public void onDeleteBill(BillObject bill) {
    }
}

