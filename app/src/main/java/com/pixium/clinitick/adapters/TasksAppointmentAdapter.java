package com.pixium.clinitick.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pixium.clinitick.R;
import com.pixium.clinitick.db.entities.Appointment;
import com.pixium.clinitick.models.TasksAppointmentCardModel;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class TasksAppointmentAdapter extends
        RecyclerView.Adapter<TasksAppointmentAdapter.AppointmentHolder> {
    private List<TasksAppointmentCardModel> appointments = new ArrayList<>();
    private OnItemCheckClickListener mCheckListener;
    private OnItemCancelClickListener mCancelListener;
    private OnItemUnknownClickListener mUnknownListener;

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
                .inflate(R.layout.tasks_appointment_item, parent, false);
        return new AppointmentHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentHolder holder, int position) {
        TasksAppointmentCardModel current = appointments.get(position);
        Appointment curAppointment = current.getAppointment();
        Timestamp timestamp = new Timestamp(curAppointment.getVisitTime());
        String timeStr = Character.toString(timestamp.toString().charAt(11))
                + timestamp.toString().charAt(12)
                + timestamp.toString().charAt(13)
                + timestamp.toString().charAt(14)
                + timestamp.toString().charAt(15);

        holder.timeTv.setText(timeStr);
        holder.patientTv.setText(current.getPatientName());
        holder.titleTv.setText(curAppointment.getTitle());
        switch (curAppointment.getState()) {
            case "unknown":
                holder.checkBtn.setBackgroundResource(R.drawable.bg_circle_check_disabled);
                holder.cancelBtn.setBackgroundResource(R.drawable.bg_circle_close_disabled);
                break;
            case "done":
                holder.checkBtn.setBackgroundResource(R.drawable.bg_circle_check_enabled);
                holder.cancelBtn.setBackgroundResource(R.drawable.bg_circle_close_disabled);
                break;
            case "canceled":
                holder.checkBtn.setBackgroundResource(R.drawable.bg_circle_check_disabled);
                holder.cancelBtn.setBackgroundResource(R.drawable.bg_circle_close_enabled);
                break;
        }

        holder.checkBtn.setOnClickListener(v -> {
            if (curAppointment.getState().equals("unknown") ||
                    curAppointment.getState().equals("canceled")) {
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
            if (curAppointment.getState().equals("unknown") ||
                    curAppointment.getState().equals("done")) {
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

    public void setAppointments(List<TasksAppointmentCardModel> appointments) {
        this.appointments = appointments;
        notifyDataSetChanged();
    }

    public interface OnItemCheckClickListener {
        void onItemCheckClick(int id) throws ExecutionException, InterruptedException;
    }

    public interface OnItemCancelClickListener {
        void onItemCancelClick(int id) throws ExecutionException, InterruptedException;
    }

    public interface OnItemUnknownClickListener {
        void onItemUnknownClick(int id) throws ExecutionException, InterruptedException;
    }

    public static class AppointmentHolder extends RecyclerView.ViewHolder {
        private final TextView timeTv;
        private final TextView patientTv;
        private final TextView titleTv;
        private final Button checkBtn;
        private final Button cancelBtn;

        public AppointmentHolder(@NonNull View itemView) {
            super(itemView);
            timeTv = itemView.findViewById(R.id.tv_time_tasks_appointment_cv);
            patientTv = itemView.findViewById(R.id.tv_patient_name_tasks_appointment_cv);
            titleTv = itemView.findViewById(R.id.tv_title_tasks_appointment_cv);
            checkBtn = itemView.findViewById(R.id.btn_check_tasks_appointment_cv);
            cancelBtn = itemView.findViewById(R.id.btn_cancel_tasks_appointment_cv);
        }
    }
}
