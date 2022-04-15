package com.llopez.awallet.ui.add_earning_category;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.llopez.awallet.utilities.SendDataStatus;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CreateEarningCategoryViewModel extends ViewModel {
    private FirebaseFirestore firestore;
    private FirebaseUser user;
    private SimpleDateFormat dateFormat;

    private MutableLiveData<SendDataStatus> dataStatus;
    public MutableLiveData<SendDataStatus> getCurrentDataStatus() {
        if (this.dataStatus == null) {
            this.dataStatus = new MutableLiveData<>(SendDataStatus.INITIALIZE);
        }
        return this.dataStatus;
    }

    public CreateEarningCategoryViewModel(FirebaseUser user, FirebaseFirestore firestore) {
        this.user = user;
        this.firestore = firestore;
        this.dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
    }

    public void createCategory(String name, String color) {
        dataStatus.setValue(SendDataStatus.LOADING);

        Map<String, Object> category = new HashMap<>();
        category.put("name", name);
        category.put("color", color);
        category.put("createdAt", dateFormat.format(new Date()));

        CollectionReference userDataReference = firestore.collection("earning_category_"+ user.getEmail() +"");

        userDataReference
                .document(name)
                .set(category)
                .addOnSuccessListener(documentReference -> {
                    dataStatus.setValue(SendDataStatus.SUCCESS);
                })
                .addOnFailureListener(e -> {
                    dataStatus.setValue(SendDataStatus.ERROR);
                });
    }
}
