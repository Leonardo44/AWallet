package com.llopez.awallet.ui.add_earning;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.llopez.awallet.model.BillCategory;
import com.llopez.awallet.model.EarningCategory;
import com.llopez.awallet.utilities.GetDataStatus;
import com.llopez.awallet.utilities.SendDataStatus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddEarningViewModel extends ViewModel {
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
    public ArrayList<EarningCategory> categorieList;

    public AddEarningViewModel(FirebaseUser user, FirebaseFirestore firestore) {
        this.user = user;
        this.firestore = firestore;
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        this.categorieList = new ArrayList<EarningCategory>();
    }

    public void loadData() {
        getDataStatus.setValue(GetDataStatus.LOADING);

        firestore.collection("earning_category_"+ user.getEmail() +"")
                .orderBy("createdAt")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots.getDocuments().size() > 0) {
                        ArrayList<EarningCategory> list = new ArrayList<>();

                        for (int i= 0; i < queryDocumentSnapshots.getDocuments().size(); i++){
                            String name = queryDocumentSnapshots.getDocuments().get(i).getString("name");
                            String color = queryDocumentSnapshots.getDocuments().get(i).getString("color");
                            Timestamp timestamp = queryDocumentSnapshots.getDocuments().get(i).getTimestamp("createdAt");

                            EarningCategory category = new EarningCategory(name, name, color, timestamp.toDate());

                            list.add(category);
                        }

                        categorieList = list;
                    }
                    getDataStatus.setValue(GetDataStatus.SUCCESS);
                });
    }

    public void createEarning(EarningCategory category, String name, Double amount, String description) {
        sendDataStatus.setValue(SendDataStatus.LOADING);

        Map<String, Object> earning = new HashMap<>();
        earning.put("name", name);
        earning.put("amount", amount);
        earning.put("description", description);
        earning.put("createdAt", new Date());

        DocumentReference userDataReference = firestore.collection("earning_category_"+ user.getEmail() +"").document(category.getName());

        userDataReference
                .collection("earnings")
                .add(earning)
                .addOnSuccessListener(documentReference -> {
                    sendDataStatus.setValue(SendDataStatus.SUCCESS);
                })
                .addOnFailureListener(e -> {
                    sendDataStatus.setValue(SendDataStatus.ERROR);
                });
    }
}
