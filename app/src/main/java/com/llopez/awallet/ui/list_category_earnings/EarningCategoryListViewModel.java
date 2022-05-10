package com.llopez.awallet.ui.list_category_earnings;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.llopez.awallet.model.BillCategory;
import com.llopez.awallet.model.EarningCategory;
import com.llopez.awallet.utilities.GetDataStatus;

public class EarningCategoryListViewModel extends ViewModel  {
    private FirebaseFirestore firestore;
    private FirebaseUser user;
    public FirestoreRecyclerOptions firestoreRecyclerOptions;

    private MutableLiveData<GetDataStatus> dataStatus;
    public MutableLiveData<GetDataStatus> getCurrentDataStatus() {
        if (this.dataStatus == null) {
            this.dataStatus = new MutableLiveData<>(GetDataStatus.INITIALIZE);
        }
        return this.dataStatus;
    }

    public EarningCategoryListViewModel(FirebaseUser user, FirebaseFirestore firestore) {
        this.user = user;
        this.firestore = firestore;
    }

    public void deleteCategory(String documentName) {
        firestore.collection("earning_category_"+ user.getEmail() +"")
                .document(documentName)
                .delete()
                .addOnSuccessListener(aVoid -> {
                })
                .addOnFailureListener(e -> {
                    dataStatus.setValue(GetDataStatus.ERROR);
                });
    }

    public void loadDataFromService() {
        dataStatus.setValue(GetDataStatus.LOADING);

        Query query = firestore.collection("earning_category_"+ user.getEmail() +"")
                .orderBy("createdAt");
        firestoreRecyclerOptions = new FirestoreRecyclerOptions
                .Builder<EarningCategory>()
                .setQuery(query, EarningCategory.class)
                .build();

        dataStatus.setValue(GetDataStatus.SUCCESS);
    }
}
