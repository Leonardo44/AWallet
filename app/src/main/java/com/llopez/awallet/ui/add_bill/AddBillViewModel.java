package com.llopez.awallet.ui.add_bill;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.llopez.awallet.model.BillCategory;
import com.llopez.awallet.model.EarningCategory;
import com.llopez.awallet.utilities.GetDataStatus;
import com.llopez.awallet.utilities.SendDataStatus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddBillViewModel extends ViewModel {
    private FirebaseFirestore firestore;
    private FirebaseUser user;
    private SimpleDateFormat dateFormat;

    private MutableLiveData<SendDataStatus> sendDataStatus;
    public MutableLiveData<SendDataStatus> getSendCurrentDataStatus() {
        if (this.sendDataStatus == null) {
            this.sendDataStatus = new MutableLiveData<>(SendDataStatus.INITIALIZE);
        }
        return this.sendDataStatus;
    }
    private MutableLiveData<GetDataStatus> getDataStatus;
    public MutableLiveData<GetDataStatus> getCurrentDataStatus() {
        if (this.getDataStatus == null) {
            this.getDataStatus = new MutableLiveData<>(GetDataStatus.INITIALIZE);
        }
        return this.getDataStatus;
    }
    public ArrayList<BillCategory> categorieList;

    public AddBillViewModel(FirebaseUser user, FirebaseFirestore firestore) {
        this.user = user;
        this.firestore = firestore;
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        this.categorieList = new ArrayList<BillCategory>();
    }

    public void loadData() {
        getDataStatus.setValue(GetDataStatus.LOADING);

        firestore.collection("bill_category_"+ user.getEmail() +"")
                .orderBy("createdAt")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots.getDocuments().size() > 0) {
                        ArrayList<BillCategory> list = new ArrayList<>();

                        for (int i= 0; i < queryDocumentSnapshots.getDocuments().size(); i++){
                            String name = queryDocumentSnapshots.getDocuments().get(i).getString("name");
                            String color = queryDocumentSnapshots.getDocuments().get(i).getString("color");
                            String createdAt = queryDocumentSnapshots.getDocuments().get(i).getString("createdAt");
                            BillCategory category = new BillCategory(name, color, createdAt);

                            list.add(category);
                        }

                        categorieList = list;
                    }
                    getDataStatus.setValue(GetDataStatus.SUCCESS);
                });
    }

    public void createBill(BillCategory category, String name, Double amount, String description) {
        sendDataStatus.setValue(SendDataStatus.LOADING);

        Map<String, Object> earning = new HashMap<>();
        earning.put("name", name);
        earning.put("amount", amount);
        earning.put("description", description);
        earning.put("createdAt", dateFormat.format(new Date()));

        DocumentReference userDataReference = firestore.collection("bill_category_"+ user.getEmail() +"").document(category.getName());

        userDataReference
                .collection("bills")
                .add(earning)
                .addOnSuccessListener(documentReference -> {
                    sendDataStatus.setValue(SendDataStatus.SUCCESS);
                })
                .addOnFailureListener(e -> {
                    sendDataStatus.setValue(SendDataStatus.ERROR);
                });
    }
}
