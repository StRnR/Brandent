package com.pixium.brandent;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.pixium.brandent.db.Clinic;

public class ClinicAdapter extends ListAdapter<Clinic, ClinicAdapter.ClinicHolder> {
    private OnItemClickListener listener;

    public ClinicAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Clinic> DIFF_CALLBACK = new DiffUtil.ItemCallback<Clinic>() {
        @Override
        public boolean areItemsTheSame(@NonNull Clinic oldItem, @NonNull Clinic newItem) {
            return oldItem.getClinicId() == newItem.getClinicId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Clinic oldItem, @NonNull Clinic newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) &&
                    oldItem.getColor().equals(newItem.getColor());
        }
    };

    @NonNull
    @Override
    public ClinicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.clinic_item, parent, false);
        return new ClinicHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ClinicHolder holder, int position) {
        Clinic currentClinic = getItem(position);
        holder.titleTv.setText(currentClinic.getTitle());
        holder.colorIv.setBackgroundColor(Color.parseColor(currentClinic.getColor()));
    }

    public Clinic getClinicAt(int position) {
        return getItem(position);
    }

    class ClinicHolder extends RecyclerView.ViewHolder {
        private TextView titleTv;
        private ImageView colorIv;

        public ClinicHolder(@NonNull View itemView) {
            super(itemView);
            titleTv = itemView.findViewById(R.id.tv_clinic_title_cv);
            colorIv = itemView.findViewById(R.id.iv_clinic_color_cv);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Clinic clinic);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
