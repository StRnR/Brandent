package com.pixium.brandent.adapters;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pixium.brandent.R;
import com.pixium.brandent.UiTools;
import com.pixium.brandent.models.FinanceCardModel;

import java.util.ArrayList;
import java.util.List;

public class FinanceAdapter extends RecyclerView.Adapter<FinanceAdapter.FinanceHolder> {
    private List<FinanceCardModel> finances = new ArrayList<>();

    @NonNull
    @Override
    public FinanceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.monthly_finance_item
                , parent, false);
        return new FinanceHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull FinanceHolder holder, int position) {
        FinanceCardModel financeCardModel = finances.get(position);
        holder.titleTv.setText(financeCardModel.getTitle());
        holder.descriptionTv.setText(financeCardModel.getDescription());
        holder.amountTv.setText(UiTools.priceToString(financeCardModel.getAmount(), true));
        if (financeCardModel.isExpense()) {
            holder.amountTv.setTextColor(Color.parseColor("#e02020"));
        }
        holder.dateTv.setText(financeCardModel.getDate());
    }

    @Override
    public int getItemCount() {
        return finances.size();
    }

    public void setFinances(List<FinanceCardModel> finances) {
        this.finances = finances;
        notifyDataSetChanged();
    }

    static class FinanceHolder extends RecyclerView.ViewHolder {
        private final TextView titleTv;
        private final TextView descriptionTv;
        private final TextView amountTv;
        private final TextView dateTv;

        public FinanceHolder(@NonNull View itemView) {
            super(itemView);
            titleTv = itemView.findViewById(R.id.tv_title_finance_cv);
            descriptionTv = itemView.findViewById(R.id.tv_description_finance_cv);
            amountTv = itemView.findViewById(R.id.tv_amount_finance_cv);
            dateTv = itemView.findViewById(R.id.tv_date_finance_cv);
        }
    }
}
