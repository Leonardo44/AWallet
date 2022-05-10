package com.llopez.awallet.ui.list_category_earnings;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
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
import com.llopez.awallet.model.EarningCategory;
import com.llopez.awallet.utilities.GetDataStatus;

public class EarningCategoryListFragment extends Fragment implements EarningCategoryAdapter.EarningCategoryAdapterListener {
    private RecyclerView rvEarningCategory;
    private FloatingActionButton btnAdd;
    private TextView emptyMessage;

    private EarningCategoryAdapter adapter;
    private EarningCategoryListViewModel viewModel;

    public EarningCategoryListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, new EarningCategoryListViewModelFactory(FirebaseAuth.getInstance().getCurrentUser(), FirebaseFirestore.getInstance())).get(EarningCategoryListViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout =  inflater.inflate(R.layout.fragment_earning_category_list, container, false);
        rvEarningCategory = layout.findViewById(R.id.rvEarningCategory);
        btnAdd = layout.findViewById(R.id.btnAddEarningCategory);
        emptyMessage = layout.findViewById(R.id.emptyMessage);

        btnAdd.setOnClickListener(v -> moveToAddCreateEarningFragment());

        rvEarningCategory.setLayoutManager(new LinearLayoutManager(getContext()));

        final Observer<GetDataStatus> getDataStatusObserver = newValue -> {
            switch (newValue) {
                case INITIALIZE:
                    break;
                case LOADING:
                    break;
                case ERROR:
                    break;
                case SUCCESS:
                    adapter = new EarningCategoryAdapter(viewModel.firestoreRecyclerOptions, this);
                    rvEarningCategory.setAdapter(adapter);
                case NOT_INTERNET:
                    break;
            }
        };
        viewModel.getCurrentDataStatus().observe(this.getActivity(), getDataStatusObserver);

        return layout;
    }

    private void moveToAddCreateEarningFragment() {
        Bundle bundle = new Bundle();
        bundle.putBoolean("is_edit", false);
        NavHostFragment.findNavController(this).navigate(R.id.action_earningCategoryListFragment_to_createEarningCategoryFragment, bundle);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MutableLiveData<String> documentEarningCategoryIdLiveData = NavHostFragment
                .findNavController(this)
                .getCurrentBackStackEntry()
                .getSavedStateHandle()
                .getLiveData("document_earning_category_id_delete");

        documentEarningCategoryIdLiveData.observe(getViewLifecycleOwner(), s -> {
            if (s != null) {
                NavHostFragment.findNavController(this).popBackStack();
                viewModel.deleteCategory(s);
            }
        });

        viewModel.loadDataFromService();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (adapter != null) {
            adapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (adapter != null) {
            adapter.stopListening();
        }
    }

    @Override
    public void onEditEarningCategory(EarningCategory category) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("is_edit", true);
        bundle.putString("category_name", category.getName());
        bundle.putString("category_color", category.getColor());
        NavHostFragment.findNavController(this).navigate(R.id.action_earningCategoryListFragment_to_createEarningCategoryFragment, bundle);
    }

    @Override
    public void onDeleteEarningCategory(EarningCategory category) {
        Bundle bundle = new Bundle();
        bundle.putString("document_earning_category_id_delete", category.getName());
        NavHostFragment.findNavController(this).navigate(R.id.itemDeleteEarningCategoryDialogFragment, bundle);
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