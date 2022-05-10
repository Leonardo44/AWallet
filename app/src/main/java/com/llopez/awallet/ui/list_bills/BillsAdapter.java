package com.llopez.awallet.ui.list_bills;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.llopez.awallet.R;
import com.llopez.awallet.model.BillObject;

import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;

public class BillsAdapter  extends RecyclerView.Adapter<BillsAdapter.ViewHolder> {
    private List<BillObject> localDataSet;
    private BillsAdapterAdapterListener listener;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView tvValue;
        public final TextView tvBillItemCategory;
        public final View viewIdentifierBillItem;
        public final TextView tvBillItemCreatedAtTitle;
        public final TextView tvBillItemCreatedAtValue;
        public final TextView tvBillItemUpdatedAtTitle;
        public final TextView tvBillItemUpdatedAtValue;
        public final TextView tvBillItemDescription;

        public ViewHolder(View view) {
            super(view);
            tvValue = view.findViewById(R.id.tvBillItemValue);
            tvBillItemCategory = view.findViewById(R.id.tvBillItemCategory);
            viewIdentifierBillItem = view.findViewById(R.id.viewIdentifierBillItem);
            tvBillItemCreatedAtTitle = view.findViewById(R.id.tvBillItemCreatedAtTitle);
            tvBillItemCreatedAtValue = view.findViewById(R.id.tvBillItemCreatedAtValue);
            tvBillItemUpdatedAtTitle = view.findViewById(R.id.tvBillItemUpdatedAtTitle);
            tvBillItemUpdatedAtValue = view.findViewById(R.id.tvBillItemUpdatedAtValue);
            tvBillItemDescription = view.findViewById(R.id.tvBillItemDescription);
        }
    }

    public BillsAdapter(List<BillObject> dataSet, BillsAdapterAdapterListener listener) {
        localDataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.bill_item_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        String createdAt = new SimpleDateFormat("MM/dd/yyyy").format(localDataSet.get(position).getCreatedAt());

        viewHolder.tvValue.setText("$" + localDataSet.get(position).getAmount() + "");
        viewHolder.tvBillItemCategory.setText(localDataSet.get(position).getCategory().getName());
        viewHolder.viewIdentifierBillItem.setBackgroundColor(android.graphics.Color.parseColor(localDataSet.get(position).getCategory().getColor()));
        viewHolder.tvBillItemCreatedAtTitle.setText(R.string.item_list_name_object);
        viewHolder.tvBillItemCreatedAtValue.setText(localDataSet.get(position).getName());
        viewHolder.tvBillItemUpdatedAtTitle.setText(R.string.item_list_date_object);
        viewHolder.tvBillItemUpdatedAtValue.setText(createdAt);
        viewHolder.tvBillItemDescription.setText(localDataSet.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    interface BillsAdapterAdapterListener {
        void onEditBill(BillObject bill);
        void onDeleteBill(BillObject bill);
    }
}
