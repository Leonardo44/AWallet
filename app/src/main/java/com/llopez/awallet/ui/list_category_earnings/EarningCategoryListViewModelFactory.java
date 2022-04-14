package com.llopez.awallet.ui.list_category_earnings;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.llopez.awallet.ui.list_category_bills.BillCategoryListViewModel;

public class EarningCategoryListViewModelFactory implements ViewModelProvider.Factory{
    private FirebaseFirestore firestore;
    private FirebaseUser user;

    public EarningCategoryListViewModelFactory(FirebaseUser user, FirebaseFirestore firestore) {
        this.user = user;
        this.firestore = firestore;
    }

    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new EarningCategoryListViewModel(user, firestore);
    }
}
