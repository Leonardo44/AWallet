package com.llopez.awallet.ui.add_earning_category;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.llopez.awallet.ui.add_bill_category.CreateBillCategoryViewModel;

public class CreateEarningCategoryViewModelFactory implements ViewModelProvider.Factory {
    private FirebaseFirestore firestore;
    private FirebaseUser user;

    public CreateEarningCategoryViewModelFactory(FirebaseUser user, FirebaseFirestore firestore) {
        this.user = user;
        this.firestore = firestore;
    }

    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new CreateEarningCategoryViewModel(user, firestore);
    }
}
