package com.pixium.brandent.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pixium.brandent.R;
import com.pixium.brandent.db.entities.Appointment;
import com.pixium.brandent.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class PatientAppointmentAdapter extends
        RecyclerView.Adapter<PatientAppointmentAdapter.AppointmentHolder> {
    private List<Appointment> appointments = new ArrayList<>();
    private OnItemCheckClickListener mCheckListener;
    private OnItemCancelClickListener mCancelListener;
    private OnItemUnknownClickListener mUnknownListener;

    public interface OnItemCheckClickListener {
        void onItemCheckClick(int id) throws ExecutionException, InterruptedException;
    }

    public interface OnItemCancelClickListener {
        void onItemCancelClick(int id) throws ExecutionException, InterruptedException;
    }

    public interface OnItemUnknownClickListener {
        void onItemUnknownClick(int id) throws ExecutionException, InterruptedException;
    }

    public void setOnItemCheckClickListener(OnItemCheckClickListener listener) {
        mCheckListener = listener;
    }

    public void setOnItemCancelClickListener(OnItemCancelClickListener listener) {
        mCancelListener = listener;
    }

    public void setOnItemUnknownClickListener(OnItemUnknownClickListener listener) {
        mUnknownListener = listener;
    }

    @NonNull
    @Override
    public AppointmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.patient_appointment_item, parent, false);
        return new AppointmentHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentHolder holder, int position) {
        Appointment current = appointments.get(position);

        holder.titleTv.setText(current.getTitle());
        holder.dateTv.setText(DateUtils.getPersianStringDate(current.getVisitTime()));
        switch (current.getState()) {
            case "UNKNOWN":
                holder.checkBtn.setBackgroundResource(R.drawable.bg_circle_check_disabled);
                holder.cancelBtn.setBackgroundResource(R.drawable.bg_circle_close_disabled);
                break;
            case "DONE":
                holder.checkBtn.setBackgroundResource(R.drawable.bg_circle_check_enabled);
                holder.cancelBtn.setBackgroundResource(R.drawable.bg_circle_close_disabled);
                break;
            case "CANCELED":
                holder.checkBtn.setBackgroundResource(R.drawable.bg_circle_check_disabled);
                holder.cancelBtn.setBackgroundResource(R.drawable.bg_circle_close_enabled);
                break;
        }

        holder.checkBtn.setOnClickListener(v -> {
            if (current.getState().equals("UNKNOWN") ||
                    current.getState().equals("CANCELED")) {
                holder.checkBtn.setBackgroundResource(R.drawable.bg_circle_check_enabled);
                holder.cancelBtn.setBackgroundResource(R.drawable.bg_circle_close_disabled);
                if (mCheckListener != null) {
                    try {
                        mCheckListener.onItemCheckClick(current.getAppointmentId());
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                holder.checkBtn.setBackgroundResource(R.drawable.bg_circle_check_disabled);
                holder.cancelBtn.setBackgroundResource(R.drawable.bg_circle_close_disabled);
                if (mUnknownListener != null) {
                    try {
                        mUnknownListener.onItemUnknownClick(current.getAppointmentId());
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        holder.cancelBtn.setOnClickListener(v -> {
            if (current.getState().equals("UNKNOWN") ||
                    current.getState().equals("DONE")) {
                holder.checkBtn.setBackgroundResource(R.drawable.bg_circle_check_disabled);
                holder.cancelBtn.setBackgroundResource(R.drawable.bg_circle_close_enabled);
                if (mCancelListener != null) {
                    try {
                        mCancelListener.onItemCancelClick(current.getAppointmentId());
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                holder.checkBtn.setBackgroundResource(R.drawable.bg_circle_check_disabled);
                holder.cancelBtn.setBackgroundResource(R.drawable.bg_circle_close_disabled);
                if (mUnknownListener != null) {
                    try {
                        mUnknownListener.onItemUnknownClick(current.getAppointmentId());
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
        notifyDataSetChanged();
    }

    public static class AppointmentHolder extends RecyclerView.ViewHolder {
        private final TextView titleTv;
        private final TextView dateTv;
        private final Button checkBtn;
        private final Button cancelBtn;

        public AppointmentHolder(@NonNull View itemView) {
            super(itemView);
            titleTv = itemView.findViewById(R.id.tv_title_patient_appointment_cv);
            dateTv = itemView.findViewById(R.id.tv_date_patient_appointment_cv);
            checkBtn = itemView.findViewById(R.id.btn_check_patient_appointment_cv);
            cancelBtn = itemView.findViewById(R.id.btn_cancel_patient_appointment_cv);
        }
    }
}