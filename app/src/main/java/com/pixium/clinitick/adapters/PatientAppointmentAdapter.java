package com.pixium.clinitick.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pixium.clinitick.DateTools;
import com.pixium.clinitick.R;
import com.pixium.clinitick.db.entities.Appointment;
import com.pixium.clinitick.utils.DateUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class PatientAppointmentAdapter extends
        RecyclerView.Adapter<PatientAppointmentAdapter.AppointmentHolder> {
    private List<Appointment> appointments = new ArrayList<>();
    private OnItemClickListener mClickListener;
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
                .inflate(R.layout.patient_appointment_item, parent, false);
        return new AppointmentHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentHolder holder, int position) {
        Appointment current = appointments.get(position);

        holder.titleTv.setText(current.getTitle());
        try {
            if (!current.getVisitTime().equals(DateTools.timestampFromString(DateTools.oldLastUpdated
                    , DateTools.apiTimeFormat))) {
                holder.dateTv.setText(DateUtils.getPersianStringDate(current.getVisitTime()));
            } else {
                holder.dateTv.setText("");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        switch (current.getState()) {
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
            if (current.getState().equals("unknown") ||
                    current.getState().equals("canceled")) {
                if (mCheckListener != null) {
                    try {
                        mCheckListener.onItemCheckClick(current.getAppointmentId());
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } else {
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
            if (current.getState().equals("unknown") ||
                    current.getState().equals("done")) {
                if (mCancelListener != null) {
                    try {
                        mCancelListener.onItemCancelClick(current.getAppointmentId());
                    } catch (ExecutionException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } else {
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

    public interface OnItemCheckClickListener {
        void onItemCheckClick(int id) throws ExecutionException, InterruptedException;
    }

    public interface OnItemCancelClickListener {
        void onItemCancelClick(int id) throws ExecutionException, InterruptedException;
    }

    public interface OnItemUnknownClickListener {
        void onItemUnknownClick(int id) throws ExecutionException, InterruptedException;
    }

    class AppointmentHolder extends RecyclerView.ViewHolder {
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
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (mClickListener != null && position != RecyclerView.NO_POSITION) {
                    mClickListener.onItemClick(appointments.get(position));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Appointment appointment);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mClickListener = listener;
    }
}
