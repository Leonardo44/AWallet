package com.llopez.awallet.ui.list_category_bills;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class BillCategoryListViewModelFactory implements ViewModelProvider.Factory {
    private FirebaseFirestore firestore;
    private FirebaseUser user;

    public BillCategoryListViewModelFactory(FirebaseUser user, FirebaseFirestore firestore) {
        this.user = user;
        this.firestore = firestore;
    }

    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new BillCategoryListViewModel(user, firestore);
    }
}
