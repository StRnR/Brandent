package com.pixium.brandent.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pixium.brandent.R;
import com.pixium.brandent.models.TodayItem;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class TodayHomeAdapter extends RecyclerView.Adapter<TodayHomeAdapter.TodayViewHolder> {
    ArrayList<TodayItem> mTodayItems;

    public TodayHomeAdapter(ArrayList<TodayItem> todayList) {
        mTodayItems = todayList;
    }

    @NonNull
    @Override
    public TodayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.today_item
                , parent, false);
        return new TodayViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TodayViewHolder holder, int position) {
        TodayItem todayItem = mTodayItems.get(position);
        holder.colorIv.setBackgroundColor(Color.parseColor(todayItem.getColor()));
        String str = todayItem.getAppointmentNumber() + " " + "ویزیت";
        holder.appointmentNumbersTv.setText(str);
        str = "(" + todayItem.getClinicTitle() + ")";
        holder.clinicTitleTv.setText(str);
    }

    @Override
    public int getItemCount() {
        return mTodayItems.size();
    }

    public static class TodayViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView colorIv;
        public TextView appointmentNumbersTv;
        public TextView clinicTitleTv;

        public TodayViewHolder(@NonNull View itemView) {
            super(itemView);
            colorIv = itemView.findViewById(R.id.iv_clinic_color_today);
            appointmentNumbersTv = itemView.findViewById(R.id.tv_appointment_numbers);
            clinicTitleTv = itemView.findViewById(R.id.tv_clinic_title_today);
        }
    }
}
