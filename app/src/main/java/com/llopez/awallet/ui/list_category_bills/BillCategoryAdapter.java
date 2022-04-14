package com.llopez.awallet.ui.list_category_bills;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;
import com.llopez.awallet.model.BillCategory;
import com.llopez.awallet.R;
import com.llopez.awallet.utilities.Color;

public class BillCategoryAdapter extends FirestoreRecyclerAdapter<BillCategory, BillCategoryAdapter.ViewHolder> {
    BillCategoryAdapterListener listener;

    public BillCategoryAdapter(@NonNull FirestoreRecyclerOptions<BillCategory> options, BillCategoryAdapterListener listener){
        super(options);
        this.listener = listener;
    }

    @NonNull
    @Override
    public BillCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bill_category_item_list, parent,false);
        return new BillCategoryAdapter.ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull BillCategoryAdapter.ViewHolder holder, int position, @NonNull BillCategory category) {
        holder.tvName.setText(category.getName());
        holder.tvDateKey.setText(R.string.bill_category_item_list);
        holder.tvDateValue.setText(category.getCreatedAt());
        holder.viewColor.setBackgroundColor(android.graphics.Color.parseColor(category.getColor()));
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDateKey, tvDateValue;
        Button btnUpdate, btnDelete;
        View viewColor;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            tvName = itemView.findViewById(R.id.tvBillCategoryItemListName);
            tvDateKey = itemView.findViewById(R.id.tvBillCategoryItemListCreatedAtTitle);
            tvDateValue = itemView.findViewById(R.id.tvBillCategoryItemListCreatedAtValue);
            viewColor = itemView.findViewById(R.id.viewIdentifierBillCategory);
            btnUpdate = itemView.findViewById(R.id.btnUpdateBillCategoryItem);
            btnDelete = itemView.findViewById(R.id.btnDeleteBillCategoryItemList);
        }
    }

    interface BillCategoryAdapterListener {
        void onEditBillCategory(BillCategory category);
        void onDeleteBillCategory(BillCategory category);
    }
}

