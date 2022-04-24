package com.llopez.awallet.ui.list_bills;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.llopez.awallet.model.BillCategory;
import com.llopez.awallet.model.BillObject;
import com.llopez.awallet.utilities.GetDataStatus;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ListBillsViewModel extends ViewModel {
    private FirebaseFirestore firestore;
    private FirebaseUser user;
    public FirestoreRecyclerOptions firestoreRecyclerOptions;
    public List<BillObject> billsList;

    private MutableLiveData<GetDataStatus> dataStatus;
    public MutableLiveData<GetDataStatus> getCurrentDataStatus() {
        if (this.dataStatus == null) {
            this.dataStatus = new MutableLiveData<>(GetDataStatus.INITIALIZE);
        }
        return this.dataStatus;
    }

    public ListBillsViewModel(FirebaseUser user, FirebaseFirestore firestore) {
        this.user = user;
        this.firestore = firestore;
        this.billsList = new ArrayList<>();
    }

    public void loadDataFromService() {
        dataStatus.setValue(GetDataStatus.LOADING);

        firestore.collection("bill_category_"+ user.getEmail() +"")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<BillCategory> categoryList = new ArrayList<>();

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            BillCategory category = new BillCategory(document.getString("name"), document.getString("color"), document.getString("createdAt"));
                            categoryList.add(category);
                        }

                        if (categoryList.isEmpty()) {
                            dataStatus.setValue(GetDataStatus.SUCCESS);
                        } else {
                            for (BillCategory c : categoryList) {
                                firestore.collection("bill_category_" + user.getEmail() + "")
                                        .document(c.getName())
                                        .collection("bills")
                                        .get()
                                        .addOnCompleteListener(t -> {
                                            if (t.isSuccessful()) {
                                                for (QueryDocumentSnapshot d : t.getResult()) {
                                                    BillObject bill = new BillObject(c, d.getString("name"), d.getDouble("amount"), d.getString("description"), d.getString("createdAt"));
                                                    billsList.add(bill);
                                                }

                                                if (categoryList.get(categoryList.size() - 1) == c) {
                                                    dataStatus.setValue(GetDataStatus.SUCCESS);
                                                }
                                            }
                                        });
                            }
                        }
                    } else {
                        dataStatus.setValue(GetDataStatus.ERROR);
                    }
                });
    }
}
