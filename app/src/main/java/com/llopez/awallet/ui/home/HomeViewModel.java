package com.llopez.awallet.ui.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.llopez.awallet.model.BillCategory;
import com.llopez.awallet.model.BillObject;
import com.llopez.awallet.model.EarningCategory;
import com.llopez.awallet.model.EarningObject;
import com.llopez.awallet.utilities.GetDataStatus;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class HomeViewModel extends ViewModel {
    private FirebaseFirestore firestore;
    private FirebaseUser user;
    public FirestoreRecyclerOptions firestoreRecyclerOptions;
    public Double billMonthAmount;
    public Double earningMonthAmount;
    private MutableLiveData<GetDataStatus> billDataStatus;
    public MutableLiveData<GetDataStatus> getBillCurrentDataStatus() {
        if (this.billDataStatus == null) {
            this.billDataStatus = new MutableLiveData<>(GetDataStatus.INITIALIZE);
        }
        return this.billDataStatus;
    }
    private MutableLiveData<GetDataStatus> earningDataStatus;
    public MutableLiveData<GetDataStatus> getEarningCurrentDataStatus() {
        if (this.earningDataStatus == null) {
            this.earningDataStatus = new MutableLiveData<>(GetDataStatus.INITIALIZE);
        }
        return this.earningDataStatus;
    }

    public HomeViewModel(FirebaseUser user, FirebaseFirestore firestore) {
        this.user = user;
        this.firestore = firestore;
        this.billMonthAmount = 0.0;
        this.earningMonthAmount = 0.0;
    }

    public void loadDataFromService() {
        billDataStatus.setValue(GetDataStatus.LOADING);
        earningDataStatus.setValue(GetDataStatus.LOADING);

        ZoneId defaultZoneId = ZoneId.systemDefault();
        LocalDate convertedDate = LocalDate.now();
        LocalDate startDate = convertedDate.withDayOfMonth(1);
        LocalDate endDate = convertedDate.withDayOfMonth(convertedDate.getMonth().length(convertedDate.isLeapYear()));

        billMonthAmount = 0.0;
        earningMonthAmount = 0.0;

        firestore.collection("bill_category_"+ user.getEmail() +"")
                .orderBy("createdAt")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<BillCategory> categoryList = new ArrayList<>();

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            BillCategory category = new BillCategory(document.getString("name"), document.getString("name"), document.getString("color"), document.getTimestamp("createdAt").toDate());
                            categoryList.add(category);
                        }

                        if (categoryList.isEmpty()) {
                            billDataStatus.setValue(GetDataStatus.SUCCESS);
                        } else {
                            for (BillCategory c : categoryList) {
                                firestore.collection("bill_category_" + user.getEmail() + "")
                                        .document(c.getName())
                                        .collection("bills")
                                        .whereGreaterThanOrEqualTo("createdAt",  Date.from(startDate.atStartOfDay(defaultZoneId).toInstant()))
                                        .whereLessThanOrEqualTo("createdAt", Date.from(endDate.atStartOfDay(defaultZoneId).toInstant()))
                                        .get()
                                        .addOnCompleteListener(t -> {
                                            if (t.isSuccessful()) {
                                                for (QueryDocumentSnapshot d : t.getResult()) {
                                                    billMonthAmount += d.getDouble("amount");
                                                }

                                                if (categoryList.get(categoryList.size() - 1) == c) {
                                                    billDataStatus.setValue(GetDataStatus.SUCCESS);
                                                }
                                            }
                                        });
                            }
                        }
                    } else {
                        billDataStatus.setValue(GetDataStatus.ERROR);
                    }
                });

        firestore.collection("earning_category_"+ user.getEmail() +"")
                .orderBy("createdAt")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<EarningCategory> categoryList = new ArrayList<>();

                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Timestamp timestamp = document.getTimestamp("createdAt");
                            EarningCategory category = new EarningCategory(document.getString("name"), document.getString("name"), document.getString("color"), timestamp.toDate());
                            categoryList.add(category);
                        }

                        if (categoryList.isEmpty()) {
                            earningDataStatus.setValue(GetDataStatus.SUCCESS);
                        } else {
                            for (EarningCategory c : categoryList) {
                                firestore.collection("earning_category_" + user.getEmail() + "")
                                        .document(c.getName())
                                        .collection("earnings")
                                        .whereGreaterThanOrEqualTo("createdAt",  Date.from(startDate.atStartOfDay(defaultZoneId).toInstant()))
                                        .whereLessThanOrEqualTo("createdAt", Date.from(endDate.atStartOfDay(defaultZoneId).toInstant()))
                                        .get()
                                        .addOnCompleteListener(t -> {
                                            if (t.isSuccessful()) {
                                                for (QueryDocumentSnapshot d : t.getResult()) {
                                                    earningMonthAmount += d.getDouble("amount");
                                                }

                                                if (categoryList.get(categoryList.size() - 1) == c) {
                                                    earningDataStatus.setValue(GetDataStatus.SUCCESS);
                                                }
                                            }
                                        });
                            }
                        }
                    } else {
                        earningDataStatus.setValue(GetDataStatus.ERROR);
                    }
                });
    }
}