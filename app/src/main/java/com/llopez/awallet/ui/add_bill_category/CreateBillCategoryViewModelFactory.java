package com.llopez.awallet.ui.add_bill_category;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

class CreateBillCategoryViewModelFactory implements ViewModelProvider.Factory {
    private FirebaseFirestore firestore;
    private FirebaseUser user;

    public CreateBillCategoryViewModelFactory(FirebaseUser user, FirebaseFirestore firestore) {
        this.user = user;
        this.firestore = firestore;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new CreateBillCategoryViewModel(user, firestore);
    }
}
