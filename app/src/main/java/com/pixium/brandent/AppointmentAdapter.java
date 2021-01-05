package com.pixium.brandent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pixium.brandent.db.Appointment;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.AppointmentHolder> {
    private List<AppointmentCardModel> appointments = new ArrayList<>();

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
                holder.closeBtn.setBackgroundResource(R.drawable.bg_circle_close_disabled);
                break;
            case "DONE":
                holder.checkBtn.setBackgroundResource(R.drawable.bg_circle_check_enabled);
                holder.closeBtn.setBackgroundResource(R.drawable.bg_circle_close_disabled);
                break;
            case "CANCELED":
                holder.checkBtn.setBackgroundResource(R.drawable.bg_circle_check_disabled);
                holder.closeBtn.setBackgroundResource(R.drawable.bg_circle_close_enabled);
                break;
        }

//        holder.checkBtn.setOnClickListener(v -> {
//            if (curAppointment.getState().equals("UNKNOWN") ||
//                    curAppointment.getState().equals("CANCELED")) {
//                holder.checkBtn.setBackgroundResource(R.drawable.bg_circle_check_enabled);
//                holder.closeBtn.setBackgroundResource(R.drawable.bg_circle_close_disabled);
//                Appointment updateAppointment = new Appointment(curAppointment.getUuid()
//                        , null, curAppointment.getClinicForId(),
//                        curAppointment.getPatientForId(), curAppointment.getVisitTime(),
//                        curAppointment.getPrice(), curAppointment.getTitle(), "DONE");
//
//            } else {
//                holder.checkBtn.setBackgroundResource(R.drawable.bg_circle_check_disabled);
//                holder.closeBtn.setBackgroundResource(R.drawable.bg_circle_close_disabled);
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }

    public void setAppointments(List<AppointmentCardModel> appointments) {
        this.appointments = appointments;
        notifyDataSetChanged();
    }

    class AppointmentHolder extends RecyclerView.ViewHolder {
        private TextView timeTv;
        private TextView patientTv;
        private TextView titleTv;
        private Button checkBtn;
        private Button closeBtn;

        public AppointmentHolder(@NonNull View itemView) {
            super(itemView);
            timeTv = itemView.findViewById(R.id.tv_time_appointment_cv);
            patientTv = itemView.findViewById(R.id.tv_patient_name_appointment_cv);
            titleTv = itemView.findViewById(R.id.tv_title_appointment_cv);
            checkBtn = itemView.findViewById(R.id.btn_check_appointment_cv);
            closeBtn = itemView.findViewById(R.id.btn_close_appointment_cv);
        }
    }
}