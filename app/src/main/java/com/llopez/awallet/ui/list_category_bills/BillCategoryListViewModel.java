package com.llopez.awallet.ui.list_category_bills;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.llopez.awallet.model.BillCategory;
import com.llopez.awallet.model.EarningObject;
import com.llopez.awallet.utilities.GetDataStatus;
import com.llopez.awallet.utilities.SendDataStatus;

import java.text.SimpleDateFormat;

public class BillCategoryListViewModel extends ViewModel {
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

    public BillCategoryListViewModel(FirebaseUser user, FirebaseFirestore firestore) {
        this.user = user;
        this.firestore = firestore;
    }

    public void deleteCategory(String documentName) {
        firestore.collection("bill_category_"+ user.getEmail() +"")
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

        Query query = firestore.collection("bill_category_"+ user.getEmail() +"")
                .orderBy("createdAt");
        firestoreRecyclerOptions = new FirestoreRecyclerOptions
                .Builder<BillCategory>()
                .setQuery(query, BillCategory.class)
                .build();

        dataStatus.setValue(GetDataStatus.SUCCESS);
    }
}
