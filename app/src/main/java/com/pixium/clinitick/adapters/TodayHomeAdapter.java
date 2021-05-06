package com.pixium.clinitick.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.pixium.clinitick.R;
import com.pixium.clinitick.models.TodayItem;

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
        switch (todayItem.getColor()) {
            case "color_1":
                holder.colorIv.setBackgroundColor(ContextCompat.getColor(holder.colorIv.getContext()
                        , R.color.clinicLightGreen));
                break;

            case "color_2":
                holder.colorIv.setBackgroundColor(ContextCompat.getColor(holder.colorIv.getContext()
                        , R.color.clinicGreen));
                break;

            case "color_3":
                holder.colorIv.setBackgroundColor(ContextCompat.getColor(holder.colorIv.getContext()
                        , R.color.clinicTeal));
                break;

            case "color_4":
                holder.colorIv.setBackgroundColor(ContextCompat.getColor(holder.colorIv.getContext()
                        , R.color.clinicCyan));
                break;

            case "color_5":
                holder.colorIv.setBackgroundColor(ContextCompat.getColor(holder.colorIv.getContext()
                        , R.color.clinicBlue));
                break;

            case "color_6":
                holder.colorIv.setBackgroundColor(ContextCompat.getColor(holder.colorIv.getContext()
                        , R.color.clinicPurple));
                break;

            case "color_7":
                holder.colorIv.setBackgroundColor(ContextCompat.getColor(holder.colorIv.getContext()
                        , R.color.clinicPink));
                break;

            case "color_8":
                holder.colorIv.setBackgroundColor(ContextCompat.getColor(holder.colorIv.getContext()
                        , R.color.clinicRed));
                break;
        }
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
