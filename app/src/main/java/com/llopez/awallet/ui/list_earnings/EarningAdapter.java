package com.llopez.awallet.ui.list_earnings;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.llopez.awallet.R;
import com.llopez.awallet.model.BillObject;
import com.llopez.awallet.model.EarningObject;
import com.llopez.awallet.ui.list_bills.BillsAdapter;

import java.text.SimpleDateFormat;
import java.util.List;

public class EarningAdapter extends RecyclerView.Adapter<EarningAdapter.ViewHolder> {
    private List<EarningObject> localDataSet;
    private EarningAdapter.EarningAdapterAdapterListener listener;

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
            tvValue = view.findViewById(R.id.tvEarningItemValue);
            tvBillItemCategory = view.findViewById(R.id.tvEarningItemCategory);
            viewIdentifierBillItem = view.findViewById(R.id.viewIdentifierEarningItem);
            tvBillItemCreatedAtTitle = view.findViewById(R.id.tvEarningItemCreatedAtTitle);
            tvBillItemCreatedAtValue = view.findViewById(R.id.tvEarningItemCreatedAtValue);
            tvBillItemUpdatedAtTitle = view.findViewById(R.id.tvEarningItemUpdatedAtTitle);
            tvBillItemUpdatedAtValue = view.findViewById(R.id.tvEarningItemUpdatedAtValue);
            tvBillItemDescription = view.findViewById(R.id.tvEarningItemDescription);
        }
    }

    public EarningAdapter(List<EarningObject> dataSet, EarningAdapter.EarningAdapterAdapterListener listener) {
        localDataSet = dataSet;
    }

    @Override
    public EarningAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.earning_item_list, viewGroup, false);
        return new EarningAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EarningAdapter.ViewHolder viewHolder, final int position) {
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

    interface EarningAdapterAdapterListener {
        void onEditEarning(EarningObject earning);
        void onDeleteEarning(EarningObject earning);
    }
}
