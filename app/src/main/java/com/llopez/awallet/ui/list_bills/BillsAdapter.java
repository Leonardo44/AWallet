package com.llopez.awallet.ui.list_bills;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.llopez.awallet.R;
import androidx.recyclerview.widget.RecyclerView;

public class BillsAdapter  extends RecyclerView.Adapter<BillsAdapter.ViewHolder> {
    private Integer[] localDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public ViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.tvBillItemValue);
        }
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used
     * by RecyclerView.
     */
    public BillsAdapter(Integer[] dataSet) {
        localDataSet = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.bill_item_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
    }

    @Override
    public int getItemCount() {
        return localDataSet.length;
    }
}
