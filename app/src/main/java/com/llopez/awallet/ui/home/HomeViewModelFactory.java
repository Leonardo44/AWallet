package com.llopez.awallet.ui.home;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.llopez.awallet.ui.list_bills.ListBillsViewModel;

public class HomeViewModelFactory implements ViewModelProvider.Factory {
    private FirebaseFirestore firestore;
    private FirebaseUser user;

    public HomeViewModelFactory(FirebaseUser user, FirebaseFirestore firestore) {
        this.user = user;
        this.firestore = firestore;
    }

    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new HomeViewModel(user, firestore);
    }
}
