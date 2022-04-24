package com.llopez.awallet.ui.list_earnings;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.llopez.awallet.model.BillCategory;
import com.llopez.awallet.model.BillObject;
import com.llopez.awallet.model.EarningCategory;
import com.llopez.awallet.model.EarningObject;
import com.llopez.awallet.utilities.GetDataStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EarningListViewModel extends ViewModel {
    private FirebaseFirestore firestore;
    private FirebaseUser user;
    public FirestoreRecyclerOptions firestoreRecyclerOptions;
    public List<EarningObject> earningsList;

    private MutableLiveData<GetDataStatus> dataStatus;
    public MutableLiveData<GetDataStatus> getCurrentDataStatus() {
        if (this.dataStatus == null) {
            this.dataStatus = new MutableLiveData<>(GetDataStatus.INITIALIZE);
        }
        return this.dataStatus;
    }

    public EarningListViewModel(FirebaseUser user, FirebaseFirestore firestore) {
        this.user = user;
        this.firestore = firestore;
        this.earningsList = new ArrayList<>();
    }

    public void loadDataFromService() {
        dataStatus.setValue(GetDataStatus.LOADING);

        earningsList.clear();
        firestore.collection("earning_category_"+ user.getEmail() +"")
                .orderBy("createdAt")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<EarningCategory> categoryList = new ArrayList<>();

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            EarningCategory category = new EarningCategory(document.getString("name"), document.getString("color"), document.getString("createdAt"));
                            categoryList.add(category);
                        }

                        if (categoryList.isEmpty()) {
                            dataStatus.setValue(GetDataStatus.SUCCESS);
                        } else {
                            for (EarningCategory c : categoryList) {
                                firestore.collection("earning_category_" + user.getEmail() + "")
                                        .document(c.getName())
                                        .collection("earnings")
                                        .orderBy("createdAt", Query.Direction.DESCENDING)
                                        .get()
                                        .addOnCompleteListener(t -> {
                                            if (t.isSuccessful()) {
                                                for (QueryDocumentSnapshot d : t.getResult()) {
                                                    EarningObject bill = new EarningObject(c, d.getString("name"), d.getDouble("amount"), d.getString("description"), d.getString("createdAt"));
                                                    earningsList.add(bill);
                                                }

                                                if (categoryList.get(categoryList.size() - 1) == c) {
                                                    Collections.sort(earningsList, (o1, o2) -> o2.getCreatedAt().compareTo(o1.getCreatedAt()));
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