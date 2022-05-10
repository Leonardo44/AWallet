package com.llopez.awallet.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.llopez.awallet.ui.list_bills.BillsAdapter;
import com.llopez.awallet.ui.list_bills.ListBillsViewModel;
import com.llopez.awallet.ui.list_bills.ListBillsViewModelFactory;
import com.llopez.awallet.ui.on_boarding.OnBoardingActivity;
import com.llopez.awallet.R;
import com.llopez.awallet.utilities.GetDataStatus;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    private TextView textViewTitleFragment;
    private TextView textViewKeyEarning;
    private TextView textViewValueEarning;
    private TextView textViewKeyBill;
    private TextView textViewValueBill;
    private PieChart chartHome;

    private HomeViewModel viewModel;

    public HomeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this, new HomeViewModelFactory(FirebaseAuth.getInstance().getCurrentUser(), FirebaseFirestore.getInstance())).get(HomeViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        textViewTitleFragment = view.findViewById(R.id.tvHomeTitle);
        textViewKeyEarning = view.findViewById(R.id.textViewKeyEarning);
        textViewValueEarning = view.findViewById(R.id.textViewValueEarning);
        textViewKeyBill = view.findViewById(R.id.textViewKeyBill);
        textViewValueBill = view.findViewById(R.id.textViewValueBill);
        chartHome = view.findViewById(R.id.chartHome);

        final Observer<GetDataStatus> getBillCurrentDataStatus = newValue -> {
            switch (newValue) {
                case INITIALIZE:
                    break;
                case LOADING:
                    break;
                case ERROR:
                    break;
                case SUCCESS:
                    loadPieChartData();
                case NOT_INTERNET:
                    break;
            }
        };
        final Observer<GetDataStatus> getEarningCurrentDataStatus = newValue -> {
            switch (newValue) {
                case INITIALIZE:
                    break;
                case LOADING:
                    break;
                case ERROR:
                    break;
                case SUCCESS:
                    loadPieChartData();
                case NOT_INTERNET:
                    break;
            }
        };
        viewModel.getBillCurrentDataStatus().observe(this.getActivity(), getBillCurrentDataStatus);
        viewModel.getEarningCurrentDataStatus().observe(this.getActivity(), getEarningCurrentDataStatus);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences preferences = getActivity().getSharedPreferences("credential", Context.MODE_PRIVATE);
        boolean isFirstTime = preferences.getBoolean("isFirstTime", true);

        if (isFirstTime) {
            Intent intent = new Intent(getActivity(), OnBoardingActivity.class);
            startActivity(intent);
        }

        viewModel.loadDataFromService();
    }

    private void loadPieChartData() {
        ArrayList<PieEntry> entries = new ArrayList<>();
        ArrayList<Integer> colors = new ArrayList<>();

        entries.add(new PieEntry(viewModel.billMonthAmount.floatValue(), R.string.menu_expenses));
        entries.add(new PieEntry(viewModel.earningMonthAmount.floatValue(), R.string.menu_earnings));

        colors.add(R.color.red_error);
        colors.add(R.color.blue_primary_dark);

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(chartHome));
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);

        chartHome.setData(data);
        chartHome.invalidate();

        chartHome.setDrawHoleEnabled(true);


        textViewValueEarning.setText("$"+ viewModel.earningMonthAmount +"");
        textViewValueBill.setText("$"+ viewModel.billMonthAmount +"");
    }
}