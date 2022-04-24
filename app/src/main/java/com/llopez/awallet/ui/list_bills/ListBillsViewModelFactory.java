package com.llopez.awallet.ui.list_bills;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.llopez.awallet.ui.list_category_bills.BillCategoryListViewModel;

public class ListBillsViewModelFactory implements ViewModelProvider.Factory {
    private FirebaseFirestore firestore;
    private FirebaseUser user;

    public ListBillsViewModelFactory(FirebaseUser user, FirebaseFirestore firestore) {
        this.user = user;
        this.firestore = firestore;
    }

    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ListBillsViewModel(user, firestore);
    }
}
