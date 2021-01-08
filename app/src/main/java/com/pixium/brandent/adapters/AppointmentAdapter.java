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
import com.pixium.brandent.models.AppointmentCardModel;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.AppointmentHolder> {
    private List<AppointmentCardModel> appointments = new ArrayList<>();
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
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointment_item,
                parent, false);
        return new AppointmentHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentHolder holder, int position) {
        AppointmentCardModel current = appointments.get(position);
        Appointment curAppointment = current.getAppointment();
        Timestamp timestamp = new Timestamp(curAppointment.getVisitTime());
        String timeStr = Character.toString(timestamp.toString().charAt(11))
                + Character.toString(timestamp.toString().charAt(12))
                + Character.toString(timestamp.toString().charAt(13))
                + Character.toString(timestamp.toString().charAt(14))
                + Character.toString(timestamp.toString().charAt(15));

        holder.timeTv.setText(timeStr);
        holder.patientTv.setText(current.getPatientName());
        holder.titleTv.setText(curAppointment.getTitle());
        switch (curAppointment.getState()) {
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
            if (curAppointment.getState().equals("UNKNOWN") ||
                    curAppointment.getState().equals("CANCELED")) {
                holder.checkBtn.setBackgroundResource(R.drawable.bg_circle_check_enabled);
                holder.cancelBtn.setBackgroundResource(R.drawable.bg_circle_close_disabled);
                if (mCheckListener != null) {
                    try {
                        mCheckListener.onItemCheckClick(curAppointment.getAppointmentId());
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                holder.checkBtn.setBackgroundResource(R.drawable.bg_circle_check_disabled);
                holder.cancelBtn.setBackgroundResource(R.drawable.bg_circle_close_disabled);
                if (mUnknownListener != null) {
                    try {
                        mUnknownListener.onItemUnknownClick(curAppointment.getAppointmentId());
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        holder.cancelBtn.setOnClickListener(v -> {
            if (curAppointment.getState().equals("UNKNOWN") ||
                    curAppointment.getState().equals("DONE")) {
                holder.checkBtn.setBackgroundResource(R.drawable.bg_circle_check_disabled);
                holder.cancelBtn.setBackgroundResource(R.drawable.bg_circle_close_enabled);
                if (mCancelListener != null) {
                    try {
                        mCancelListener.onItemCancelClick(curAppointment.getAppointmentId());
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                holder.checkBtn.setBackgroundResource(R.drawable.bg_circle_check_disabled);
                holder.cancelBtn.setBackgroundResource(R.drawable.bg_circle_close_disabled);
                if (mUnknownListener != null) {
                    try {
                        mUnknownListener.onItemUnknownClick(curAppointment.getAppointmentId());
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

    public void setAppointments(List<AppointmentCardModel> appointments) {
        this.appointments = appointments;
        notifyDataSetChanged();
    }

    public static class AppointmentHolder extends RecyclerView.ViewHolder {
        private TextView timeTv;
        private TextView patientTv;
        private TextView titleTv;
        private Button checkBtn;
        private Button cancelBtn;

        public AppointmentHolder(@NonNull View itemView) {
            super(itemView);
            timeTv = itemView.findViewById(R.id.tv_time_appointment_cv);
            patientTv = itemView.findViewById(R.id.tv_patient_name_appointment_cv);
            titleTv = itemView.findViewById(R.id.tv_title_appointment_cv);
            checkBtn = itemView.findViewById(R.id.btn_check_appointment_cv);
            cancelBtn = itemView.findViewById(R.id.btn_close_appointment_cv);
        }
    }
}
