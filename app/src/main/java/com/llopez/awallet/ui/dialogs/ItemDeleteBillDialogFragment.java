package com.llopez.awallet.ui.dialogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.llopez.awallet.R;

public class ItemDeleteBillDialogFragment  extends BottomSheetDialogFragment {
    private TextView tvDeleteItem;
    private Button btnYesDelete;
    private Button btnNoCancel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        String documentId = getArguments().getString("document_bill_id_delete");

        View layout = inflater.inflate(R.layout.fragment_item_delete_dialog, container, false);
        tvDeleteItem = layout.findViewById(R.id.tvDeleteItem);
        btnYesDelete = layout.findViewById(R.id.btnYesDelete);
        btnNoCancel = layout.findViewById(R.id.btnNoCancel);

        btnNoCancel.setOnClickListener(v -> {
            NavHostFragment.findNavController(this).popBackStack();
        });

        btnYesDelete.setOnClickListener(v -> {
            NavHostFragment.findNavController(this).getPreviousBackStackEntry().getSavedStateHandle().set("document_bill_id_delete", documentId);
        });

        return layout;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
