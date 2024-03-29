package com.brandent.clinitick.adapters;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.brandent.clinitick.R;
import com.brandent.clinitick.UiTools;
import com.brandent.clinitick.models.FinanceCardModel;

import java.util.ArrayList;
import java.util.List;

public class FinanceAdapter extends RecyclerView.Adapter<FinanceAdapter.FinanceHolder> {
    private List<FinanceCardModel> finances = new ArrayList<>();
    private OnItemClickListener listener;

    @NonNull
    @Override
    public FinanceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.monthly_finance_item, parent, false);
        return new FinanceHolder(itemView);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull FinanceHolder holder, int position) {
        FinanceCardModel financeCardModel = finances.get(position);
        holder.setIsRecyclable(false);
        holder.titleTv.setText(financeCardModel.getTitle());
        holder.descriptionTv.setText(financeCardModel.getDescription());
        holder.amountTv.setText(UiTools.priceToString(financeCardModel.getAmount(), true));
        if (financeCardModel.isExpense()) {
            holder.amountTv.setTextColor(Color.parseColor("#e02020"));
        } else {
            holder.amountTv.setTextColor(Color.parseColor("#6BCF00"));
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

    class FinanceHolder extends RecyclerView.ViewHolder {
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

            itemView.setOnClickListener(v -> {
                int position = getBindingAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(finances.get(position));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(FinanceCardModel financeCardModel);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
