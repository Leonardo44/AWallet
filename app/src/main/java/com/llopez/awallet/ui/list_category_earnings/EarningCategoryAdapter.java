package com.llopez.awallet.ui.list_category_earnings;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.llopez.awallet.R;
import com.llopez.awallet.model.EarningCategory;

import java.text.SimpleDateFormat;

public class EarningCategoryAdapter extends FirestoreRecyclerAdapter<EarningCategory, EarningCategoryAdapter.ViewHolder> {
    EarningCategoryAdapter.EarningCategoryAdapterListener listener;

    public EarningCategoryAdapter(@NonNull FirestoreRecyclerOptions<EarningCategory> options, EarningCategoryAdapter.EarningCategoryAdapterListener listener){
        super(options);
        this.listener = listener;
    }

    @NonNull
    @Override
    public EarningCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.earning_category_item_list, parent,false);
        return new EarningCategoryAdapter.ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull EarningCategoryAdapter.ViewHolder holder, int position, @NonNull EarningCategory category) {
        String createdAt = new SimpleDateFormat("MM/dd/yyyy").format(category.getCreatedAt());

        holder.tvName.setText(category.getName());
        holder.tvDateKey.setText(R.string.bill_category_item_list);
        holder.tvDateValue.setText(createdAt);
        holder.viewColor.setBackgroundColor(android.graphics.Color.parseColor(category.getColor()));

        holder.btnDelete.setOnClickListener(v -> {
            listener.onDeleteEarningCategory(category);
        });
        holder.btnUpdate.setOnClickListener(v -> {
            listener.onEditEarningCategory(category);
        });
    }

    @Override
    public void onDataChanged() {
        super.onDataChanged();
        listener.showEmptyMessage(getItemCount() == 0);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDateKey, tvDateValue;
        Button btnUpdate, btnDelete;
        View viewColor;

        public ViewHolder(@NonNull View itemView){
            super(itemView);
            tvName = itemView.findViewById(R.id.tvEarningCategoryItemListName);
            tvDateKey = itemView.findViewById(R.id.tvEarningCategoryItemListCreatedAtTitle);
            tvDateValue = itemView.findViewById(R.id.tvEarningCategoryItemListCreatedAtValue);
            viewColor = itemView.findViewById(R.id.viewIdentifierEarningCategory);
            btnUpdate = itemView.findViewById(R.id.btnUpdateEarningCategoryItem);
            btnDelete = itemView.findViewById(R.id.btnDeleteEarningCategoryItemList);
        }
    }

    interface EarningCategoryAdapterListener {
        void onEditEarningCategory(EarningCategory category);
        void onDeleteEarningCategory(EarningCategory category);
        void showEmptyMessage(boolean visible);
    }
}
