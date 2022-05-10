package com.llopez.awallet.ui.list_earnings;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.llopez.awallet.R;
import com.llopez.awallet.model.BillObject;
import com.llopez.awallet.model.EarningObject;
import com.llopez.awallet.ui.list_bills.BillsAdapter;
import com.llopez.awallet.ui.list_bills.ListBillsViewModel;
import com.llopez.awallet.ui.list_bills.ListBillsViewModelFactory;
import com.llopez.awallet.utilities.GetDataStatus;

import java.util.Objects;

public class ListEarningsFragment extends Fragment implements EarningAdapter.EarningAdapterAdapterListener {
    private RecyclerView rvEarnings;
    private FloatingActionButton btnAdd;
    private TextView emptyMessage;

    private EarningAdapter adapter;
    private EarningListViewModel viewModel;

    public ListEarningsFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, new EarningListViewModelFactory(FirebaseAuth.getInstance().getCurrentUser(), FirebaseFirestore.getInstance())).get(EarningListViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_list_earnings, container, false);
        rvEarnings = layout.findViewById(R.id.rvEarnings);
        btnAdd = layout.findViewById(R.id.floatingActionButtonEarnings);
        emptyMessage = layout.findViewById(R.id.emptyMessage);

        emptyMessage.setVisibility(View.VISIBLE);

        btnAdd.setOnClickListener(v -> moveToAddEarningFragment());

        rvEarnings.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        final Observer<GetDataStatus> getDataStatusObserver = newValue -> {
            switch (newValue) {
                case INITIALIZE:
                    break;
                case LOADING:
                    break;
                case ERROR:
                    break;
                case SUCCESS:
                    adapter = new EarningAdapter(viewModel.earningsList, this);

                    if (viewModel.earningsList.isEmpty()) {
                        emptyMessage.setVisibility(View.VISIBLE);
                    } else {
                        emptyMessage.setVisibility(View.GONE);
                    }

                    rvEarnings.setAdapter(adapter);
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
        MutableLiveData<String> documentEarningIdLiveData = NavHostFragment
                .findNavController(this)
                .getCurrentBackStackEntry()
                .getSavedStateHandle()
                .getLiveData("document_earning_id_delete");

        documentEarningIdLiveData.observe(getViewLifecycleOwner(), s -> {
            if (s != null) {
                NavHostFragment.findNavController(this).popBackStack();
                viewModel.deleteEarning(s);
            }
        });

        viewModel.loadDataFromService();
    }

    private void moveToAddEarningFragment() {
        Bundle bundle = new Bundle();
        bundle.putBoolean("is_edit", false);
        NavHostFragment.findNavController(this).navigate(R.id.action_listEarningsFragment_to_addEarningFragment, bundle);
    }

    @Override
    public void onEditEarning(EarningObject earning) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("is_edit", true);
        bundle.putString("category_name", earning.getCategory().getName());
        bundle.putString("earning_document_name", earning.getDocumentName());
        bundle.putString("earning_name", earning.getName());
        bundle.putDouble("earning_amount", earning.getAmount());
        bundle.putString("earning_description", earning.getDescription());
        NavHostFragment.findNavController(this).navigate(R.id.action_listEarningsFragment_to_addEarningFragment, bundle);
    }

    @Override
    public void onDeleteEarning(EarningObject earning) {
        Bundle bundle = new Bundle();
        bundle.putString("document_earning_id_delete", earning.getDocumentName());
        NavHostFragment.findNavController(this).navigate(R.id.itemDeleteDialogFragment, bundle);
    }
}
